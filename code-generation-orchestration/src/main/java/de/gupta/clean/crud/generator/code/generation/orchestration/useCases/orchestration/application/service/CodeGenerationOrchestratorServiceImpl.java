package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.application.service;

import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.SourceCodeTemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.api.application.SourceCodeFileWriter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
final class CodeGenerationOrchestratorServiceImpl implements CodeGenerationOrchestratorService
{
	private final DomainModelParser modelParser;
	private final SourceCodeTemplateProcessor templateProcessor;
	private final SourceCodeFileWriter sourceCodeFileWriter;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		var model = modelParser.parseDomainModel(configuration.domainModelSourceCodeFilePath());

		// TODO: determine the templates using options
		var files = templateProcessor.generateSourceCode(model, Set.of());

		files.forEach((template, sourceCodeFile) ->
				{
					var request = SourceCodeWriteRequest.from(model.contentRootPath(), sourceCodeFile.fileName(),
							sourceCodeFile.sourceCode().sourceCode(),
							template.forceOverwrite() || configuration.forceOverwrite());

					sourceCodeFileWriter.writeSourceCode(request);
				}
		);

		return 0;
	}

	CodeGenerationOrchestratorServiceImpl(final DomainModelParser modelParser,
										  final SourceCodeTemplateProcessor templateProcessor,
										  final SourceCodeFileWriter sourceCodeFileWriter)
	{
		this.modelParser = modelParser;
		this.templateProcessor = templateProcessor;
		this.sourceCodeFileWriter = sourceCodeFileWriter;
	}
}