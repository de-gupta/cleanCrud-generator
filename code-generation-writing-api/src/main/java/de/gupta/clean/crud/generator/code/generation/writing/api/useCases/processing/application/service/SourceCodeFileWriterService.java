package de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;

public interface SourceCodeFileWriterService
{
	void writeSourceCode(final SourceCodeWriteRequest request);
}