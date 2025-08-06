package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModel;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.SourceCodeTemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.facade.SourceCodeTemplateProcessorServiceFacade;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
final class SourceCodeTemplateProcessorImpl implements SourceCodeTemplateProcessor
{
	private final SourceCodeTemplateProcessorServiceFacade service;

	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final TemplateModel model,
																	  final TemplateSelector templateSelector)
	{
		return service.generateSourceCode(model, templateSelector);
	}

	SourceCodeTemplateProcessorImpl(final SourceCodeTemplateProcessorServiceFacade service)
	{
		this.service = service;
	}
}