package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModel;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
final class SourceCodeTemplateProcessorServiceImpl implements SourceCodeTemplateProcessorService
{
	private final TemplateProcessor templateProcessor;
	private final TemplateRepository templateRepository;

	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final TemplateModel model,
																	  final TemplateSelector templateSelector)
	{
		return resolveTemplates(templateSelector)
				.stream()
				.collect(Collectors.toMap(
								Function.identity(),
								sct -> templateProcessor.process(sct, model)
						)
				);
	}

	private Set<SourceCodeTemplate> resolveTemplates(TemplateSelector selector)
	{
		// TODO: write real logic here
		return templateRepository.allTemplates();
	}

	SourceCodeTemplateProcessorServiceImpl(final TemplateProcessor templateProcessor,
										   final TemplateRepository templateRepository)
	{
		this.templateProcessor = templateProcessor;
		this.templateRepository = templateRepository;
	}
}