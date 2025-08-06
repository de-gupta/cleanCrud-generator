package de.gupta.clean.crud.generator.code.generation.model.api.domain.model;

import de.gupta.commons.utility.string.StringCaseUtility;

public record Property(String name, String returnType, String fullyQualifiedTypeName)
{
	public static Property of(final String name, final String returnType, final String fullyQualifiedTypeName)
	{
		return new Property(name, returnType, fullyQualifiedTypeName);
	}

	public String capitalizedName()
	{
		return StringCaseUtility.capitalizeFirstLetter(name);
	}
}