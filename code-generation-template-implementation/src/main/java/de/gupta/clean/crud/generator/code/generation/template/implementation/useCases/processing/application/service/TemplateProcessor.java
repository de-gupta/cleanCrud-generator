package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;

import java.util.Map;

public interface TemplateProcessor
{
	SourceCodeFile process(final SourceCodeTemplate template, final Model model,
						   final Map<String, String> domainGenericTypes,
						   final Map<String, String> persistenceGenericTypes,
						   final Map<String, String> apiGenericTypes
	);
}