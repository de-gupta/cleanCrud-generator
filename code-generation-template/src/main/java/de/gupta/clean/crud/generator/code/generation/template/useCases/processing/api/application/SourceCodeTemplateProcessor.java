package de.gupta.clean.crud.generator.code.generation.template.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.orchestration.domain.model.SourceCodeTemplateSelection;

public interface SourceCodeTemplateProcessor
{
	// TODO: change return type to be source+fileName
	String generateSourceCode(final Model model, final SourceCodeTemplateSelection templateSelection);
}