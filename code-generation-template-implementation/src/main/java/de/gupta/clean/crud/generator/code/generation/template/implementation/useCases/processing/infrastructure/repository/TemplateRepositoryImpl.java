package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.repository;

import de.gupta.aletheia.functional.Unfolding;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions.TemplateLoadingException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TemplateRepositoryImpl implements TemplateRepository
{
	private final Set<SourceCodeTemplate> cachedTemplates;

	@Override
	public Set<SourceCodeTemplate> allTemplates()
	{
		return Set.copyOf(cachedTemplates);
	}

	@Override
	public SourceCodeTemplate findTemplateByName(String templateName)
	{
		return cachedTemplates.stream()
							  .filter(template -> template.templateName().equals(templateName))
							  .findFirst()
							  .orElseThrow(() -> TemplateLoadingException.withMessage(
									  "Template not found: " + templateName));
	}

	TemplateRepositoryImpl()
	{
		this.cachedTemplates = loadAllTemplates();
	}

	private static Set<SourceCodeTemplate> loadAllTemplates()
	{
		try
		{
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath:templates/**/*.ftl");

			return Arrays.stream(resources)
						 .map(TemplateRepositoryImpl::createTemplateFromResource)
						 .collect(Collectors.toSet());
		}
		catch (IOException e)
		{
			throw TemplateLoadingException.withMessage("Failed to load templates: " + e.getMessage());
		}
	}

	private static SourceCodeTemplate createTemplateFromResource(Resource resource)
	{
		return Unfolding.of(resource)
						.metamorphose(Resource::getFilename)
						.metamorphose(f -> f.substring(0, f.length() - 4))
						.metamorphose(name -> new SourceCodeTemplate(name, false))
						.decree(() -> TemplateLoadingException.withMessage("Failed to create template from resource"));
	}
}