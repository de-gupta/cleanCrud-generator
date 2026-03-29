package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record CodeGenerationConfiguration(
		GenerationInputs inputs,
		LayerConcreteTypes genericTypes,
		GenerationSelection generation,
		OwnershipConfiguration ownership,
		OverwriteConfiguration overwrite,
		boolean historized
)
{
	public static CodeGenerationConfiguration of(final String baseModelSourceCodeFilePath, final boolean historized)
	{
		return new CodeGenerationConfiguration(
				new GenerationInputs(baseModelSourceCodeFilePath, null, null, null),
				LayerConcreteTypes.defaults(),
				GenerationSelection.defaults(),
				OwnershipConfiguration.defaults(),
				OverwriteConfiguration.defaults(),
				historized
		);
	}

	public static CodeGenerationConfiguration of(final String baseModelSourceCodeFilePath)
	{
		return of(baseModelSourceCodeFilePath, false);
	}

	public CodeGenerationConfiguration
	{
		inputs = inputs == null ? GenerationInputs.empty() : inputs.normalized();
		genericTypes = genericTypes == null ? LayerConcreteTypes.defaults() : genericTypes.normalized();
		generation = generation == null ? GenerationSelection.defaults() : generation.normalized();
		ownership =
				ownership == null ? OwnershipConfiguration.defaults().normalized(inputs) : ownership.normalized(inputs);
		overwrite = overwrite == null ? OverwriteConfiguration.defaults() : overwrite.normalized();
	}
}