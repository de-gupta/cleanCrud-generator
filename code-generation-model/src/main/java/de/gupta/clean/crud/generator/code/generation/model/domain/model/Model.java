package de.gupta.clean.crud.generator.code.generation.model.domain.model;

import java.util.Map;
import java.util.SequencedCollection;
import java.util.Set;

public record Model(
		String modelName,
		String packageName,
		SequencedCollection<String> genericTypeParameters,
		Map<String, ConcreteType> domainGenericTypes,
		Map<String, ConcreteType> persistenceGenericTypes,
		Map<String, ConcreteType> apiGenericTypes,
		Set<Property> properties
)
{
}