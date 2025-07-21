package de.gupta.clean.crud.generator.code.generation.writing.useCases.processing.facade;

import de.gupta.clean.crud.generator.code.generation.writing.domain.model.SourceCodeWriteRequest;

public interface SourceCodeFileWriterServiceFacade
{
	int writeSourceCode(final SourceCodeWriteRequest request);
}