package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.TemplateSelector;

import java.util.Map;

public interface SourceCodeTemplateProcessorService
{
	Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final Model model,
															   final TemplateSelector templateSelector,
															   final Map<String, String> domainGenericTypes,
															   final Map<String, String> persistenceGenericTypes,
															   final Map<String, String> apiGenericTypes
	);
}