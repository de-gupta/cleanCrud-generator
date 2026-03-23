package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import java.util.Map;
import java.util.Set;

public record CodeGenerationConfiguration(
		String domainModelSourceCodeFilePath,
		Map<String, String> domainConcreteTypes,
		Map<String, String> persistenceConcreteTypes,
		Map<String, String> apiConcreteTypes,
		Set<String> templateGroups,
		boolean forceOverwrite,
		boolean historized
)
{
	public static CodeGenerationConfiguration of(final String domainModelSourceCodeFilePath)
	{
		return of(domainModelSourceCodeFilePath, false);
	}

	public static CodeGenerationConfiguration of(
			final String domainModelSourceCodeFilePath,
			final boolean historized)
	{
		var map = Map.of("U", "String", "V", "Long");
		return new CodeGenerationConfiguration(domainModelSourceCodeFilePath, map, map, map,
				Set.of(),
				false,
				historized);
	}
}