package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.Map;
import java.util.Set;

record ApiProjectionImpl(
		AggregateComposition composition,
		Map<String, String> concreteTypes,
		Set<String> domainGenericImports)
		implements ApiProjection
{
	@Override
	public Set<String> genericImports()
	{
		return ApiProjectionSupport.genericImports(domainGenericImports, concreteTypes);
	}

	@Override
	public Set<String> imports()
	{
		return ApiProjectionSupport.imports(composition, domainGenericImports, concreteTypes);
	}

	@Override
	public Set<String> createImports()
	{
		return ApiProjectionSupport.createImports(composition, domainGenericImports, concreteTypes);
	}

	@Override
	public Set<String> updateImports()
	{
		return ApiProjectionSupport.updateImports(composition, domainGenericImports, concreteTypes);
	}

	@Override
	public Set<String> responseImports()
	{
		return ApiProjectionSupport.responseImports(composition, domainGenericImports, concreteTypes);
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
}
