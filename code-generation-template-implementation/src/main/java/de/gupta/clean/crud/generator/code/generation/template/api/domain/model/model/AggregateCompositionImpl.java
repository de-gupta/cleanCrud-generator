package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

record AggregateCompositionImpl(Set<Property> properties, List<GeneratedRelationship> relationships)
		implements AggregateComposition
{
	@Override
	public boolean hasRelationships()
	{
		return !relationships.isEmpty();
	}

	@Override
	public Set<String> relationshipPropertyNames()
	{
		return relationships.stream()
		                    .map(GeneratedRelationship::propertyName)
		                    .collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public java.util.SequencedCollection<Property> standaloneProperties()
	{
		return properties.stream().filter(property -> !relationshipPropertyNames().contains(property.name())).toList();
	}

	@Override
	public java.util.SequencedCollection<Property> requiredProperties()
	{
		return standaloneProperties().stream().filter(property -> !property.optional()).toList();
	}

	@Override
	public String declaredBuilderPropertyType(final Property property)
	{
		return property.paramType();
	}
}
