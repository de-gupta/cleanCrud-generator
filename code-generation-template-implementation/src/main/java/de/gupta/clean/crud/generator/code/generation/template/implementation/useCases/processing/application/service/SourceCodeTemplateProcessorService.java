package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;

import java.util.Map;
import java.util.Set;

public interface SourceCodeTemplateProcessorService
{
	Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final Model model,
															   final Set<SourceCodeTemplate> sourceCodeTemplates);
}