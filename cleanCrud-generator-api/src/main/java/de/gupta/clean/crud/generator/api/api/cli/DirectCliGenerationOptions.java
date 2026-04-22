package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership;

import java.util.List;
import java.util.Objects;
import java.util.Set;

record DirectCliGenerationOptions(
		String positionalBaseModelPath,
		String baseModelPath,
		String domainModelPath,
		String persistenceModelPath,
		String apiModelPath,
		List<String> domainTypes,
		List<String> persistenceTypes,
		List<String> apiTypes,
		Set<String> includeGroups,
		Set<String> includeTemplates,
		Set<String> includeTags,
		Set<String> excludeGroups,
		Set<String> excludeTemplates,
		Set<String> excludeTags,
		GeneratedArtifactOwnership baseModelOwnership,
		GeneratedArtifactOwnership domainModelOwnership,
		GeneratedArtifactOwnership persistenceModelOwnership,
		GeneratedArtifactOwnership apiModelOwnership,
		List<String> ownershipGroupRules,
		List<String> ownershipTemplateRules,
		List<String> ownershipTagRules,
		Boolean overwriteDefault,
		List<String> overwriteGroupRules,
		List<String> overwriteTemplateRules,
		List<String> overwriteTagRules,
		List<String> overwriteFileRules,
		boolean historized)
{
	boolean hasDirectOptions()
	{
		return firstNonBlank(positionalBaseModelPath, baseModelPath, domainModelPath, persistenceModelPath,
				apiModelPath) != null
				|| historized
				|| !domainTypes.isEmpty()
				|| !persistenceTypes.isEmpty()
				|| !apiTypes.isEmpty()
				|| !includeGroups.isEmpty()
				|| !includeTemplates.isEmpty()
				|| !includeTags.isEmpty()
				|| !excludeGroups.isEmpty()
				|| !excludeTemplates.isEmpty()
				|| !excludeTags.isEmpty()
				|| baseModelOwnership != null
				|| domainModelOwnership != null
				|| persistenceModelOwnership != null
				|| apiModelOwnership != null
				|| !ownershipGroupRules.isEmpty()
				|| !ownershipTemplateRules.isEmpty()
				|| !ownershipTagRules.isEmpty()
				|| overwriteDefault != null
				|| !overwriteGroupRules.isEmpty()
				|| !overwriteTemplateRules.isEmpty()
				|| !overwriteTagRules.isEmpty()
				|| !overwriteFileRules.isEmpty();
	}

	String resolvedBaseModelPath()
	{
		return firstNonBlank(baseModelPath, positionalBaseModelPath);
	}

	private static String firstNonBlank(final String... values)
	{
		return java.util.Arrays.stream(values)
		                       .filter(Objects::nonNull)
		                       .map(String::trim)
		                       .filter(value -> !value.isBlank())
		                       .findFirst()
		                       .orElse(null);
	}
}
