package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.OverwriteResolver;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.OwnershipResolver;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.GeneratedRelationship;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModelFactory;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.SourceCodeTemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration.TemplateMetadataRegistry;
import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.api.application.SourceCodeFileWriter;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Pattern;

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
	private static final Pattern PACKAGE_PATTERN = Pattern.compile("(?m)^\\s*package\\s+([a-zA-Z_][\\w.]*)\\s*;");
	private static final Set<String> BASE_MODEL_TEMPLATES = Set.of("BaseModel");
	private static final Set<String> DOMAIN_MODEL_TEMPLATES = Set.of(
			"DomainModel",
			"DomainModelImpl",
			"DomainModelBuilder",
			"DomainModelBuilderFactory",
			"DomainModelCreateDTO",
			"DomainModelUpdatePatchDTO",
			"DomainModelResponseDTO",
			"DomainModelPatcher",
			"DomainResponseBuilder");
	private static final Set<String> PERSISTENCE_MODEL_TEMPLATES = Set.of(
			"PersistenceModel",
			"PersistenceModelImpl",
			"PersistenceModelBuilderFactory",
			"PersistenceJpaConverters");
	private static final Set<String> API_MODEL_TEMPLATES = Set.of(
			"APIModelCreateDTO",
			"APIModelResponseDTO",
			"APIModelUpdatePatchDTO");
	private final DomainModelParser modelParser;
	private final SourceCodeTemplateProcessor templateProcessor;
	private final SourceCodeFileWriter sourceCodeFileWriter;
	private final RelationshipGenerationConfigurationValidator relationshipConfigurationValidator;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		var model = modelParser.parseDomainModel(configuration.inputs().baseModelSourceCodeFilePath());
		relationshipConfigurationValidator.validate(model, configuration.relationships());
		var relationships = configuration.relationships().stream()
		                                 .map(relationship -> model.properties()
		                                                           .stream()
		                                                           .filter(property -> property.name().equals(
																		   relationship.masterProperty()))
		                                                           .findFirst()
		                                                           .map(property ->
																   {
																	   var normalizedRelationship =
																			   relationship.normalized();
																	   var effectiveCardinality =
																			   normalizedRelationship.cardinality() == null
																					   ? property.collectionValued() ?
																						 "MANY" : "ONE"
																					   :
																					   normalizedRelationship.cardinality()
							                                                                                 .name();
																	   var effectiveReconciliationStrategy =
																			   normalizedRelationship.reconciliationStrategy() == null
																					   ? "ONE".equals(
																					   effectiveCardinality)
																						 ? "REPLACE"
																						 : "MERGE_BY_ID"
																					   :
																					   normalizedRelationship.reconciliationStrategy()
							                                                                                 .name();
																	   return new GeneratedRelationship(
																			   property,
																			   model.modelName().endsWith("Model")
																					   ? model.modelName().substring(
																					   0,
																					   model.modelName()
							                                                                .length() - "Model".length())
																					   : model.modelName(),
																			   normalizedRelationship.satelliteAggregate(),
																			   effectiveCardinality,
																			   effectiveReconciliationStrategy,
																			   normalizedRelationship.satelliteDomainIdType(),
																			   normalizedRelationship.cascadeCreate(),
																			   normalizedRelationship.cascadeUpdate(),
																			   normalizedRelationship.cascadeDelete(),
																			   normalizedRelationship.orphanDelete(),
																			   normalizedRelationship.hydrateOnFetch(),
																			   normalizedRelationship.generateNestedCreate(),
																			   normalizedRelationship.generateNestedUpdate());
																   })
		                                                           .orElseThrow())
		                                 .toList();
		var templateModel = TemplateModelFactory.create(model.packageName(), model.modelName(),
				model.genericTypeParameters(), model.properties(), configuration.genericTypes().domain(),
				configuration.genericTypes().persistence(), configuration.genericTypes().api(), Set.of(),
				configuration.historized(), relationships);
		var overwriteResolver = OverwriteResolver.with(configuration.overwrite());

		var files = templateProcessor.generateSourceCode(templateModel,
				TemplateSelector.with(
						resolveTemplateGroups(configuration),
						resolveTemplates(configuration),
						resolveTags(configuration),
						configuration.generation().excludeGroups(),
						resolveExcludedTemplates(configuration),
						resolveExcludedTags(configuration)));

		files.forEach((template, sourceCodeFile) ->
		{
			if (!shouldWrite(template, sourceCodeFile, model.contentRootPath(), configuration, overwriteResolver))
			{
				return;
			}
			var targetPath = resolveTargetPath(model.contentRootPath(), sourceCodeFile);
			var request = SourceCodeWriteRequest.from(model.contentRootPath(), sourceCodeFile.fileName(),
					sourceCodeFile.sourceCode().sourceCode(),
					overwriteResolver.shouldOverwrite(template, targetPath));

			sourceCodeFileWriter.writeSourceCode(request);
		});

		return 0;
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

	private Set<String> resolveExcludedTemplates(final CodeGenerationConfiguration configuration)
	{
		var templates = new java.util.LinkedHashSet<>(configuration.generation().excludeTemplates());
		applyRelationshipGenerationExclusions(templates, configuration);
		applyOwnershipExclusions(templates, configuration);
		return Set.copyOf(templates);
	}

	private Set<String> resolveTags(final CodeGenerationConfiguration configuration)
	{
		return configuration.generation().tags();
	}

	private Set<String> resolveExcludedTags(final CodeGenerationConfiguration configuration)
	{
		return configuration.generation().excludeTags();
	}

	private void applyRelationshipGenerationExclusions(
			final java.util.Set<String> templates,
			final CodeGenerationConfiguration configuration)
	{
		if (configuration.relationships().isEmpty())
		{
			templates.add("CrudRelationshipConfiguration");
		}
	}

	private void applyOwnershipExclusions(
			final java.util.Set<String> templates,
			final CodeGenerationConfiguration configuration)
	{
		var ownershipResolver = OwnershipResolver.with(configuration.ownership());
		TemplateMetadataRegistry.getAllTemplateMetadata().forEach((templateName, metadataConfig) ->
		{
			var template = new SourceCodeTemplate(templateName, templateName + ".ftl", false,
					metadataConfig.templateGroup(), metadataConfig.metadata());
			if (isUserOwned(template, ownershipResolver, configuration))
			{
				templates.add(templateName);
			}
		});
	}

	private boolean isUserOwned(
			final SourceCodeTemplate template,
			final OwnershipResolver ownershipResolver,
			final CodeGenerationConfiguration configuration)
	{
		return ownershipResolver.ownershipOf(template)
		                        .orElseGet(() -> defaultOwnershipFor(template.templateName(), configuration))
				== GeneratedArtifactOwnership.USER;
	}

	private GeneratedArtifactOwnership defaultOwnershipFor(
			final String templateName,
			final CodeGenerationConfiguration configuration)
	{
		if (BASE_MODEL_TEMPLATES.contains(templateName))
		{
			return configuration.ownership().baseModel();
		}
		if (DOMAIN_MODEL_TEMPLATES.contains(templateName))
		{
			return configuration.ownership().domainModel();
		}
		if (PERSISTENCE_MODEL_TEMPLATES.contains(templateName))
		{
			return configuration.ownership().persistenceModel();
		}
		if (API_MODEL_TEMPLATES.contains(templateName))
		{
			return configuration.ownership().apiModel();
		}
		return GeneratedArtifactOwnership.GENERATED;
	}

	private boolean shouldWrite(
			final SourceCodeTemplate template,
			final SourceCodeFile sourceCodeFile,
			final Path contentRootPath,
			final CodeGenerationConfiguration configuration,
			final OverwriteResolver overwriteResolver)
	{
		var targetPath = resolveTargetPath(contentRootPath, sourceCodeFile);
		return !Files.exists(targetPath) || overwriteResolver.shouldOverwrite(template, targetPath);
	}

	private Path resolveTargetPath(final Path contentRootPath, final SourceCodeFile sourceCodeFile)
	{
		var sourceCode = sourceCodeFile.sourceCode().sourceCode();
		var matcher = PACKAGE_PATTERN.matcher(sourceCode);
		if (!matcher.find())
		{
			return contentRootPath.resolve(sourceCodeFile.fileName());
		}
		return contentRootPath.resolve(matcher.group(1).replace('.', java.io.File.separatorChar))
		                      .resolve(sourceCodeFile.fileName());
	}

	CodeGenerationOrchestratorImpl(
			final DomainModelParser modelParser,
			final SourceCodeTemplateProcessor templateProcessor,
			final SourceCodeFileWriter sourceCodeFileWriter,
			final RelationshipGenerationConfigurationValidator relationshipConfigurationValidator)
	{
		this.modelParser = modelParser;
		this.templateProcessor = templateProcessor;
		this.sourceCodeFileWriter = sourceCodeFileWriter;
		this.relationshipConfigurationValidator = relationshipConfigurationValidator;
	}
}