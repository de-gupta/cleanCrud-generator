package de.gupta.clean.crud.generator.code.generation.writing.api.domain.model;

public record SourceCodeWriteRequest(
		String contentRootPath,
		String fileName,
		String sourceCode,
		boolean overwriteExistingFile
)
{
	public static SourceCodeWriteRequest from(final String contentRootPath, final String fileName,
											  final String sourceCode, final boolean overwriteExistingFile)
	{
		return new SourceCodeWriteRequest(contentRootPath, fileName, sourceCode, overwriteExistingFile);
	}
}