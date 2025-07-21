package de.gupta.clean.crud.generator.code.generation.implementation.useCases.processing.facade;

import de.gupta.clean.crud.generator.code.generation.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.implementation.useCases.processing.application.service.SourceCodeTemplateProcessorService;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
final class SourceCodeTemplateProcessorServiceFacadeImpl implements SourceCodeTemplateProcessorServiceFacade
{
	private final SourceCodeTemplateProcessorService service;

	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final Model model,
																	  final Set<SourceCodeTemplate> sourceCodeTemplates)
	{
		return service.generateSourceCode(model, sourceCodeTemplates);
	}

	SourceCodeTemplateProcessorServiceFacadeImpl(final SourceCodeTemplateProcessorService service)
	{
		this.service = service;
	}
}