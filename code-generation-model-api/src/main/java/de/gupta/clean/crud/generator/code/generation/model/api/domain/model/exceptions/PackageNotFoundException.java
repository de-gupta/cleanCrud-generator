package de.gupta.clean.crud.generator.code.generation.model.api.domain.model.exceptions;

public class PackageNotFoundException extends RuntimeException
{
	public static PackageNotFoundException withMessage(String message)
	{
		return new PackageNotFoundException(message);
	}

	private PackageNotFoundException(String message)
	{
		super(message);
	}
}