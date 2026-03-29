package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import java.util.Locale;
import java.util.Map;

public record OverwriteConfiguration(
		boolean defaultOverwrite,
		Map<String, Boolean> groups,
		Map<String, Boolean> templates,
		Map<String, Boolean> tags,
		Map<String, Boolean> files
)
{
	public static OverwriteConfiguration defaults()
	{
		return new OverwriteConfiguration(false, Map.of(), Map.of(), Map.of(), Map.of());
	}

	public OverwriteConfiguration normalized()
	{
		return new OverwriteConfiguration(
				defaultOverwrite,
				normalize(groups, true),
				normalize(templates, false),
				normalize(tags, false),
				normalize(files, false)
		);
	}

	private static Map<String, Boolean> normalize(final Map<String, Boolean> values, final boolean uppercase)
	{
		if (values == null || values.isEmpty())
		{
			return Map.of();
		}
		return values.entrySet()
		             .stream()
		             .filter(entry -> entry.getKey() != null && !entry.getKey().isBlank() && entry.getValue() != null)
		             .collect(java.util.stream.Collectors.toUnmodifiableMap(
							 entry ->
							 {
								 var key = entry.getKey().trim();
								 return uppercase ? key.toUpperCase(Locale.ROOT) : key;
							 },
							 Map.Entry::getValue,
							 (left, right) -> right
					 ));
	}
}