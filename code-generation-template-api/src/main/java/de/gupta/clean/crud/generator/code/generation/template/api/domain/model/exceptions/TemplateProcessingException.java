package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions;

public final class TemplateProcessingException extends RuntimeException
{
	public static TemplateProcessingException withMessage(final String message)
	{
		final String errorMessage = "Processing template failed. The details are: " + message;
		return new TemplateProcessingException(errorMessage);
	}

	private TemplateProcessingException(final String message)
	{
		super(message);
	}
}