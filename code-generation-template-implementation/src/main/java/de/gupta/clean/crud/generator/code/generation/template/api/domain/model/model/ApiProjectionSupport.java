package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class ApiProjectionSupport
{
	static Set<String> genericImports(final Set<String> domainGenericImports, final Map<String, String> concreteTypes)
	{
		return TypeNameSupport.combineImports(domainGenericImports,
				TypeNameSupport.inferredConcreteTypeImports(concreteTypes));
	}

	static Set<String> imports(
			final AggregateComposition composition,
			final Set<String> domainGenericImports,
			final Map<String, String> concreteTypes)
	{
		return TypeNameSupport.combineImports(
				genericImports(domainGenericImports, concreteTypes),
				TypeNameSupport.propertyImports(composition.properties()));
	}

	static Set<String> createImports(
			final AggregateComposition composition,
			final Set<String> domainGenericImports,
			final Map<String, String> concreteTypes)
	{
		return TypeNameSupport.combineImports(
				imports(composition, domainGenericImports, concreteTypes),
				apiCreateRelationshipImports(composition.relationships()));
	}

	static Set<String> updateImports(
			final AggregateComposition composition,
			final Set<String> domainGenericImports,
			final Map<String, String> concreteTypes)
	{
		return TypeNameSupport.combineImports(
				imports(composition, domainGenericImports, concreteTypes),
				apiUpdateRelationshipImports(composition.relationships()));
	}

	static Set<String> responseImports(
			final AggregateComposition composition,
			final Set<String> domainGenericImports,
			final Map<String, String> concreteTypes)
	{
		return TypeNameSupport.combineImports(
				imports(composition, domainGenericImports, concreteTypes),
				apiResponseRelationshipImports(composition.relationships()));
	}

	private static Set<String> apiCreateRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			if (relationship.referenced())
			{
				imports.addAll(TypeNameSupport.importsForResolvedType(relationship.satelliteApiIdType()));
			}
			else
			{
				imports.add(relationship.createImport());
			}
		});
		imports.remove("");
		return imports;
	}

	private static Set<String> apiUpdateRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			imports.addAll(TypeNameSupport.importsForResolvedType(relationship.satelliteApiIdType()));
			if (relationship.owned())
			{
				imports.add(relationship.updatePatchImport());
			}
		});
		imports.remove("");
		return imports;
	}

	private static Set<String> apiResponseRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship -> imports.add(relationship.responseImport()));
		imports.remove("");
		return imports;
	}

	private ApiProjectionSupport()
	{
	}
}
