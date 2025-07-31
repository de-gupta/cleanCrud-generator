package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.repository;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;

import java.util.Set;

public interface TemplateRepository
{
	Set<SourceCodeTemplate> allTemplates();

	SourceCodeTemplate findTemplateByName(String templateName);
}