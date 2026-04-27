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
		return DomainProjectionSupport.imports(composition, genericImports());
	}

	@Override
	public Set<String> modelImports()
	{
		return DomainProjectionSupport.modelImports(composition, genericImports());
	}

	@Override
	public Set<String> createImports()
	{
		return DomainProjectionSupport.createImports(composition, genericImports());
	}

	@Override
	public Set<String> updateImports()
	{
		return DomainProjectionSupport.updateImports(composition, genericImports());
	}

	@Override
	public Set<String> responseImports()
	{
		return DomainProjectionSupport.responseImports(composition, genericImports());
	}

	@Override
	public String concreteType(final String genericType)
	{
		return TypeNameSupport.concreteType(concreteTypes, genericType);
	}

	@Override
	public String resolvedType(final String declaredType)
	{
		return TypeNameSupport.resolvedType(concreteTypes, declaredType);
	}

	@Override
	public String boxedResolvedType(final String declaredType)
	{
		return TypeNameSupport.boxedResolvedType(concreteTypes, declaredType);
	}

	@Override
	public String propertyType(final Property property)
	{
		return TypeNameSupport.valueType(concreteTypes, property);
	}

	@Override
	public String builderPropertyType(final Property property)
	{
		return TypeNameSupport.valueType(concreteTypes, property);
	}
}
