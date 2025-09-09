package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template;

import java.util.Optional;
import java.util.Set;

/**
 * Value class containing metadata information extracted from template files.
 * This includes description, dependencies, validation rules, and other template-specific information.
 */
public record TemplateMetadata(
		Optional<String> description,
		Set<String> dependencies,
		Set<String> tags,
		Optional<String> author,
		Optional<String> version,
		boolean forceOverwrite
)
{
	public static TemplateMetadata empty()
	{
		return new TemplateMetadata(
				Optional.empty(),
				Set.of(),
				Set.of(),
				Optional.empty(),
				Optional.empty(),
				false
		);
	}

	public static TemplateMetadata with(String description)
	{
		return new TemplateMetadata(
				Optional.ofNullable(description),
				Set.of(),
				Set.of(),
				Optional.empty(),
				Optional.empty(),
				false
		);
	}

	public static TemplateMetadata with(String description, boolean forceOverwrite)
	{
		return new TemplateMetadata(
				Optional.ofNullable(description),
				Set.of(),
				Set.of(),
				Optional.empty(),
				Optional.empty(),
				forceOverwrite
		);
	}
}