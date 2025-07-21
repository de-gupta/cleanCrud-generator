package de.gupta.clean.crud.generator.code.generation.writing.domain.model;

public record SourceCodeWriteRequest(
		String contentRootPath,
		String packageName,
		String fileName,
		String sourceCode,
		boolean overwriteExistingFile
)
{
}