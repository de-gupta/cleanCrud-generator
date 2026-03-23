package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.Map;
import java.util.SequencedCollection;
import java.util.Set;

public interface TemplateModel
{
	String packageName();

	String basePackage();

	String modelName();

	boolean isGeneric();

	boolean historized();

	Set<String> domainGenericImports();

	default Set<String> persistenceGenericImports()
	{
		return domainGenericImports();
	}

	default Set<String> apiGenericImports()
	{
		return domainGenericImports();
	}

	SequencedCollection<String> genericTypeParameters();

	default SequencedCollection<String> genericTypeParams()
	{
		return genericTypeParameters();
	}

	Set<Property> properties();

	Map<String, String> domainConcreteTypes();

	Map<String, String> persistenceConcreteTypes();

	Map<String, String> apiConcreteTypes();
}