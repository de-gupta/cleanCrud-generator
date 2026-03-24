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
import java.util.Optional;
import java.util.regex.Pattern;

@Component
final class TemplateProcessorImpl implements TemplateProcessor
{
	private static final Pattern TOP_LEVEL_TYPE_PATTERN = Pattern.compile(
			"public\\s+(?:(?:final|abstract|sealed|non-sealed|static)\\s+)*(?:class|interface|record|enum)\\s+([A-Za-z_][A-Za-z0-9_]*)");
	private final Configuration freemarkerConfiguration;

	@Override
	public SourceCodeFile process(final SourceCodeTemplate template, final TemplateModel model)
	{
		SourceCodeTemplate validTemplate = Unfolding.of(template)
													.decree(() -> InvalidTemplateException.withMessage(
															"Template cannot be null"));
		SourceCode sourceCode = templateCode(validTemplate, model);
		return SourceCodeFile.with(
				detectFileName(sourceCode).orElse(validTemplate.templateSourceCodeFilename()),
				sourceCode);
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

	private Optional<String> detectFileName(final SourceCode sourceCode)
	{
		var matcher = TOP_LEVEL_TYPE_PATTERN.matcher(sourceCode.sourceCode());
		return matcher.find() ? Optional.of(matcher.group(1) + ".java") : Optional.empty();
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