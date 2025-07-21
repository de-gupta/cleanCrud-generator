package de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface SourceCodeTemplateProcessor
{
	default Optional<SourceCodeFile> generateSourceCode(final Model model, final SourceCodeTemplate sourceCodeTemplate)
	{
		return generateSourceCode(model, Set.of(sourceCodeTemplate)).values().stream().findFirst();
	}

	Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final Model model,
															   final Set<SourceCodeTemplate> sourceCodeTemplates);
}