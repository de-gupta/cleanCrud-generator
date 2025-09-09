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
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
final class TemplateRepositoryImpl implements TemplateRepository
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
		return Unfolding.beckon(resource)
						.metamorphose(Resource::getFilename)
						.metamorphose(f -> f.substring(0, f.length() - 4))
						.metamorphose(name ->
						{
							var metadataConfig = TemplateMetadataRegistry.getTemplateMetadata(name)
																		 .orElseThrow(
																				 () -> TemplateLoadingException.withMessage(
																						 "Template metadata not found in registry: " + name));

							return new SourceCodeTemplate(
									name,
									metadataConfig.metadata().forceOverwrite(),
									metadataConfig.templateGroup(),
									metadataConfig.metadata()
							);
						})
						.decree(() -> TemplateLoadingException.withMessage("Failed to create template from resource"));
	}


	TemplateRepositoryImpl()
	{
		this.cachedTemplates = loadAllTemplates();
	}
}