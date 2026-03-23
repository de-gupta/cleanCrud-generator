package de.gupta.clean.crud.generator.code.generation.model.api.domain.model.exceptions;

public class ParsingFailedException extends RuntimeException
{
	public static ParsingFailedException withMessage(String message)
	{
		return new ParsingFailedException(message);
	}

	private ParsingFailedException(String message)
	{
		super(message);
	}
}