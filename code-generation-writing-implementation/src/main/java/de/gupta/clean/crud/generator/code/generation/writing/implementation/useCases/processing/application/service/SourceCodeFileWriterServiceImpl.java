package de.gupta.clean.crud.generator.code.generation.writing.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.writing.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.useCases.processing.application.service.SourceCodeFileWriterService;
import de.gupta.commons.utility.javaLanguage.classes.ClassWritingUtility;
import org.springframework.stereotype.Service;

@Service
final class SourceCodeFileWriterServiceImpl implements SourceCodeFileWriterService
{
	@Override
	public void writeSourceCode(final SourceCodeWriteRequest request)
	{
		ClassWritingUtility.writeClass(request.fileName(), request.sourceCode(), request.contentRootPath(),
				request.overwriteExistingFile());
	}
}