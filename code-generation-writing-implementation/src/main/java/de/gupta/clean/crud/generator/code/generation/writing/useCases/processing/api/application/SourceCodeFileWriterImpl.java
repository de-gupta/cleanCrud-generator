package de.gupta.clean.crud.generator.code.generation.writing.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.writing.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.useCases.processing.facade.SourceCodeFileWriterServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class SourceCodeFileWriterImpl implements SourceCodeFileWriter
{
	private final SourceCodeFileWriterServiceFacade service;

	@Override
	public int writeSourceCode(final SourceCodeWriteRequest request)
	{
		return service.writeSourceCode(request);
	}

	SourceCodeFileWriterImpl(final SourceCodeFileWriterServiceFacade service)
	{
		this.service = service;
	}
}