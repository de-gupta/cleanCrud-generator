package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.Map;
import java.util.Set;

record DomainProjectionImpl(
		AggregateComposition composition,
		Map<String, String> concreteTypes,
		Set<String> genericImports)
		implements DomainProjection
{
	@Override
	public Set<String> imports()
	{
		return ProjectionSupport.combineImports(
				genericImports(),
				ProjectionSupport.combineImports(
						ProjectionSupport.propertyImports(composition.properties()),
						ProjectionSupport.relationshipImports(composition.relationships())));
	}

	@Override
	public String concreteType(final String genericType)
	{
		return ProjectionSupport.concreteType(concreteTypes, genericType);
	}

	@Override
	public String resolvedType(final String declaredType)
	{
		return ProjectionSupport.resolvedType(concreteTypes, declaredType);
	}

	@Override
	public String boxedResolvedType(final String declaredType)
	{
		return ProjectionSupport.boxedResolvedType(concreteTypes, declaredType);
	}

	@Override
	public String propertyType(final Property property)
	{
		return ProjectionSupport.valueType(concreteTypes, property);
	}

	@Override
	public String builderPropertyType(final Property property)
	{
		return ProjectionSupport.valueType(concreteTypes, property);
	}
}
