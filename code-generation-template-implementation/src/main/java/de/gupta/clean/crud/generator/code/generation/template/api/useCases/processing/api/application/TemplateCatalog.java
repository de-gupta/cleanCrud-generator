package de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;

import java.util.Set;

public interface TemplateCatalog
{
	Set<SourceCodeTemplate> allTemplates();
}
