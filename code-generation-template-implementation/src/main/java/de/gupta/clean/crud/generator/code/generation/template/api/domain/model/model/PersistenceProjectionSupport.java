package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

final class PersistenceProjectionSupport
{
	static Set<String> genericImports(final Set<String> domainGenericImports, final Map<String, String> concreteTypes)
	{
		return TypeNameSupport.combineImports(domainGenericImports,
				TypeNameSupport.inferredConcreteTypeImports(concreteTypes));
	}

	static Set<String> imports(
			final AggregateDescriptor aggregate,
			final AggregateComposition composition,
			final Set<String> domainGenericImports,
			final Map<String, String> concreteTypes)
	{
		var imports = new LinkedHashSet<>(TypeNameSupport.combineImports(
				genericImports(domainGenericImports, concreteTypes),
				TypeNameSupport.propertyImports(composition.properties())));
		imports.addAll(TypeNameSupport.importsForResolvedType(aggregate.rootPersistenceIdType()));
		composition.relationships().forEach(relationship ->
		{
			imports.addAll(TypeNameSupport.importsForResolvedType(relationship.satellitePersistenceIdType()));
			if (relationship.many())
			{
				imports.add("java.util.Collection");
			}
			if (relationship.optional())
			{
				imports.add("java.util.Optional");
			}
		});
		return imports;
	}

	static Set<String> interfaceImports(
			final AggregateDescriptor aggregate,
			final AggregateComposition composition,
			final Set<String> domainGenericImports,
			final Map<String, String> concreteTypes)
	{
		var imports = new LinkedHashSet<>(genericImports(domainGenericImports, concreteTypes));
		imports.addAll(TypeNameSupport.importsForResolvedType(aggregate.rootPersistenceIdType()));
		composition.standaloneProperties().forEach(property ->
				imports.addAll(TypeNameSupport.importsForResolvedType(
						TypeNameSupport.resolvedType(concreteTypes, property.baseType()))));
		composition.relationships().forEach(relationship ->
		{
			imports.addAll(TypeNameSupport.importsForResolvedType(relationship.satellitePersistenceIdType()));
			if (relationship.many())
			{
				imports.add("java.util.Collection");
			}
		});
		imports.remove("");
		return imports;
	}

	private PersistenceProjectionSupport()
	{
	}
}
