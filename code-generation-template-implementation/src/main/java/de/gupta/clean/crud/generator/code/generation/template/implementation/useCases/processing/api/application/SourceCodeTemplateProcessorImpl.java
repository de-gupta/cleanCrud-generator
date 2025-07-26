package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.SourceCodeTemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.facade.SourceCodeTemplateProcessorServiceFacade;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
final class SourceCodeTemplateProcessorImpl implements SourceCodeTemplateProcessor
{
	private final SourceCodeTemplateProcessorServiceFacade service;

	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final Model model,
																	  final Set<SourceCodeTemplate> sourceCodeTemplates)
	{
		return service.generateSourceCode(model, sourceCodeTemplates);
	}

	SourceCodeTemplateProcessorImpl(final SourceCodeTemplateProcessorServiceFacade service)
	{
		this.service = service;
	}
}