package de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModel;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;

import java.util.Map;

public interface SourceCodeTemplateProcessor
{
	Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(final TemplateModel model,
															   final TemplateSelector templateSelector);
}