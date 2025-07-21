package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions;

public final class TemplateLoadingException extends RuntimeException
{
	public static TemplateLoadingException withMessage(final String message)
	{
		final String errorMessage = "Loading template failed. The details are: " + message;
		return new TemplateLoadingException(errorMessage);
	}

	private TemplateLoadingException(final String message)
	{
		super(message);
	}
}