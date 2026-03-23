package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;

public record SourceCodeTemplate(
		String templateName,
		boolean forceOverwrite,
		TemplateGroup templateGroup,
		TemplateMetadata metadata)
{
	public String templateFileName()
	{
		return templateName + ".ftl";
	}

	public String templateSourceCodeFilename()
	{
		return templateName + ".java";
	}
}