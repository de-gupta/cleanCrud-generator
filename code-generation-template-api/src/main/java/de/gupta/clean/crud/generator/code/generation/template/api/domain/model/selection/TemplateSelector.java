package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection;

import java.util.Set;
import java.util.stream.Collectors;

public record TemplateSelector(Set<TemplateGroup> templateGroups)
{
	public static TemplateSelector with(final Set<String> templateGroups)
	{
		return new TemplateSelector(
				templateGroups
						.stream()
						.map(TemplateGroup::valueOf)
						.collect(Collectors.toSet())
		);
	}
}