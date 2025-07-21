package de.gupta.clean.crud.generator.code.generation.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
final class SourceCodeTemplateProcessorServiceImpl implements SourceCodeTemplateProcessorService
{
	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final Model model,
																	  final Set<SourceCodeTemplate> sourceCodeTemplates)
	{
		return Map.of();
	}
}