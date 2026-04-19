package de.gupta.clean.crud.generator.code.generation.model.api.domain.model;

import de.gupta.commons.utility.string.StringCaseUtility;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Property(String name, String returnType, String fullyQualifiedTypeName, boolean enumType)
{
	private static final Pattern QUALIFIED_TYPE_PATTERN = Pattern.compile(
			"\\b[a-zA-Z_]\\w*(?:\\.[a-zA-Z_]\\w*)+\\b");
	private static final Set<String> COLLECTION_TYPES = Set.of(
			"Collection",
			"List",
			"Set",
			"java.util.Collection",
			"java.util.List",
			"java.util.Set"
	);
	private static final Set<String> NON_AGGREGATE_PACKAGE_PREFIXES = Set.of(
			"java.",
			"jakarta.",
			"javax."
	);
	private static final Set<String> NON_AGGREGATE_SIMPLE_TYPES = Set.of(
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
			"BigInteger",
			"Object",
			"Void"
	);

	public static Property of(final String name, final String returnType, final String fullyQualifiedTypeName)
	{
		return new Property(name, returnType, fullyQualifiedTypeName, false);
	}

	public static Property of(
			final String name,
			final String returnType,
			final String fullyQualifiedTypeName,
			final boolean enumType)
	{
		return new Property(name, returnType, fullyQualifiedTypeName, enumType);
	}

	public String capitalizedName()
	{
		return StringCaseUtility.capitalizeFirstLetter(name);
	}

	public boolean optional()
	{
		return returnType.startsWith("Optional<") || fullyQualifiedTypeName.startsWith("java.util.Optional<");
	}

	public String getter()
	{
		return name;
	}

	public boolean collectionValued()
	{
		return COLLECTION_TYPES.contains(rawType(baseTypeQualifiedName()));
	}

	public String collectionElementType()
	{
		if (!collectionValued())
		{
			return baseType();
		}
		return stripOuterGeneric(baseType());
	}

	public String collectionElementQualifiedTypeName()
	{
		if (!collectionValued())
		{
			return baseTypeQualifiedName();
		}
		return stripOuterGeneric(baseTypeQualifiedName());
	}

	public String candidateAggregateType()
	{
		String qualifiedCandidate = collectionValued() ? collectionElementQualifiedTypeName() : baseTypeQualifiedName();
		String rawQualifiedCandidate = rawType(qualifiedCandidate);
		String simpleName = rawSimpleTypeName(qualifiedCandidate);
		if (NON_AGGREGATE_PACKAGE_PREFIXES.stream().anyMatch(rawQualifiedCandidate::startsWith))
		{
			return "";
		}
		if (NON_AGGREGATE_SIMPLE_TYPES.contains(simpleName))
		{
			return "";
		}
		if (!simpleName.isEmpty() && simpleName.chars().allMatch(Character::isUpperCase))
		{
			return "";
		}
		String aggregateName = simpleName.endsWith("APIModelResponse")
				? simpleName.substring(0, simpleName.length() - "APIModelResponse".length())
				: simpleName.endsWith("Model") ? simpleName.substring(0, simpleName.length() - "Model".length()) :
				  simpleName;
		return aggregateName;
	}

	public boolean aggregateRelationshipCandidate()
	{
		return !candidateAggregateType().isBlank();
	}

	public String type()
	{
		return baseType();
	}

	public String paramType()
	{
		return returnType;
	}

	public String baseType()
	{
		return optional() ? stripOuterGeneric(returnType) : returnType;
	}

	public String baseTypeImport()
	{
		return extractImport(optional() ? stripOuterGeneric(fullyQualifiedTypeName) : fullyQualifiedTypeName);
	}

	public String baseTypeQualifiedName()
	{
		return optional() ? stripOuterGeneric(fullyQualifiedTypeName) : fullyQualifiedTypeName;
	}

	public Set<String> imports()
	{
		final Set<String> imports = new LinkedHashSet<>();
		final Matcher matcher = QUALIFIED_TYPE_PATTERN.matcher(fullyQualifiedTypeName);
		while (matcher.find())
		{
			final String candidate = matcher.group();
			if (!candidate.startsWith("java.lang."))
			{
				imports.add(candidate);
			}
		}
		return imports;
	}

	public boolean isEnum()
	{
		return enumType;
	}

	private String extractImport(final String typeName)
	{
		String candidate = stripGenericArguments(typeName);
		return candidate.contains(".") && !candidate.startsWith("java.lang.") ? candidate : "";
	}

	private String stripOuterGeneric(final String typeName)
	{
		int start = typeName.indexOf('<');
		int end = typeName.lastIndexOf('>');
		return start >= 0 && end > start ? typeName.substring(start + 1, end) : typeName;
	}

	private String stripGenericArguments(final String typeName)
	{
		int genericStart = typeName.indexOf('<');
		return genericStart >= 0 ? typeName.substring(0, genericStart) : typeName;
	}

	private String rawType(final String typeName)
	{
		return stripGenericArguments(typeName);
	}

	private String rawSimpleTypeName(final String typeName)
	{
		String rawTypeName = rawType(typeName);
		int lastPackageSeparator = rawTypeName.lastIndexOf('.');
		return lastPackageSeparator >= 0 ? rawTypeName.substring(lastPackageSeparator + 1) : rawTypeName;
	}
}