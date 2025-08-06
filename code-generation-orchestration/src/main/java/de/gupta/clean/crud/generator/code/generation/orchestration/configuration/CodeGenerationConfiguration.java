package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import java.util.Map;
import java.util.Set;

public record CodeGenerationConfiguration(
		String domainModelSourceCodeFilePath,
		Map<String, String> domainConcreteTypes,
		Map<String, String> persistenceConcreteTypes,
		Map<String, String> apiConcreteTypes,
		Set<String> templateGroups,
		boolean forceOverwrite
)
{
	public static CodeGenerationConfiguration of(final String domainModelSourceCodeFilePath)
	{
		var map = Map.of("U", "String", "V", "Long");
		return new CodeGenerationConfiguration(domainModelSourceCodeFilePath, map, map, map,
				Set.of(),
				false);
	}
}