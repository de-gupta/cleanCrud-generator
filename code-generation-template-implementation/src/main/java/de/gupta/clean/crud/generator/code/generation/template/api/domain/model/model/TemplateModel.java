package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.*;

public interface TemplateModel
{
	Map<String, String> RESERVED_SQL_IDENTIFIERS = Map.ofEntries(
			Map.entry("user", "user_id"),
			Map.entry("order", "order_value"),
			Map.entry("group", "group_value"),
			Map.entry("key", "key_value"),
			Map.entry("value", "value_value")
	);
	Map<String, String> SIMPLE_TYPE_IMPORTS = Map.ofEntries(
			Map.entry("UUID", "java.util.UUID"),
			Map.entry("LocalDate", "java.time.LocalDate"),
			Map.entry("LocalDateTime", "java.time.LocalDateTime"),
			Map.entry("LocalTime", "java.time.LocalTime"),
			Map.entry("Instant", "java.time.Instant"),
			Map.entry("OffsetDateTime", "java.time.OffsetDateTime"),
			Map.entry("ZonedDateTime", "java.time.ZonedDateTime"),
			Map.entry("BigDecimal", "java.math.BigDecimal"),
			Map.entry("BigInteger", "java.math.BigInteger")
	);
	Set<String> SIMPLE_PERSISTENCE_TYPES = Set.of(
			"String",
			"Boolean",
			"boolean",
			"Byte",
			"byte",
			"Short",
			"short",
			"Integer",
			"int",
			"Long",
			"long",
			"Float",
			"float",
			"Double",
			"double",
			"Character",
			"char",
			"UUID",
			"LocalDate",
			"LocalDateTime",
			"LocalTime",
			"Instant",
			"OffsetDateTime",
			"ZonedDateTime",
			"BigDecimal",
			"BigInteger"
	);

	String packageName();

	String basePackage();

	String modelName();

	default String modelBaseName()
	{
		return modelName().endsWith("Model") ? modelName().substring(0, modelName().length() - "Model".length()) :
				modelName();
	}

	default String beanNamePrefix()
	{
		return uncapitalize(modelBaseName());
	}

	default String qualifier(final String suffix)
	{
		return beanNamePrefix() + suffix;
	}

	default String sqlIdentifier(final String identifier)
	{
		return RESERVED_SQL_IDENTIFIERS.getOrDefault(identifier.toLowerCase(Locale.ROOT), identifier);
	}

	default String sqlColumnName(final Property property)
	{
		return sqlIdentifier(property.name());
	}

	default String persistenceModelTableName()
	{
		return sqlIdentifier(modelBaseName().toLowerCase(Locale.ROOT) + "_persistence_model");
	}

	default String persistenceJpaConvertersTypeName()
	{
		return modelBaseName() + "PersistenceJpaConverters";
	}

	default String persistenceModelHistoryTableName()
	{
		return sqlIdentifier(modelBaseName().toLowerCase(Locale.ROOT) + "_persistence_model_history");
	}

	default String domainPersistenceAdapterTableName()
	{
		return sqlIdentifier(modelBaseName().toLowerCase(Locale.ROOT) + "_domain_persistence_adapter_model");
	}

	default String domainPersistenceAdapterHistoryTableName()
	{
		return sqlIdentifier(modelBaseName().toLowerCase(Locale.ROOT) + "_domain_persistence_adapter_model_history");
	}

	boolean isGeneric();

	boolean historized();

	Set<String> domainGenericImports();

	java.util.List<GeneratedRelationship> relationships();

	default boolean hasRelationships()
	{
		return !relationships().isEmpty();
	}

	default Set<String> relationshipPropertyNames()
	{
		return relationships().stream()
		                      .map(GeneratedRelationship::propertyName)
		                      .collect(java.util.stream.Collectors.toUnmodifiableSet());
	}

	default SequencedCollection<Property> standaloneProperties()
	{
		return properties().stream()
		                   .filter(property -> !relationshipPropertyNames().contains(property.name()))
		                   .toList();
	}

	default Set<String> propertyImports()
	{
		var imports = new LinkedHashSet<String>();
		properties().forEach(property -> imports.addAll(property.imports()));
		properties().stream()
		            .map(Property::baseType)
		            .map(this::importForType)
		            .filter(importName -> !importName.isBlank())
		            .forEach(imports::add);
		return imports;
	}

	default Set<String> relationshipImports()
	{
		var imports = new LinkedHashSet<String>();
		relationships().forEach(relationship ->
		{
			imports.add(relationship.responseImport());
			imports.add(relationship.createImport());
			imports.add(relationship.updatePatchImport());
		});
		return imports;
	}

	default Set<String> domainModelImports()
	{
		return combineImports(domainGenericImports(), combineImports(propertyImports(), relationshipImports()));
	}

	default Set<String> persistenceGenericImports()
	{
		return combineImports(domainGenericImports(), inferredConcreteTypeImports(persistenceConcreteTypes()));
	}

