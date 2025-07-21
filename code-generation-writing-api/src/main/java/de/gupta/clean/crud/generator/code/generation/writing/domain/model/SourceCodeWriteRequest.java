package de.gupta.clean.crud.generator.code.generation.writing.domain.model;

public record SourceCodeWriteRequest(
		String contentRoot,
		String packageName,
		String fileName,
		String sourceCode,
		boolean overwriteExistingFile
)
{
}