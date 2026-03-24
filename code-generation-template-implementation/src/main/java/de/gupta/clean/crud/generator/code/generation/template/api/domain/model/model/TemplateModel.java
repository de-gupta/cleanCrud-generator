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

	default String modelBaseName()
	{
		return modelName().endsWith("Model") ? modelName().substring(0, modelName().length() - "Model".length()) :
				modelName();
	}

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

	default String domainConcreteType(final String genericType)
	{
		return resolveConcreteType(domainConcreteTypes(), genericType);
	}

	default String persistenceConcreteType(final String genericType)
	{
		return resolveConcreteType(persistenceConcreteTypes(), genericType);
	}

	default String apiConcreteType(final String genericType)
	{
		return resolveConcreteType(apiConcreteTypes(), genericType);
	}

	default String domainResolvedType(final String declaredType)
	{
		return domainConcreteType(declaredType);
	}

	default String persistenceResolvedType(final String declaredType)
	{
		return persistenceConcreteType(declaredType);
	}

	default String apiResolvedType(final String declaredType)
	{
		return apiConcreteType(declaredType);
	}

	default String domainPropertyType(final Property property)
	{
		return property.optional() ? "Optional<" + domainResolvedType(property.baseType()) + ">" :
				domainResolvedType(property.type());
	}

	default String persistencePropertyType(final Property property)
	{
		return property.optional() ? "Optional<" + persistenceResolvedType(property.baseType()) + ">" :
				persistenceResolvedType(property.type());
	}

	default String apiPropertyType(final Property property)
	{
		return property.optional() ? "Optional<" + apiResolvedType(property.baseType()) + ">" :
				apiResolvedType(property.type());
	}

	default boolean apiAndDomainTypesDiffer(final String genericType)
	{
		return !apiConcreteType(genericType).equals(domainConcreteType(genericType));
	}

	default boolean persistenceAndDomainTypesDiffer(final String genericType)
	{
		return !persistenceConcreteType(genericType).equals(domainConcreteType(genericType));
	}

	default SequencedCollection<String> apiDomainDifferingGenericTypeParameters()
	{
		return genericTypeParameters().stream().filter(this::apiAndDomainTypesDiffer).toList();
	}

	default SequencedCollection<String> persistenceDomainDifferingGenericTypeParameters()
	{
		return genericTypeParameters().stream().filter(this::persistenceAndDomainTypesDiffer).toList();
	}

	default String domainConcreteTypeAt(final int index)
	{
		return concreteTypeAt(domainConcreteTypes(), index);
	}

	default String persistenceConcreteTypeAt(final int index)
	{
		return concreteTypeAt(persistenceConcreteTypes(), index);
	}

	default String apiConcreteTypeAt(final int index)
	{
		return concreteTypeAt(apiConcreteTypes(), index);
	}

	private String concreteTypeAt(final Map<String, String> concreteTypes, final int index)
	{
		return concreteTypes.get(genericTypeParameters().stream().skip(index).findFirst().orElseThrow());
	}

	private String resolveConcreteType(final Map<String, String> concreteTypes, final String declaredType)
	{
		return concreteTypes.getOrDefault(declaredType, declaredType);
	}
}