	default Set<String> persistenceModelImports()
	{
		var imports = new LinkedHashSet<>(combineImports(persistenceGenericImports(), propertyImports()));
		relationships().forEach(relationship ->
		{
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

	default Set<String> apiGenericImports()
	{
		return combineImports(domainGenericImports(), inferredConcreteTypeImports(apiConcreteTypes()));
	}

	default Set<String> apiModelImports()
	{
		return combineImports(apiGenericImports(), combineImports(propertyImports(), relationshipImports()));
	}

	SequencedCollection<String> genericTypeParameters();

	default SequencedCollection<String> genericTypeParams()
	{
		return genericTypeParameters();
	}

	Set<Property> properties();

	default SequencedCollection<Property> requiredProperties()
	{
		return standaloneProperties().stream().filter(property -> !property.optional()).toList();
	}

	default SequencedCollection<Property> jpaConverterProperties()
	{
		return standaloneProperties().stream().filter(this::requiresJpaConverter).toList();
	}

	default String duplicateKeyTypeName()
	{
		return modelBaseName() + "DuplicateKey";
	}

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
		return property.optional() ? "Optional<" + boxedType(domainResolvedType(property.baseType())) + ">" :
				normalizedGeneratedType(domainResolvedType(property.type()));
	}

	default String baseBuilderPropertyType(final Property property)
	{
		return property.paramType();
	}

	default String domainBuilderPropertyType(final Property property)
	{
		return property.optional() ? "Optional<" + boxedType(domainResolvedType(property.baseType())) + ">" :
				normalizedGeneratedType(domainResolvedType(property.type()));
	}

	default String persistencePropertyType(final Property property)
	{
		return property.optional() ? "Optional<" + boxedType(persistenceResolvedType(property.baseType())) + ">" :
				normalizedGeneratedType(persistenceResolvedType(property.type()));
	}

	default String persistenceBuilderPropertyType(final Property property)
	{
		return property.optional() ? "Optional<" + boxedType(persistenceResolvedType(property.baseType())) + ">" :
				normalizedGeneratedType(persistenceResolvedType(property.type()));
	}

	default boolean requiresJpaConverter(final Property property)
	{
		if (property.isEnum())
		{
			return false;
		}
		return !SIMPLE_PERSISTENCE_TYPES.contains(rawTypeName(persistenceResolvedType(property.baseType())));
	}

	default String jpaConverterNestedClassName(final Property property)
	{
		return property.capitalizedName() + "JpaConverter";
	}

	default String apiPropertyType(final Property property)
	{
		return property.optional() ? "Optional<" + boxedType(apiResolvedType(property.baseType())) + ">" :
				normalizedGeneratedType(apiResolvedType(property.type()));
	}

	default String boxedDomainResolvedType(final String declaredType)
	{
		return boxedType(domainResolvedType(declaredType));
	}

	default String boxedPersistenceResolvedType(final String declaredType)
	{
		return boxedType(persistenceResolvedType(declaredType));
	}

	default String boxedApiResolvedType(final String declaredType)
	{
		return boxedType(apiResolvedType(declaredType));
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

	private String normalizedGeneratedType(final String declaredType)
	{
		StringBuilder normalized = new StringBuilder();
		StringBuilder token = new StringBuilder();
		int genericDepth = 0;
		for (int index = 0; index < declaredType.length(); index++)
		{
			char character = declaredType.charAt(index);
			if (Character.isJavaIdentifierPart(character) || character == '.')
			{
				token.append(character);
				continue;
			}
			appendNormalizedToken(normalized, token, genericDepth > 0);
			normalized.append(character);
			if (character == '<')
			{
				genericDepth++;
			}
			else if (character == '>')
			{
				genericDepth--;
			}
		}
		appendNormalizedToken(normalized, token, genericDepth > 0);
		return normalized.toString();
	}

	private void appendNormalizedToken(final StringBuilder normalized, final StringBuilder token,
	                                   final boolean boxPrimitive)
	{
		if (token.isEmpty())
		{
			return;
		}
		String value = token.toString();
		normalized.append(boxPrimitive ? boxedType(value) : value);
		token.setLength(0);
	}

	private String boxedType(final String declaredType)
	{
		return switch (declaredType)
		{
			case "byte" -> "Byte";
			case "short" -> "Short";
			case "int" -> "Integer";
			case "long" -> "Long";
			case "float" -> "Float";
			case "double" -> "Double";
			case "boolean" -> "Boolean";
			case "char" -> "Character";
			default -> declaredType;
		};
	}

	private String concreteTypeAt(final Map<String, String> concreteTypes, final int index)
	{
		return concreteTypes.get(genericTypeParameters().stream().skip(index).findFirst().orElseThrow());
	}

	private String resolveConcreteType(final Map<String, String> concreteTypes, final String declaredType)
	{
		return concreteTypes.getOrDefault(declaredType, declaredType);
	}

	private Set<String> inferredConcreteTypeImports(final Map<String, String> concreteTypes)
	{
		var imports = new LinkedHashSet<String>();
		concreteTypes.values().stream()
		             .map(this::importForType)
		             .filter(importName -> !importName.isBlank())
		             .forEach(imports::add);
		return imports;
	}

	private Set<String> combineImports(final Set<String> first, final Set<String> second)
	{
		var combined = new LinkedHashSet<String>();
		combined.addAll(first);
		combined.addAll(second);
		return combined;
	}

	private String importForType(final String typeName)
	{
		var rawType = stripGenericArguments(typeName);
		if (rawType.contains("."))
		{
			return rawType;
		}
		return SIMPLE_TYPE_IMPORTS.getOrDefault(rawType, "");
	}

	private String stripGenericArguments(final String typeName)
	{
		int genericStart = typeName.indexOf('<');
		return genericStart >= 0 ? typeName.substring(0, genericStart) : typeName;
	}

	private String rawTypeName(final String typeName)
	{
		var rawType = stripGenericArguments(typeName);
		int packageSeparator = rawType.lastIndexOf('.');
		return packageSeparator >= 0 ? rawType.substring(packageSeparator + 1) : rawType;
	}

	private String uncapitalize(final String value)
	{
		return value.isEmpty() ? value : Character.toLowerCase(value.charAt(0)) + value.substring(1);
	}
}