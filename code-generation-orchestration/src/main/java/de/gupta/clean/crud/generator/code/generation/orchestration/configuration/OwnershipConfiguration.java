package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record OwnershipConfiguration(
		GeneratedArtifactOwnership baseModel,
		GeneratedArtifactOwnership domainModel,
		GeneratedArtifactOwnership persistenceModel,
		GeneratedArtifactOwnership apiModel
)
{
	public static OwnershipConfiguration defaults()
	{
		return new OwnershipConfiguration(
				GeneratedArtifactOwnership.GENERATED,
				GeneratedArtifactOwnership.GENERATED,
				GeneratedArtifactOwnership.GENERATED,
				GeneratedArtifactOwnership.GENERATED
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
				apiModel == null ? inferOwnership(inputs.apiModelSourceCodeFilePath(), defaults().apiModel()) : apiModel
		);
	}

	private static GeneratedArtifactOwnership inferOwnership(
			final String sourceCodePath,
			final GeneratedArtifactOwnership defaultOwnership)
	{
		return sourceCodePath == null || sourceCodePath.isBlank() ? defaultOwnership : GeneratedArtifactOwnership.USER;
	}
}