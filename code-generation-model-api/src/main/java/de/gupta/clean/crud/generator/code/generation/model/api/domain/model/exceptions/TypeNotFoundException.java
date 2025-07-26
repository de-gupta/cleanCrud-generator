package de.gupta.clean.crud.generator.code.generation.model.api.domain.model.exceptions;

public class TypeNotFoundException extends RuntimeException
{
	public static TypeNotFoundException withMessage(String message)
	{
		return new TypeNotFoundException(message);
	}

	private TypeNotFoundException(String message)
	{
		super(message);
	}
}