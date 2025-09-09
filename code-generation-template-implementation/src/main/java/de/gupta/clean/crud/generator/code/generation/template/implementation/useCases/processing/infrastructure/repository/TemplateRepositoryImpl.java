package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.repository;

import de.gupta.aletheia.functional.Unfolding;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions.TemplateLoadingException;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.TemplateMetadata;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
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
							TemplateGroup group = extractTemplateGroupFromResource(resource);
							TemplateMetadata metadata = extractTemplateMetadata(resource, name);
							boolean forceOverwrite = extractForceOverwriteFlag(resource);
							return new SourceCodeTemplate(name, forceOverwrite, group, metadata);
						})
						.decree(() -> TemplateLoadingException.withMessage("Failed to create template from resource"));
	}

	private static TemplateGroup extractTemplateGroupFromResource(Resource resource)
	{
		try
		{
			String path = resource.getURI().toString();
			return Unfolding.beckon(path)
							.cleave(extractTemplateGroupMap(), TemplateGroup.DOMAIN);
		}
		catch (Exception e)
		{
			throw TemplateLoadingException.withMessage(
					"Failed to extract template group from resource: " + e.getMessage());
		}
	}

	private static TemplateMetadata extractTemplateMetadata(Resource resource, String templateName)
	{
		// TODO: Parse template file content for metadata comments
		// For now, return metadata with template name as description
		return TemplateMetadata.with("Template: " + templateName);
	}

	private static boolean extractForceOverwriteFlag(Resource resource)
	{
		// TODO: Parse template file content for forceOverwrite flag
		// For now, return false as default
		return false;
	}

	private static Map<Predicate<? super String>, Function<? super String, TemplateGroup>> extractTemplateGroupMap()
	{
		return Map.of(
				path -> path.contains("/templates/domain/"), _ -> TemplateGroup.DOMAIN,
				path -> path.contains("/templates/api/"), _ -> TemplateGroup.API,
				path -> path.contains("/templates/infrastructure/"), _ -> TemplateGroup.INFRASTRUCTURE,
				path -> path.contains("/templates/use_cases/"), _ -> TemplateGroup.USE_CASES
		);
	}

	TemplateRepositoryImpl()
	{
		this.cachedTemplates = loadAllTemplates();
	}
}