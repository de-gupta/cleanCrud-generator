package de.gupta.clean.crud.generator.code.generation.writing.implementation.useCases.processing.facade;

import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.application.service.SourceCodeFileWriterService;
import de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.facade.SourceCodeFileWriterServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class SourceCodeFileWriterServiceFacadeImpl implements SourceCodeFileWriterServiceFacade
{
	private final SourceCodeFileWriterService service;

	@Override
	public void writeSourceCode(final SourceCodeWriteRequest request)
	{
		service.writeSourceCode(request);
	}

	SourceCodeFileWriterServiceFacadeImpl(final SourceCodeFileWriterService service)
	{
		this.service = service;
	}
}