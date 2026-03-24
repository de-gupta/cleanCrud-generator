package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public record TemplateSelector(Set<TemplateGroup> templateGroups)
{
	public static TemplateSelector with(final Set<String> templateGroups)
	{
		return new TemplateSelector(templateGroups.stream()
												  .filter(group -> group != null && !group.isBlank())
												  .map(group -> group.trim().toUpperCase(Locale.ROOT))
												  .map(TemplateGroup::valueOf)
												  .collect(Collectors.toSet())
		);
	}
}