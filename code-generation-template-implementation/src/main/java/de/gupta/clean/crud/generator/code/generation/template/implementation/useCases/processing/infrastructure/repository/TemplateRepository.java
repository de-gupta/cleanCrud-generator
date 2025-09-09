package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.repository;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;

import java.util.Map;
import java.util.Set;

public interface TemplateRepository
{
	Set<SourceCodeTemplate> allTemplates();

	SourceCodeTemplate findTemplateByName(String templateName);

	Set<SourceCodeTemplate> findTemplatesByGroup(TemplateGroup group);

	Set<SourceCodeTemplate> findTemplatesByGroups(Set<TemplateGroup> groups);

	Map<TemplateGroup, Set<SourceCodeTemplate>> templateGroups();
}