package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.application.service.parsing;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.nio.file.Path;
import java.util.SequencedCollection;
import java.util.Set;

public interface CodeParser
{
	String typeName(final String sourceCodeFilePath);

	String packageName(final String sourceCodeFilePath);

	Path contentRootPath(final String sourceCodeFilePath);

	SequencedCollection<String> genericTypesParameters(final String sourceCodeFilePath);

	Set<Property> properties(final String sourceCodeFilePath);
}