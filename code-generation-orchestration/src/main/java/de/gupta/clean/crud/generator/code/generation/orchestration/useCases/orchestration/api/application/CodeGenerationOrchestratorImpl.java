package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModelFactory;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.SourceCodeTemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.api.application.SourceCodeFileWriter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
final class CodeGenerationOrchestratorImpl implements CodeGenerationOrchestrator
{
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
				TemplateSelector.with(configuration.templateGroups()));

		files.forEach((template, sourceCodeFile) ->
		{
			var request = SourceCodeWriteRequest.from(model.contentRootPath(), sourceCodeFile.fileName(),
					sourceCodeFile.sourceCode().sourceCode(),
					template.forceOverwrite() || configuration.forceOverwrite());

			sourceCodeFileWriter.writeSourceCode(request);
		});

		return 0;
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