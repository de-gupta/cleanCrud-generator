package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public record TemplateSelector(
		Set<TemplateGroup> groups,
		Set<String> templates,
		Set<String> tags,
		Set<TemplateGroup> excludeGroups,
		Set<String> excludeTemplates,
		Set<String> excludeTags
)
{
	public static TemplateSelector with(
			final Set<String> groups,
			final Set<String> templates,
			final Set<String> tags,
			final Set<String> excludeGroups,
			final Set<String> excludeTemplates,
			final Set<String> excludeTags)
	{
		return new TemplateSelector(
				toGroups(groups),
				normalizeTrimmed(templates),
				normalizeLowercase(tags),
				toGroups(excludeGroups),
				normalizeTrimmed(excludeTemplates),
				normalizeLowercase(excludeTags)
		);
	}

	private static Set<TemplateGroup> toGroups(final Set<String> groups)
	{
		return groups == null
				? Set.of()
				: groups.stream()
				        .filter(group -> group != null && !group.isBlank())
				        .map(group -> group.trim().toUpperCase(Locale.ROOT))
				        .map(TemplateGroup::valueOf)
				        .collect(Collectors.toUnmodifiableSet());
	}

	private static Set<String> normalizeTrimmed(final Set<String> values)
	{
		return values == null
				? Set.of()
				: values.stream()
				        .filter(value -> value != null && !value.isBlank())
				        .map(String::trim)
				        .collect(Collectors.toUnmodifiableSet());
	}

	private static Set<String> normalizeLowercase(final Set<String> values)
	{
		return values == null
				? Set.of()
				: values.stream()
				        .filter(value -> value != null && !value.isBlank())
				        .map(String::trim)
				        .map(value -> value.toLowerCase(Locale.ROOT))
				        .collect(Collectors.toUnmodifiableSet());
	}
}