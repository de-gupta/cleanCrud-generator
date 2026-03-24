package de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;

public interface SourceCodeFileWriter
{
	void writeSourceCode(final SourceCodeWriteRequest request);
}