package de.gupta.clean.crud.generator.code.generation.model.api.domain.model;

public record Property(String name, String returnType, String fullyQualifiedTypeName)
{
	public static Property of(final String name, final String returnType, final String fullyQualifiedTypeName)
	{
		return new Property(name, returnType, fullyQualifiedTypeName);
	}

	public String baseType()
	{
		return isOptional() ? baseTypeWithOptional(fullyQualifiedTypeName) :
				baseTypeWithoutOptional(fullyQualifiedTypeName);
	}

	public boolean isOptional()
	{
		return fullyQualifiedTypeName.startsWith("java.util.Optional<") && fullyQualifiedTypeName.endsWith(">");
	}

	private String baseTypeWithOptional(final String fullyQualifiedTypeName)
	{
		return fullyQualifiedTypeName.substring(fullyQualifiedTypeName.indexOf(".") + 1,
				fullyQualifiedTypeName.lastIndexOf(">"));
	}

	private String baseTypeWithoutOptional(final String fullyQualifiedTypeName)
	{
		return fullyQualifiedTypeName.substring(fullyQualifiedTypeName.lastIndexOf(".") + 1);
	}
}