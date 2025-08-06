package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.freemarker;

import de.gupta.aletheia.functional.Unfolding;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCode;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions.InvalidTemplateException;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions.TemplateLoadingException;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions.TemplateProcessingException;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModel;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service.TemplateProcessor;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;

@Component
final class TemplateProcessorImpl implements TemplateProcessor
{
	private final Configuration freemarkerConfiguration;

	@Override
	public SourceCodeFile process(final SourceCodeTemplate template, final TemplateModel model)
	{
		return Unfolding.of(template)
						.interlace(SourceCodeTemplate::templateSourceCodeFilename)
						.metamorphose(p -> SourceCodeFile.with(p.second(), templateCode(p.first(), model)))
						.decree(() -> InvalidTemplateException.withMessage("Template cannot be null"));
	}

	private SourceCode templateCode(final SourceCodeTemplate template, final TemplateModel model)
	{
		StringWriter writer = new StringWriter();
		try
		{
			loadTemplate(template.templateFileName()).process(model, writer);
		}
		catch (TemplateException | IOException e)
		{
			throw TemplateProcessingException.withMessage(e.getMessage());
		}
		return SourceCode.with(writer.toString());
	}

	private Template loadTemplate(final String templateName)
	{
		try
		{
			return freemarkerConfiguration.getTemplate(templateName);
		}
		catch (Exception e)
		{
			throw TemplateLoadingException.withMessage(e.getMessage());
		}
	}

	TemplateProcessorImpl(final Configuration freemarkerConfiguration)
	{
		this.freemarkerConfiguration = freemarkerConfiguration;
	}
}