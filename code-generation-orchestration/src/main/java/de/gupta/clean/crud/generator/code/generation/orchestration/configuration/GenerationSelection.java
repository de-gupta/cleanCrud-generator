package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public record GenerationSelection(
		Set<String> groups,
		Set<String> templates,
		Set<String> tags,
		Set<String> excludeGroups,
		Set<String> excludeTemplates,
		Set<String> excludeTags
)
{
	public static GenerationSelection defaults()
	{
		return new GenerationSelection(Set.of(), Set.of(), Set.of(), Set.of(), Set.of(), Set.of());
	}

	public GenerationSelection normalized()
	{
		return new GenerationSelection(
				normalizeUppercase(groups),
				normalizeTrimmed(templates),
				normalizeLowercase(tags),
				normalizeUppercase(excludeGroups),
				normalizeTrimmed(excludeTemplates),
				normalizeLowercase(excludeTags)
		);
	}

	private static Set<String> normalizeUppercase(final Set<String> values)
	{
		return normalize(values, value -> value.toUpperCase(Locale.ROOT));
	}

	private static Set<String> normalizeLowercase(final Set<String> values)
	{
		return normalize(values, value -> value.toLowerCase(Locale.ROOT));
	}

	private static Set<String> normalizeTrimmed(final Set<String> values)
	{
		return normalize(values, value -> value);
	}

	private static Set<String> normalize(final Set<String> values,
	                                     final java.util.function.Function<String, String> mapper)
	{
		return values == null
				? Set.of()
				: values.stream()
				        .filter(value -> value != null && !value.isBlank())
				        .map(String::trim)
				        .map(mapper)
				        .collect(Collectors.toUnmodifiableSet());
	}
}