package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.OverwriteResolver;
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
			TemplateGroup.USE_CASE_DELETE.name(),
			TemplateGroup.USE_CASE_POST_COMMIT.name(),
			TemplateGroup.USE_CASE_SUBPROCESS.name());
	private final GenerationContextResolver generationContextResolver;
	private final SourceCodeTemplateProcessor templateProcessor;
	private final SourceCodeFileWriter sourceCodeFileWriter;
	private final RelationshipGenerationConfigurationValidator relationshipConfigurationValidator;
	private final GeneratedRelationshipFactory generatedRelationshipFactory;
	private final RelationshipConcreteTypeResolver relationshipConcreteTypeResolver;
	private final GenerationExclusionPolicy generationExclusionPolicy;
	private final SourceCodeTargetPathResolver sourceCodeTargetPathResolver;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		var resolvedRequest = generationContextResolver.resolve(configuration);
		var model = resolvedRequest.model();
		var effectiveConfiguration = resolvedRequest.configuration();
		relationshipConfigurationValidator.validate(model, effectiveConfiguration.relationships());
		var relationships = generatedRelationshipFactory.create(model, effectiveConfiguration.relationships());
		var layerTypes = relationshipConcreteTypeResolver.merge(effectiveConfiguration.genericTypes(), relationships);
		var templateModel = TemplateModelFactory.create(model.packageName(), model.modelName(),
				model.genericTypeParameters(), model.properties(), layerTypes.domain(),
				layerTypes.persistence(), layerTypes.api(), Set.of(),
				effectiveConfiguration.historized(), relationships,
				effectiveConfiguration.rootAggregateIds().apiIdType(),
				effectiveConfiguration.rootAggregateIds().domainIdType(),
				effectiveConfiguration.rootAggregateIds().persistenceIdType(),
				effectiveConfiguration.postCommitHooks().save(),
				effectiveConfiguration.postCommitHooks().update(),
				effectiveConfiguration.postCommitHooks().delete(),
				effectiveConfiguration.subprocesses().save(),
				effectiveConfiguration.subprocesses().update(),
				effectiveConfiguration.subprocesses().delete());
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
			final GenerationContextResolver generationContextResolver,
			final SourceCodeTemplateProcessor templateProcessor,
			final SourceCodeFileWriter sourceCodeFileWriter,
			final RelationshipGenerationConfigurationValidator relationshipConfigurationValidator,
			final GeneratedRelationshipFactory generatedRelationshipFactory,
			final RelationshipConcreteTypeResolver relationshipConcreteTypeResolver,
			final GenerationExclusionPolicy generationExclusionPolicy,
			final SourceCodeTargetPathResolver sourceCodeTargetPathResolver)
	{
		this.generationContextResolver = generationContextResolver;
		this.templateProcessor = templateProcessor;
		this.sourceCodeFileWriter = sourceCodeFileWriter;
		this.relationshipConfigurationValidator = relationshipConfigurationValidator;
		this.generatedRelationshipFactory = generatedRelationshipFactory;
		this.relationshipConcreteTypeResolver = relationshipConcreteTypeResolver;
		this.generationExclusionPolicy = generationExclusionPolicy;
		this.sourceCodeTargetPathResolver = sourceCodeTargetPathResolver;
	}
}
