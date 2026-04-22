package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.List;
import java.util.SequencedCollection;
import java.util.Set;

public interface AggregateComposition
{
	Set<Property> properties();

	List<GeneratedRelationship> relationships();

	boolean hasRelationships();

	Set<String> relationshipPropertyNames();

	SequencedCollection<Property> standaloneProperties();

	SequencedCollection<Property> requiredProperties();

	String declaredBuilderPropertyType(Property property);
}
