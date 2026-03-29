package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class OverwriteResolver
{
	private final OverwriteConfiguration configuration;

	public static OverwriteResolver with(final OverwriteConfiguration configuration)
	{
		return new OverwriteResolver(
				configuration == null ? OverwriteConfiguration.defaults() : configuration.normalized());
	}

	public boolean shouldOverwrite(final SourceCodeTemplate template, final Path targetFile)
	{
		return resolveFile(targetFile)
				.or(() -> resolveTemplate(template))
				.or(() -> resolveTags(template))
				.or(() -> resolveGroup(template))
				.orElse(configuration.defaultOverwrite() || template.forceOverwrite());
	}

	private Optional<Boolean> resolveGroup(final SourceCodeTemplate template)
	{
		return Optional.ofNullable(configuration.groups().get(template.templateGroup().name()));
	}

	private Optional<Boolean> resolveTags(final SourceCodeTemplate template)
	{
		Set<Boolean> matches = new LinkedHashSet<>();
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
					"Conflicting overwrite rules for template tags: " + template.templateName());
		}
		return Optional.of(matches.iterator().next());
	}

	private Optional<Boolean> resolveTemplate(final SourceCodeTemplate template)
	{
		return Optional.ofNullable(configuration.templates().get(template.templateName()));
	}

	private Optional<Boolean> resolveFile(final Path targetFile)
	{
		if (targetFile == null)
		{
			return Optional.empty();
		}
		var normalized = targetFile.toAbsolutePath().normalize().toString();
		return Optional.ofNullable(configuration.files().get(normalized));
	}

	private OverwriteResolver(final OverwriteConfiguration configuration)
	{
		this.configuration = configuration;
	}
}