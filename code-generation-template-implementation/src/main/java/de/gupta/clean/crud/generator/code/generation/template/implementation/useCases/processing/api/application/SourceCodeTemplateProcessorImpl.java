package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModel;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateSelector;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.SourceCodeTemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.application.service.TemplateProcessor;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.repository.TemplateRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
final class SourceCodeTemplateProcessorImpl implements SourceCodeTemplateProcessor
{
	private final TemplateProcessor templateProcessor;
	private final TemplateRepository templateRepository;

	@Override
	public Map<SourceCodeTemplate, SourceCodeFile> generateSourceCode(
			final TemplateModel model,
			final TemplateSelector templateSelector)
	{
		return resolveTemplates(templateSelector)
				.stream()
				.collect(Collectors.toMap(
						Function.identity(),
						template -> templateProcessor.process(template, model)
				));
	}

	private Set<SourceCodeTemplate> resolveTemplates(final TemplateSelector selector)
	{
		if (selector == null)
		{
			return templateRepository.allTemplates();
		}
		return templateRepository.allTemplates().stream()
		                         .filter(template -> selector.groups().isEmpty() || selector.groups().contains(
										 template.templateGroup()))
		                         .filter(template -> selector.templates().isEmpty() || selector.templates().contains(
										 template.templateName()))
		                         .filter(template -> selector.tags().isEmpty()
										 || template.metadata().tags().stream().anyMatch(selector.tags()::contains))
		                         .filter(template -> !selector.excludeGroups().contains(template.templateGroup()))
		                         .filter(template -> !selector.excludeTemplates().contains(template.templateName()))
		                         .filter(template -> template.metadata().tags().stream()
		                                                     .noneMatch(selector.excludeTags()::contains))
		                         .collect(Collectors.toUnmodifiableSet());
	}

	SourceCodeTemplateProcessorImpl(
			final TemplateProcessor templateProcessor,
			final TemplateRepository templateRepository)
	{
		this.templateProcessor = templateProcessor;
		this.templateRepository = templateRepository;
	}
}