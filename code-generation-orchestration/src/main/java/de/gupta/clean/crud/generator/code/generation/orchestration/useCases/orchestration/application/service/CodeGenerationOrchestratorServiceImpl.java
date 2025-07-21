package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.application.service;

import de.gupta.clean.crud.generator.code.generation.api.useCases.parsing.api.application.DomainModelParser;
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
		// TODO: write code to parse model, generate code from templates and then write
		// TODO: parse content of the incoming file as source code
		var model = modelParser.parseDomainModel(null);

		// TODO: determine the templates using options
		var files = templateProcessor.generateSourceCode(model, Set.of());

		final String contentRoot = null; // TODO: either from config or derived from model file
		final boolean overwrite = false; // TODO: from config, overridden for specific templates

		files.forEach((template, sourceCodeFile) ->
				{
					SourceCodeWriteRequest request = SourceCodeWriteRequest.from(contentRoot, sourceCodeFile.fileName(),
							sourceCodeFile.sourceCode().sourceCode(), template.forceOverwrite() || overwrite);
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