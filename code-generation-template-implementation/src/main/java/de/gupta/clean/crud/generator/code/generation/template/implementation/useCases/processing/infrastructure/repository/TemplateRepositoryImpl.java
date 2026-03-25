package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.repository;

import de.gupta.aletheia.functional.Unfolding;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions.TemplateLoadingException;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration.TemplateMetadataRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
final class TemplateRepositoryImpl implements TemplateRepository
{
	private static final String TEMPLATE_ROOT = "templates/";
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

	@Override
	public Set<SourceCodeTemplate> findTemplatesByGroup(TemplateGroup group)
	{
		return cachedTemplates.stream()
							  .filter(template -> template.templateGroup().equals(group))
							  .collect(Collectors.toSet());
	}

	@Override
	public Set<SourceCodeTemplate> findTemplatesByGroups(Set<TemplateGroup> groups)
	{
		return cachedTemplates.stream()
							  .filter(template -> groups.contains(template.templateGroup()))
							  .collect(Collectors.toSet());
	}

	@Override
	public Map<TemplateGroup, Set<SourceCodeTemplate>> templateGroups()
	{
		return cachedTemplates.stream()
							  .collect(Collectors.groupingBy(
									  SourceCodeTemplate::templateGroup,
									  Collectors.toSet()));
	}

	private static Set<SourceCodeTemplate> loadAllTemplates()
	{
		try
		{
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath*:templates/**/*.ftl");

			return Arrays.stream(resources)
						 .sorted(Comparator.comparingInt(TemplateRepositoryImpl::resourcePriority))
						 .map(TemplateRepositoryImpl::createTemplateFromResource)
						 .collect(Collectors.collectingAndThen(
								 Collectors.toMap(
										 SourceCodeTemplate::templateName,
										 template -> template,
										 (preferred, ignored) -> preferred,
										 LinkedHashMap::new),
								 templates -> Set.copyOf(templates.values())));
		}
		catch (IOException e)
		{
			throw TemplateLoadingException.withMessage("Failed to load templates: " + e.getMessage());
		}
	}

	private static int resourcePriority(final Resource resource)
	{
		try
		{
			var normalized = resource.getURL().toString().replace('\\', '/');
			if (normalized.contains("/target/classes/templates/"))
			{
				return 0;
			}
			if (normalized.startsWith("file:"))
			{
				return 1;
			}
			if (normalized.startsWith("jar:file:"))
			{
				return 2;
			}
			return 3;
		}
		catch (IOException e)
		{
			return Integer.MAX_VALUE;
		}
	}

	private static SourceCodeTemplate createTemplateFromResource(Resource resource)
	{
		return Unfolding.beckon(resource)
						.metamorphose(TemplateRepositoryImpl::relativeTemplatePath)
						.metamorphose(path -> path.substring(path.lastIndexOf('/') + 1, path.length() - 4))
						.interlace(ignored -> relativeTemplatePath(resource))
						.metamorphose(pair ->
						{
							var metadataConfig = TemplateMetadataRegistry.getTemplateMetadata(pair.first())
																		 .orElseThrow(
																				 () -> TemplateLoadingException.withMessage(
																						 "Template metadata not found in registry: " + pair.first()));

							return new SourceCodeTemplate(
									pair.first(),
									pair.second(),
									metadataConfig.metadata().forceOverwrite(),
									metadataConfig.templateGroup(),
									metadataConfig.metadata()
							);
						})
						.decree(() -> TemplateLoadingException.withMessage("Failed to create template from resource"));
	}

	private static String relativeTemplatePath(final Resource resource)
	{
		try
		{
			String normalized = resource.getURL().toString().replace('\\', '/');
			int rootIndex = normalized.indexOf(TEMPLATE_ROOT);
			if (rootIndex < 0)
			{
				throw TemplateLoadingException.withMessage(
						"Could not determine template path for resource: " + resource);
			}
			return normalized.substring(rootIndex + TEMPLATE_ROOT.length());
		}
		catch (IOException e)
		{
			throw TemplateLoadingException.withMessage("Failed to inspect template resource: " + e.getMessage());
		}
	}

	TemplateRepositoryImpl()
	{
		this.cachedTemplates = loadAllTemplates();
	}
}