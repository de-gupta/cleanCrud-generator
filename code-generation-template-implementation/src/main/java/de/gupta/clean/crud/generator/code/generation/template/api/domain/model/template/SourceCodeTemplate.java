package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;

public record SourceCodeTemplate(
		String templateName,
		String templatePath,
		boolean forceOverwrite,
		TemplateGroup templateGroup,
		TemplateMetadata metadata)
{
	public String templateFileName()
	{
		return templatePath;
	}

	public String templateSourceCodeFilename()
	{
		return templateName + ".java";
	}
}