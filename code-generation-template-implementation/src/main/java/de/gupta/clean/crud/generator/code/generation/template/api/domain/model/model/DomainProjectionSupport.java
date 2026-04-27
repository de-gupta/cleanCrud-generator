package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class DomainProjectionSupport
{
	private static final String IDENTIFIED_MODEL_IMPORT =
			"de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel";

	static Set<String> imports(final AggregateComposition composition, final Set<String> genericImports)
	{
		return TypeNameSupport.combineImports(genericImports,
				TypeNameSupport.propertyImports(composition.properties()));
	}

	static Set<String> modelImports(final AggregateComposition composition, final Set<String> genericImports)
	{
		return TypeNameSupport.combineImports(imports(composition, genericImports),
				domainModelRelationshipImports(composition.relationships()));
	}

	static Set<String> createImports(final AggregateComposition composition, final Set<String> genericImports)
	{
		return TypeNameSupport.combineImports(imports(composition, genericImports),
				domainCreateRelationshipImports(composition.relationships()));
	}

	static Set<String> updateImports(final AggregateComposition composition, final Set<String> genericImports)
	{
		return TypeNameSupport.combineImports(imports(composition, genericImports),
				domainUpdateRelationshipImports(composition.relationships()));
	}

	static Set<String> responseImports(final AggregateComposition composition, final Set<String> genericImports)
	{
		return TypeNameSupport.combineImports(imports(composition, genericImports),
				domainModelRelationshipImports(composition.relationships()));
	}

	private static Set<String> domainModelRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			imports.add(relationship.domainModelImport());
			imports.add(IDENTIFIED_MODEL_IMPORT);
			imports.addAll(TypeNameSupport.importsForResolvedType(relationship.satelliteDomainIdType()));
		});
		imports.remove("");
		return imports;
	}

	private static Set<String> domainCreateRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			if (relationship.referenced())
			{
				imports.addAll(TypeNameSupport.importsForResolvedType(relationship.satelliteDomainIdType()));
			}
			else
			{
				imports.add(relationship.domainCreateImport(""));
			}
		});
		imports.remove("");
		return imports;
	}

	private static Set<String> domainUpdateRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			imports.addAll(TypeNameSupport.importsForResolvedType(relationship.satelliteDomainIdType()));
			if (relationship.owned())
			{
				imports.add(relationship.domainUpdatePatchImport(""));
			}
		});
		imports.remove("");
		return imports;
	}

	private DomainProjectionSupport()
	{
	}
}