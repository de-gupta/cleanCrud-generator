package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions;

public final class InvalidTemplateException extends RuntimeException
{
	public static InvalidTemplateException withMessage(final String message)
	{
		return new InvalidTemplateException(message);
	}

	private InvalidTemplateException(final String message)
	{
		super(message);
	}
}