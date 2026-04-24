package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.SequencedCollection;
import java.util.Set;

public interface PersistenceProjection
{
	Set<String> genericImports();

	Set<String> imports();

	Set<String> interfaceImports();

	String concreteType(String genericType);

	String resolvedType(String declaredType);

	String boxedResolvedType(String declaredType);

	String propertyType(Property property);

	String builderPropertyType(Property property);

	String sqlIdentifier(String identifier);

	String sqlColumnName(Property property);

	String modelTableName();

	String historyTableName();

	String adapterTableName();

	String adapterHistoryTableName();

	String jpaConvertersTypeName();

	boolean requiresJpaConverter(Property property);

	SequencedCollection<Property> converterProperties();

	String converterNestedClassName(Property property);
}
