package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.TemplateMetadata;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Static registry that holds template metadata configuration for all available templates.
 * This class provides a centralized place to configure template metadata including
 * force overwrite flags, descriptions, dependencies, and other template-specific information.
 */
public final class TemplateMetadataRegistry
{
	private static final Map<String, TemplateMetadataConfig> TEMPLATE_METADATA_MAP = Map.of(
			"BaseModel", new TemplateMetadataConfig(
					new TemplateMetadata(
							Optional.of("Base model interface template with validation and builder pattern"),
							Set.of("ModelBuilder", "Validatable"),
							Set.of("domain", "model", "base"),
							Optional.of("Clean CRUD Generator"),
							Optional.of("1.0"),
							false
					),
					TemplateGroup.DOMAIN
			),
			"DomainModel", new TemplateMetadataConfig(
					new TemplateMetadata(
							Optional.of("Domain-specific model interface extending base model"),
							Set.of("BaseDomainModel", "BaseModel"),
							Set.of("domain", "model", "specific"),
							Optional.of("Clean CRUD Generator"),
							Optional.of("1.0"),
							false
					),
					TemplateGroup.DOMAIN
			)
	);

	/**
	 * Retrieves template metadata configuration by template name.
	 *
	 * @param templateName the name of the template
	 * @return Optional containing the metadata configuration if found, empty otherwise
	 */
	public static Optional<TemplateMetadataConfig> getTemplateMetadata(String templateName)
	{
		return Optional.ofNullable(TEMPLATE_METADATA_MAP.get(templateName));
	}

	/**
	 * Retrieves all template metadata configurations.
	 *
	 * @return Map of template names to their metadata configurations
	 */
	public static Map<String, TemplateMetadataConfig> getAllTemplateMetadata()
	{
		return Map.copyOf(TEMPLATE_METADATA_MAP);
	}

	/**
	 * Checks if a template with the given name exists in the registry.
	 *
	 * @param templateName the name of the template
	 * @return true if the template exists, false otherwise
	 */
	public static boolean hasTemplate(String templateName)
	{
		return TEMPLATE_METADATA_MAP.containsKey(templateName);
	}

	// Prevent instantiation
	private TemplateMetadataRegistry()
	{
	}

	/**
	 * Record that combines template metadata with its group classification.
	 */
	public record TemplateMetadataConfig(
			TemplateMetadata metadata,
			TemplateGroup templateGroup
	)
	{
	}
}