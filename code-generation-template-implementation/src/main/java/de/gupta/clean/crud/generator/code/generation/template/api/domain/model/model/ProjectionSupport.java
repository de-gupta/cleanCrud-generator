package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class ProjectionSupport
{
	private static final String IDENTIFIED_MODEL_IMPORT =
			"de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel";

	private static final Map<String, String> SIMPLE_TYPE_IMPORTS = Map.ofEntries(
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

	static String concreteType(final Map<String, String> concreteTypes, final String genericType)
	{
		return normalizeGeneratedType(concreteTypes.getOrDefault(genericType, genericType));
	}

	static String resolvedType(final Map<String, String> concreteTypes, final String declaredType)
	{
		StringBuilder resolved = new StringBuilder();
		StringBuilder token = new StringBuilder();
		for (int index = 0; index < declaredType.length(); index++)
		{
			char character = declaredType.charAt(index);
			if (Character.isJavaIdentifierPart(character) || character == '.')
			{
				token.append(character);
				continue;
			}
			appendResolvedToken(resolved, token, concreteTypes);
			resolved.append(character);
		}
		appendResolvedToken(resolved, token, concreteTypes);
		return normalizeGeneratedType(resolved.toString());
	}

	static String boxedResolvedType(final Map<String, String> concreteTypes, final String declaredType)
	{
		return boxedType(resolvedType(concreteTypes, declaredType));
	}

	static String valueType(final Map<String, String> concreteTypes, final Property property)
	{
		return property.optional()
				? "Optional<" + boxedType(resolvedType(concreteTypes, property.baseType())) + ">"
				: normalizeGeneratedType(resolvedType(concreteTypes, property.type()));
	}

	static Set<String> inferredConcreteTypeImports(final Map<String, String> concreteTypes)
	{
		var imports = new LinkedHashSet<String>();
		concreteTypes.values().stream()
		             .flatMap(type -> importsForResolvedType(type).stream())
		             .forEach(imports::add);
		return imports;
	}

	static Set<String> propertyImports(final Set<Property> properties)
	{
		var imports = new LinkedHashSet<String>();
		properties.forEach(property -> imports.addAll(property.imports()));
		properties.stream()
		          .map(Property::baseType)
		          .flatMap(type -> importsForResolvedType(type).stream())
		          .forEach(imports::add);
		return imports;
	}

	static Set<String> domainModelRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			imports.add(relationship.domainModelImport());
			imports.add(IDENTIFIED_MODEL_IMPORT);
			imports.addAll(importsForResolvedType(relationship.satelliteDomainIdType()));
		});
		imports.remove("");
		return imports;
	}

	static Set<String> domainCreateRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			if (relationship.referenced())
			{
				imports.addAll(importsForResolvedType(relationship.satelliteDomainIdType()));
			}
			else
			{
				imports.add(relationship.domainCreateImport(""));
			}
		});
		imports.remove("");
		return imports;
	}

	static Set<String> domainUpdateRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			imports.addAll(importsForResolvedType(relationship.satelliteDomainIdType()));
			if (relationship.owned())
			{
				imports.add(relationship.domainUpdatePatchImport(""));
			}
		});
		imports.remove("");
		return imports;
	}

	static Set<String> apiCreateRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			if (relationship.referenced())
			{
				imports.addAll(importsForResolvedType(relationship.satelliteApiIdType()));
			}
			else
			{
				imports.add(relationship.createImport());
			}
		});
		imports.remove("");
		return imports;
	}

	static Set<String> apiUpdateRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship ->
		{
			imports.addAll(importsForResolvedType(relationship.satelliteApiIdType()));
			if (relationship.owned())
			{
				imports.add(relationship.updatePatchImport());
			}
		});
		imports.remove("");
		return imports;
	}

	static Set<String> apiResponseRelationshipImports(final List<GeneratedRelationship> relationships)
	{
		var imports = new LinkedHashSet<String>();
		relationships.forEach(relationship -> imports.add(relationship.responseImport()));
		imports.remove("");
		return imports;
	}

	static Set<String> combineImports(final Set<String> first, final Set<String> second)
	{
		var combined = new LinkedHashSet<String>();
		combined.addAll(first);
		combined.addAll(second);
		return combined;
	}

	static String boxedType(final String declaredType)
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

	static String normalizeGeneratedType(final String declaredType)
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

	static String rawTypeName(final String typeName)
	{
		var rawType = stripGenericArguments(typeName);
		int packageSeparator = rawType.lastIndexOf('.');
		return packageSeparator >= 0 ? rawType.substring(packageSeparator + 1) : rawType;
	}

	static Set<String> importsForResolvedType(final String typeName)
	{
		var imports = new LinkedHashSet<String>();
		StringBuilder token = new StringBuilder();
		for (int index = 0; index < typeName.length(); index++)
		{
			char character = typeName.charAt(index);
			if (Character.isJavaIdentifierPart(character) || character == '.')
			{
				token.append(character);
				continue;
			}
			appendImportToken(imports, token);
		}
		appendImportToken(imports, token);
		imports.remove("");
		return imports;
	}

	private static void appendNormalizedToken(
			final StringBuilder normalized,
			final StringBuilder token,
			final boolean boxPrimitive)
	{
		if (token.isEmpty())
		{
			return;
		}
		String value = token.toString();
		String boxed = boxPrimitive ? boxedType(value) : value;
		normalized.append(rawTypeName(boxed));
		token.setLength(0);
	}

	private static void appendResolvedToken(
			final StringBuilder resolved,
			final StringBuilder token,
			final Map<String, String> concreteTypes)
	{
		if (token.isEmpty())
		{
			return;
		}
		String value = token.toString();
		resolved.append(concreteTypes.getOrDefault(value, value));
		token.setLength(0);
	}

	private static void appendImportToken(final Set<String> imports, final StringBuilder token)
	{
		if (token.isEmpty())
		{
			return;
		}
		String importName = importForType(token.toString());
		if (!importName.isBlank())
		{
			imports.add(importName);
		}
		token.setLength(0);
	}

	private static String importForType(final String typeName)
	{
		var rawType = stripGenericArguments(typeName);
		if (rawType.contains("."))
		{
			return rawType.startsWith("java.lang.") ? "" : rawType;
		}
		return SIMPLE_TYPE_IMPORTS.getOrDefault(rawType, "");
	}

	private static String stripGenericArguments(final String typeName)
	{
		int genericStart = typeName.indexOf('<');
		return genericStart >= 0 ? typeName.substring(0, genericStart) : typeName;
	}

	private ProjectionSupport()
	{
	}
}