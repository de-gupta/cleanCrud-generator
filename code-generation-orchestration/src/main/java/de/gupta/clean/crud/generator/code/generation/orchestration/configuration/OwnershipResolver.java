package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class OwnershipResolver
{
	private final OwnershipConfiguration configuration;

	public static OwnershipResolver with(final OwnershipConfiguration configuration)
	{
		return new OwnershipResolver(
				configuration == null ? OwnershipConfiguration.defaults() : configuration.normalized());
	}

	public Optional<GeneratedArtifactOwnership> ownershipOf(final SourceCodeTemplate template)
	{
		return resolveTemplate(template)
				.or(() -> resolveTags(template))
				.or(() -> resolveGroup(template));
	}

	private Optional<GeneratedArtifactOwnership> resolveGroup(final SourceCodeTemplate template)
	{
		return Optional.ofNullable(configuration.groups().get(template.templateGroup().name()));
	}

	private Optional<GeneratedArtifactOwnership> resolveTags(final SourceCodeTemplate template)
	{
		Set<GeneratedArtifactOwnership> matches = new LinkedHashSet<>();
		template.metadata().tags().stream()
		        .map(configuration.tags()::get)
		        .filter(Objects::nonNull)
		        .forEach(matches::add);
		if (matches.isEmpty())
		{
			return Optional.empty();
		}
		if (matches.size() > 1)
		{
			throw new IllegalStateException(
					"Conflicting ownership rules for template tags: " + template.templateName());
		}
		return Optional.of(matches.iterator().next());
	}

	private Optional<GeneratedArtifactOwnership> resolveTemplate(final SourceCodeTemplate template)
	{
		return Optional.ofNullable(configuration.templates().get(template.templateName()));
	}

	private OwnershipResolver(final OwnershipConfiguration configuration)
	{
		this.configuration = configuration;
	}
}