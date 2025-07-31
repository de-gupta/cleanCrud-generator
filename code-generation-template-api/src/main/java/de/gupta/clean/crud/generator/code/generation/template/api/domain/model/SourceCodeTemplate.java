package de.gupta.clean.crud.generator.code.generation.template.api.domain.model;

public record SourceCodeTemplate(
		String templateName,
		boolean forceOverwrite
)
{
	public String templateFileName()
	{
		// TODO
		return templateName + ".ftl";
	}
}