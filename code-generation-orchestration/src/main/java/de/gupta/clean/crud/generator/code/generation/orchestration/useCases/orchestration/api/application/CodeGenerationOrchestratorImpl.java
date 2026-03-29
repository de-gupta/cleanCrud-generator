package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership;
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
import java.util.regex.Pattern;

@Component
final class CodeGenerationOrchestratorImpl implements CodeGenerationOrchestrator
{
	private static final Set<String> DEFAULT_TEMPLATE_GROUPS = Set.of(
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

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		var model = modelParser.parseDomainModel(configuration.inputs().baseModelSourceCodeFilePath());
		var templateModel = TemplateModelFactory.create(model.packageName(), model.modelName(),
				model.genericTypeParameters(), model.properties(), configuration.genericTypes().domain(),
				configuration.genericTypes().persistence(), configuration.genericTypes().api(), Set.of(),
				configuration.historized());
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

	private void applyOwnershipExclusions(
			final java.util.Set<String> templates,
			final CodeGenerationConfiguration configuration)
	{
		if (configuration.ownership().baseModel() == GeneratedArtifactOwnership.USER)
		{
			templates.addAll(BASE_MODEL_TEMPLATES);
		}
		if (configuration.ownership().domainModel() == GeneratedArtifactOwnership.USER)
		{
			templates.addAll(DOMAIN_MODEL_TEMPLATES);
		}
		if (configuration.ownership().persistenceModel() == GeneratedArtifactOwnership.USER)
		{
			templates.addAll(PERSISTENCE_MODEL_TEMPLATES);
		}
		if (configuration.ownership().apiModel() == GeneratedArtifactOwnership.USER)
		{
			templates.addAll(API_MODEL_TEMPLATES);
		}
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
			final SourceCodeFileWriter sourceCodeFileWriter)
	{
		this.modelParser = modelParser;
		this.templateProcessor = templateProcessor;
		this.sourceCodeFileWriter = sourceCodeFileWriter;
	}
}