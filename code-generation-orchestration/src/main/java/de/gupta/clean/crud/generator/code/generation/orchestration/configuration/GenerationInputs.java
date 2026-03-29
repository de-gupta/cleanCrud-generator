package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record GenerationInputs(
		String baseModelSourceCodeFilePath,
		String domainModelSourceCodeFilePath,
		String persistenceModelSourceCodeFilePath,
		String apiModelSourceCodeFilePath
)
{
	public static GenerationInputs empty()
	{
		return new GenerationInputs(null, null, null, null);
	}

	public GenerationInputs normalized()
	{
		return new GenerationInputs(
				normalize(baseModelSourceCodeFilePath),
				normalize(domainModelSourceCodeFilePath),
				normalize(persistenceModelSourceCodeFilePath),
				normalize(apiModelSourceCodeFilePath)
		);
	}

	private static String normalize(final String value)
	{
		return value == null || value.isBlank() ? null : value.trim();
	}
}
