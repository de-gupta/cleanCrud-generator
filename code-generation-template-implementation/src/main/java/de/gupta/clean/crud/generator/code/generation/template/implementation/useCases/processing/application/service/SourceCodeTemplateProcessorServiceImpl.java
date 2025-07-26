package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
final class SourceCodeTemplateProcessorServiceImpl implements SourceCodeTemplateProcessorService
{
	private final TemplateProcessor templateProcessor;

	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final Model model,
																	  final Set<SourceCodeTemplate> sourceCodeTemplates)
	{
		return sourceCodeTemplates.stream()
								  .collect(Collectors.toMap(
												  Function.identity(),
												  sct -> templateProcessor.process(sct, model)
										  )
								  );

	}

	SourceCodeTemplateProcessorServiceImpl(final TemplateProcessor templateProcessor)
	{
		this.templateProcessor = templateProcessor;
	}
}