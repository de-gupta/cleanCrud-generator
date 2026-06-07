package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.Set;

public interface ApiProjection
{
	Set<String> genericImports();

	Set<String> imports();

	Set<String> createImports();

	Set<String> updateImports();

	Set<String> responseImports();

	String concreteType(String genericType);

	String resolvedType(String declaredType);

	String boxedResolvedType(String declaredType);

	String propertyType(Property property);
}
