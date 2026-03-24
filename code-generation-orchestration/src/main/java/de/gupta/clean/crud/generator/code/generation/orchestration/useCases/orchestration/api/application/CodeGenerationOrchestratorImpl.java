package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
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
			TemplateGroup.API.name(),
			TemplateGroup.DOMAIN.name(),
			TemplateGroup.INFRASTRUCTURE.name(),
			TemplateGroup.USE_CASES.name());
	private static final Pattern PACKAGE_PATTERN = Pattern.compile("(?m)^\\s*package\\s+([a-zA-Z_][\\w.]*)\\s*;");
	private final DomainModelParser modelParser;
	private final SourceCodeTemplateProcessor templateProcessor;
	private final SourceCodeFileWriter sourceCodeFileWriter;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		var model = modelParser.parseDomainModel(configuration.domainModelSourceCodeFilePath());
		var templateModel = TemplateModelFactory.create(model.packageName(), model.modelName(),
				model.genericTypeParameters(), model.properties(), configuration.domainConcreteTypes(),
				configuration.persistenceConcreteTypes(), configuration.apiConcreteTypes(), Set.of(),
				configuration.historized());

		var files = templateProcessor.generateSourceCode(templateModel,
				TemplateSelector.with(resolveTemplateGroups(configuration)));

		files.forEach((template, sourceCodeFile) ->
		{
			if (!shouldWrite(template, sourceCodeFile, model.contentRootPath(), configuration))
			{
				return;
			}
			var request = SourceCodeWriteRequest.from(model.contentRootPath(), sourceCodeFile.fileName(),
					sourceCodeFile.sourceCode().sourceCode(),
					template.forceOverwrite() || configuration.forceOverwrite());

			sourceCodeFileWriter.writeSourceCode(request);
		});

		return 0;
	}

	private Set<String> resolveTemplateGroups(final CodeGenerationConfiguration configuration)
	{
		return configuration.templateGroups().isEmpty() ? DEFAULT_TEMPLATE_GROUPS : configuration.templateGroups();
	}

	private boolean shouldWrite(
			final SourceCodeTemplate template,
			final SourceCodeFile sourceCodeFile,
			final Path contentRootPath,
			final CodeGenerationConfiguration configuration)
	{
		if (template.templateGroup() != TemplateGroup.COMMON)
		{
			return true;
		}
		if (Boolean.FALSE.equals(configuration.generateCommonFiles()))
		{
			return false;
		}
		if (Boolean.TRUE.equals(configuration.generateCommonFiles()))
		{
			return true;
		}
		return !Files.exists(resolveTargetPath(contentRootPath, sourceCodeFile));
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