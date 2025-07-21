package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.application.service;

import de.gupta.clean.crud.generator.code.generation.model.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.template.useCases.processing.api.application.SourceCodeTemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.writing.useCases.processing.api.application.SourceCodeFileWriter;
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
		var model = modelParser.parseDomainModel(null);
		var files =
				templateProcessor.generateSourceCode(model, Set.of());
		sourceCodeFileWriter.writeSourceCode(null);
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