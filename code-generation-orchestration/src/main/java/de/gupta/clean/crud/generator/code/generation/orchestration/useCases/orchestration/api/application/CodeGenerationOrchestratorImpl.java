package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.LayerConcreteTypes;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.OverwriteResolver;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.GeneratedRelationship;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModelFactory;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.SourceCodeTemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.api.application.SourceCodeFileWriter;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
final class CodeGenerationOrchestratorImpl implements CodeGenerationOrchestrator
{
	private static final Set<String> DEFAULT_TEMPLATE_GROUPS = Set.of(
			TemplateGroup.COMMON.name(),
			TemplateGroup.CONFIGURATION.name(),
			TemplateGroup.DOMAIN_MODELS.name(),
			TemplateGroup.DOMAIN_SUPPORT.name(),
			TemplateGroup.API_DTOS.name(),
			TemplateGroup.API_ADAPTERS.name(),
			TemplateGroup.API_CONTROLLERS.name(),
			TemplateGroup.PERSISTENCE_MODELS.name(),
			TemplateGroup.PERSISTENCE_ADAPTERS.name(),
			TemplateGroup.PERSISTENCE_REPOSITORIES.name(),
			TemplateGroup.PERSISTENCE_HISTORY.name(),
			TemplateGroup.SECURITY.name(),
			TemplateGroup.USE_CASE_FETCH.name(),
			TemplateGroup.USE_CASE_SAVE.name(),
			TemplateGroup.USE_CASE_UPDATE.name(),
			TemplateGroup.USE_CASE_DELETE.name());
	private final DomainModelParser modelParser;
	private final SourceCodeTemplateProcessor templateProcessor;
	private final SourceCodeFileWriter sourceCodeFileWriter;
	private final RelationshipGenerationConfigurationValidator relationshipConfigurationValidator;
	private final GeneratedRelationshipFactory generatedRelationshipFactory;
	private final GenerationExclusionPolicy generationExclusionPolicy;
	private final SourceCodeTargetPathResolver sourceCodeTargetPathResolver;
	private final JavaGenerationSpecificationLoader generationSpecificationLoader;
	private final GenerationSpecificationConfigurationAssembler generationSpecificationConfigurationAssembler;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		var model = modelParser.parseDomainModel(configuration.inputs().baseModelSourceCodeFilePath());
		var effectiveConfiguration = effectiveConfiguration(configuration, model);
		relationshipConfigurationValidator.validate(model, effectiveConfiguration.relationships());
		var relationships = generatedRelationshipFactory.create(model, effectiveConfiguration.relationships());
		var layerTypes = mergeRelationshipConcreteTypes(effectiveConfiguration.genericTypes(), relationships);
		var templateModel = TemplateModelFactory.create(model.packageName(), model.modelName(),
				model.genericTypeParameters(), model.properties(), layerTypes.domain(),
				layerTypes.persistence(), layerTypes.api(), Set.of(),
				effectiveConfiguration.historized(), relationships,
				effectiveConfiguration.rootAggregateIds().apiIdType(),
				effectiveConfiguration.rootAggregateIds().domainIdType(),
				effectiveConfiguration.rootAggregateIds().persistenceIdType());
		var overwriteResolver = OverwriteResolver.with(effectiveConfiguration.overwrite());

		var files = templateProcessor.generateSourceCode(templateModel,
				TemplateSelector.with(
						resolveTemplateGroups(effectiveConfiguration),
						resolveTemplates(effectiveConfiguration),
						resolveTags(effectiveConfiguration),
						effectiveConfiguration.generation().excludeGroups(),
						generationExclusionPolicy.excludedTemplates(effectiveConfiguration),
						resolveExcludedTags(effectiveConfiguration)));

		files.forEach((template, sourceCodeFile) ->
		{
			if (!shouldWrite(template, sourceCodeFile, model.contentRootPath(), overwriteResolver))
			{
				return;
			}
			var targetPath = sourceCodeTargetPathResolver.resolve(model.contentRootPath(), sourceCodeFile);
			var request = SourceCodeWriteRequest.from(model.contentRootPath(), sourceCodeFile.fileName(),
					sourceCodeFile.sourceCode().sourceCode(),
					overwriteResolver.shouldOverwrite(template, targetPath));

			sourceCodeFileWriter.writeSourceCode(request);
		});

