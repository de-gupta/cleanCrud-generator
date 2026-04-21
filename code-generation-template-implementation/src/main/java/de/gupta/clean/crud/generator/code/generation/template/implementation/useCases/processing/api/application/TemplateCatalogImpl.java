package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.TemplateCatalog;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.repository.TemplateRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
final class TemplateCatalogImpl implements TemplateCatalog
{
	private final TemplateRepository templateRepository;

	@Override
	public Set<SourceCodeTemplate> allTemplates()
	{
		return templateRepository.allTemplates();
	}

	TemplateCatalogImpl(final TemplateRepository templateRepository)
	{
		this.templateRepository = templateRepository;
	}
}
