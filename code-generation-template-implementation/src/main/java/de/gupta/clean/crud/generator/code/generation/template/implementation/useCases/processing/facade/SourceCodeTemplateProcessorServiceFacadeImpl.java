package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.facade;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModel;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service.SourceCodeTemplateProcessorService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
final class SourceCodeTemplateProcessorServiceFacadeImpl implements SourceCodeTemplateProcessorServiceFacade
{
	private final SourceCodeTemplateProcessorService service;

	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final TemplateModel model,
																	  final TemplateSelector templateSelector)
	{
		return service.generateSourceCode(model, templateSelector);
	}

	SourceCodeTemplateProcessorServiceFacadeImpl(final SourceCodeTemplateProcessorService service)
	{
		this.service = service;
	}
}