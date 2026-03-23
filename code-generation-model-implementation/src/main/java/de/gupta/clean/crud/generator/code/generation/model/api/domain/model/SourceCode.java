package de.gupta.clean.crud.generator.code.generation.model.api.domain.model;

public record SourceCode(String sourceCode)
{
	public static SourceCode with(final String sourceCode)
	{
		return new SourceCode(sourceCode);
	}
}