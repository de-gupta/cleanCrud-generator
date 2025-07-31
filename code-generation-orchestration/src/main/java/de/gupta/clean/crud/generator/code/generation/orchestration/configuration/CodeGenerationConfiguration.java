package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import java.util.Map;
import java.util.Set;

public record CodeGenerationConfiguration(
		String domainModelSourceCodeFilePath,
		Map<String, String> domainGenericTypes,
		Map<String, String> persistenceGenericTypes,
		Map<String, String> apiGenericTypes,
		Set<String> templateGroups,
		boolean forceOverwrite
)
{
}