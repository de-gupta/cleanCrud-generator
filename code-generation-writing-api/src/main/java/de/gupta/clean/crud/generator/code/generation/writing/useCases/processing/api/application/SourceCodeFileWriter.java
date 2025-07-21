package de.gupta.clean.crud.generator.code.generation.writing.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.writing.domain.model.SourceCodeWriteRequest;

public interface SourceCodeFileWriter
{
	void writeSourceCode(final SourceCodeWriteRequest request);
}