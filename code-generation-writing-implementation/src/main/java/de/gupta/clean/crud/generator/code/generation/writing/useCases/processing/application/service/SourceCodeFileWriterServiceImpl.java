package de.gupta.clean.crud.generator.code.generation.writing.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.writing.domain.model.SourceCodeWriteRequest;
import de.gupta.commons.utility.io.FileWritingUtility;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
final class SourceCodeFileWriterServiceImpl implements SourceCodeFileWriterService
{
	private static final String SEPARATOR = File.separator;

	@Override
	public void writeSourceCode(final SourceCodeWriteRequest request)
	{
		FileWritingUtility.writeFileAndCreateDirectory(
				createPath(request.contentRootPath(), request.packageName(), request.fileName()),
				request.sourceCode(),
				request.overwriteExistingFile()
		);
	}

	private String createPath(final String contentRootPath, final String packageName, final String fileName)
	{
		return contentRootPath + SEPARATOR + packageName.replace(".", SEPARATOR) + SEPARATOR + fileName;
	}
}