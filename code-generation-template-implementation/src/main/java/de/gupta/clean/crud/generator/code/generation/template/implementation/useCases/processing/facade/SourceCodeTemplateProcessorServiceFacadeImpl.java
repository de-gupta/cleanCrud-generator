package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.facade;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service.SourceCodeTemplateProcessorService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
final class SourceCodeTemplateProcessorServiceFacadeImpl implements SourceCodeTemplateProcessorServiceFacade
{
	private final SourceCodeTemplateProcessorService service;

	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final Model model,
																	  final TemplateSelector templateSelector,
																	  final Map<String, String> domainGenericTypes,
																	  final Map<String, String> persistenceGenericTypes,
																	  final Map<String, String> apiGenericTypes)
	{
		return service.generateSourceCode(model, templateSelector, domainGenericTypes, persistenceGenericTypes, apiGenericTypes);
	}

	SourceCodeTemplateProcessorServiceFacadeImpl(final SourceCodeTemplateProcessorService service)
	{
		this.service = service;
	}
}