package de.gupta.clean.crud.generator.code.generation.writing.api.domain.model;

import java.nio.file.Path;

public record SourceCodeWriteRequest(
		Path contentRootPath,
		String fileName,
		String sourceCode,
		boolean overwriteExistingFile
)
{
	public static SourceCodeWriteRequest from(final Path contentRootPath, final String fileName,
											  final String sourceCode, final boolean overwriteExistingFile)
	{
		return new SourceCodeWriteRequest(contentRootPath, fileName, sourceCode, overwriteExistingFile);
	}
}