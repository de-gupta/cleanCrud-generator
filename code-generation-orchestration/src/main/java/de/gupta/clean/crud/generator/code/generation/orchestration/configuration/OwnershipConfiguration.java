package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

public record OwnershipConfiguration(
		GeneratedArtifactOwnership baseModel,
		GeneratedArtifactOwnership domainModel,
		GeneratedArtifactOwnership persistenceModel,
		GeneratedArtifactOwnership apiModel,
		Map<String, GeneratedArtifactOwnership> groups,
		Map<String, GeneratedArtifactOwnership> templates,
		Map<String, GeneratedArtifactOwnership> tags
)
{
	public static OwnershipConfiguration defaults()
	{
		return new OwnershipConfiguration(
				GeneratedArtifactOwnership.GENERATED,
				GeneratedArtifactOwnership.GENERATED,
				GeneratedArtifactOwnership.GENERATED,
				GeneratedArtifactOwnership.GENERATED,
				Map.of(),
				Map.of(),
				Map.of()
		);
	}

	public OwnershipConfiguration normalized()
	{
		return normalized(GenerationInputs.empty());
	}

	public OwnershipConfiguration normalized(final GenerationInputs inputs)
	{
		return new OwnershipConfiguration(
				baseModel == null ? defaults().baseModel() : baseModel,
				domainModel == null ? inferOwnership(inputs.domainModelSourceCodeFilePath(), defaults().domainModel()) :
						domainModel,
				persistenceModel == null ?
						inferOwnership(inputs.persistenceModelSourceCodeFilePath(), defaults().persistenceModel()) :
						persistenceModel,
				apiModel == null ? inferOwnership(inputs.apiModelSourceCodeFilePath(), defaults().apiModel()) :
						apiModel,
				normalizeMap(groups),
				normalizeMap(templates),
				normalizeMap(tags)
		);
	}

	private static Map<String, GeneratedArtifactOwnership> normalizeMap(
			final Map<String, GeneratedArtifactOwnership> values)
	{
		if (values == null || values.isEmpty())
		{
			return Map.of();
		}
		return Map.copyOf(new LinkedHashMap<>(values));
	}

	private static GeneratedArtifactOwnership inferOwnership(
			final String sourceCodePath,
			final GeneratedArtifactOwnership defaultOwnership)
	{
		return sourceCodePath == null || sourceCodePath.isBlank() ? defaultOwnership : GeneratedArtifactOwnership.USER;
	}
}