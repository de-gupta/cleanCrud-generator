package de.gupta.clean.crud.generator.code.generation.writing.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.writing.domain.model.SourceCodeWriteRequest;

public interface SourceCodeFileWriterService
{
	void writeSourceCode(final SourceCodeWriteRequest request);
}