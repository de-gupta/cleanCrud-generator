package de.gupta.clean.crud.generator.code.generation.model.api.domain.model;

public record SourceCodeFile(String fileName, SourceCode sourceCode)
{
	public static SourceCodeFile with(final String fileName, final SourceCode sourceCode)
	{
		return new SourceCodeFile(fileName, sourceCode);
	}
}