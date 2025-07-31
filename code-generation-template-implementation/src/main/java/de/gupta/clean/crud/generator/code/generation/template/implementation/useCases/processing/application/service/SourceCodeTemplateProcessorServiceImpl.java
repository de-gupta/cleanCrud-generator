package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.TemplateSelector;
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
																	  final TemplateSelector templateSelector,
																	  final Map<String, String> domainGenericTypes,
																	  final Map<String, String> persistenceGenericTypes,
																	  final Map<String, String> apiGenericTypes)
	{
		return resolveTemplates(templateSelector)
				.stream()
				.collect(Collectors.toMap(
								Function.identity(),
								sct -> templateProcessor.process(sct, model, domainGenericTypes,
										persistenceGenericTypes, apiGenericTypes)
						)
				);
	}

	private Set<SourceCodeTemplate> resolveTemplates(TemplateSelector selector)
	{
		// TODO
		return Set.of();
	}

	SourceCodeTemplateProcessorServiceImpl(final TemplateProcessor templateProcessor)
	{
		this.templateProcessor = templateProcessor;
	}
}