		return 0;
	}

	private CodeGenerationConfiguration effectiveConfiguration(
			final CodeGenerationConfiguration configuration,
			final de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model model)
	{
		String specSourcePath = configuration.inputs().generationSpecSourceCodeFilePath();
		if (specSourcePath == null || specSourcePath.isBlank())
		{
			return configuration;
		}
		var specification = generationSpecificationLoader.load(
				Path.of(configuration.inputs().baseModelSourceCodeFilePath()),
				Path.of(specSourcePath));
		var loaded = generationSpecificationConfigurationAssembler.assemble(model, specification);
		return new CodeGenerationConfiguration(
				configuration.inputs(),
				configuration.genericTypes(),
				configuration.generation(),
				loaded.relationships(),
				loaded.rootAggregateIds(),
				configuration.ownership(),
				configuration.overwrite(),
				configuration.historized());
	}

	private LayerConcreteTypes mergeRelationshipConcreteTypes(
			final LayerConcreteTypes configuredTypes,
			final java.util.List<GeneratedRelationship> relationships)
	{
		Map<String, String> domain = new LinkedHashMap<>(configuredTypes.domain());
		Map<String, String> persistence = new LinkedHashMap<>(configuredTypes.persistence());
		Map<String, String> api = new LinkedHashMap<>(configuredTypes.api());
		for (GeneratedRelationship relationship : relationships)
		{
			domain.put(relationship.genericPlaceholder(),
					"de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel<" + relationship.satelliteDomainIdType() + ", " + relationship.domainModelType() + ">");
			persistence.put(relationship.genericPlaceholder(), relationship.satellitePersistenceIdType());
			api.put(relationship.genericPlaceholder(), relationship.responseType());
		}
		return new LayerConcreteTypes(Map.copyOf(domain), Map.copyOf(persistence), Map.copyOf(api));
	}

	private Set<String> resolveTemplateGroups(final CodeGenerationConfiguration configuration)
	{
		return configuration.generation().groups().isEmpty() ? DEFAULT_TEMPLATE_GROUPS :
				configuration.generation().groups();
	}

	private Set<String> resolveTemplates(final CodeGenerationConfiguration configuration)
	{
		return configuration.generation().templates();
	}

	private Set<String> resolveTags(final CodeGenerationConfiguration configuration)
	{
		return configuration.generation().tags();
	}

	private Set<String> resolveExcludedTags(final CodeGenerationConfiguration configuration)
	{
		return configuration.generation().excludeTags();
	}

	private boolean shouldWrite(
			final SourceCodeTemplate template,
			final SourceCodeFile sourceCodeFile,
			final Path contentRootPath,
			final OverwriteResolver overwriteResolver)
	{
		var targetPath = sourceCodeTargetPathResolver.resolve(contentRootPath, sourceCodeFile);
		return !Files.exists(targetPath) || overwriteResolver.shouldOverwrite(template, targetPath);
	}

	CodeGenerationOrchestratorImpl(
			final DomainModelParser modelParser,
			final SourceCodeTemplateProcessor templateProcessor,
			final SourceCodeFileWriter sourceCodeFileWriter,
			final RelationshipGenerationConfigurationValidator relationshipConfigurationValidator,
			final GeneratedRelationshipFactory generatedRelationshipFactory,
			final GenerationExclusionPolicy generationExclusionPolicy,
			final SourceCodeTargetPathResolver sourceCodeTargetPathResolver,
			final JavaGenerationSpecificationLoader generationSpecificationLoader,
			final GenerationSpecificationConfigurationAssembler generationSpecificationConfigurationAssembler)
	{
		this.modelParser = modelParser;
		this.templateProcessor = templateProcessor;
		this.sourceCodeFileWriter = sourceCodeFileWriter;
		this.relationshipConfigurationValidator = relationshipConfigurationValidator;
		this.generatedRelationshipFactory = generatedRelationshipFactory;
		this.generationExclusionPolicy = generationExclusionPolicy;
		this.sourceCodeTargetPathResolver = sourceCodeTargetPathResolver;
		this.generationSpecificationLoader = generationSpecificationLoader;
		this.generationSpecificationConfigurationAssembler = generationSpecificationConfigurationAssembler;
	}
}