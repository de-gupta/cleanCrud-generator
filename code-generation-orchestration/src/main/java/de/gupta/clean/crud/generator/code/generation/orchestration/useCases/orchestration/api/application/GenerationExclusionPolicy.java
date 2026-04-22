package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.OwnershipResolver;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.TemplateCatalog;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
final class GenerationExclusionPolicy
{
	private static final String RELATIONSHIP_CONFIGURATION_TEMPLATE = "CrudRelationshipConfiguration";

	private final TemplateCatalog templateCatalog;
	private final DefaultOwnershipPolicy defaultOwnershipPolicy;

	Set<String> excludedTemplates(final CodeGenerationConfiguration configuration)
	{
		var templates = new LinkedHashSet<>(configuration.generation().excludeTemplates());
		applyRelationshipGenerationExclusions(templates, configuration);
		applyOwnershipExclusions(templates, configuration);
		return Set.copyOf(templates);
	}

	private void applyRelationshipGenerationExclusions(
			final Set<String> templates,
			final CodeGenerationConfiguration configuration)
	{
		if (configuration.relationships().isEmpty())
		{
			templates.add(RELATIONSHIP_CONFIGURATION_TEMPLATE);
		}
	}

	private void applyOwnershipExclusions(
			final Set<String> templates,
			final CodeGenerationConfiguration configuration)
	{
		var ownershipResolver = OwnershipResolver.with(configuration.ownership());
		templateCatalog.allTemplates().forEach(template ->
		{
			var ownership = ownershipResolver.ownershipOf(template)
			                                 .orElseGet(() -> defaultOwnershipPolicy.ownershipOf(
													 template.templateName(), configuration));
			if (ownership == GeneratedArtifactOwnership.USER)
			{
				templates.add(template.templateName());
			}
		});
	}

	GenerationExclusionPolicy(
			final TemplateCatalog templateCatalog,
			final DefaultOwnershipPolicy defaultOwnershipPolicy)
	{
		this.templateCatalog = templateCatalog;
		this.defaultOwnershipPolicy = defaultOwnershipPolicy;
	}
}