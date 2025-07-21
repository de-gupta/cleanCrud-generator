package de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.facade;

import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;

public interface SourceCodeFileWriterServiceFacade
{
	void writeSourceCode(final SourceCodeWriteRequest request);
}