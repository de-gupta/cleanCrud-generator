package de.gupta.clean.crud.generator.code.generation.writing.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.application.service.SourceCodeFileWriterService;
import de.gupta.commons.utility.javaLanguage.classes.ClassWritingUtility;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
final class SourceCodeFileWriterServiceImpl implements SourceCodeFileWriterService
{
	@Override
	public void writeSourceCode(final SourceCodeWriteRequest request)
	{
		Objects.requireNonNull(request, "request must not be null");
		if (request.contentRootPath() == null || request.fileName() == null || request.sourceCode() == null)
		{
			throw new IllegalArgumentException(
					"Source code write request must have non-null contentRootPath, fileName, and sourceCode");
		}
		ClassWritingUtility.writeClass(request.fileName(), request.sourceCode(), request.contentRootPath(),
				request.overwriteExistingFile());
	}
}