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
}
