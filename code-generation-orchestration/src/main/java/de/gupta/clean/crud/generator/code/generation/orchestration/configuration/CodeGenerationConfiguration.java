package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import java.util.Map;
import java.util.Set;

public record CodeGenerationConfiguration(
		String domainModelSourceCodeFilePath,
		Map<String, String> domainConcreteTypes,
		Map<String, String> persistenceConcreteTypes,
		Map<String, String> apiConcreteTypes,
		Set<String> templateGroups,
		Boolean generateCommonFiles,
		boolean forceOverwrite,
		boolean historized
)
{
	public static CodeGenerationConfiguration of(
			final String domainModelSourceCodeFilePath,
			final boolean historized)
	{
		var map = defaultConcreteTypes();
		return new CodeGenerationConfiguration(domainModelSourceCodeFilePath, map, map, map,
				Set.of(),
				null,
				false,
				historized);
	}

	public static CodeGenerationConfiguration of(final String domainModelSourceCodeFilePath)
	{
		return of(domainModelSourceCodeFilePath, false);
	}

	public static Map<String, String> defaultConcreteTypes()
	{
		return Map.of("U", "String", "V", "Long");
	}

	public CodeGenerationConfiguration
	{
		domainConcreteTypes = normalizeConcreteTypes(domainConcreteTypes);
		persistenceConcreteTypes = normalizeConcreteTypes(persistenceConcreteTypes);
		apiConcreteTypes = normalizeConcreteTypes(apiConcreteTypes);
		templateGroups = templateGroups == null ? Set.of() : Set.copyOf(templateGroups);
	}

	private static Map<String, String> normalizeConcreteTypes(final Map<String, String> concreteTypes)
	{
		return concreteTypes == null || concreteTypes.isEmpty() ? defaultConcreteTypes() : Map.copyOf(concreteTypes);
	}
}