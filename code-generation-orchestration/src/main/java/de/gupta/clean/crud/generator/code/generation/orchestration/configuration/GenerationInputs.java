package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record GenerationInputs(
		String baseModelSourceCodeFilePath,
		String domainModelSourceCodeFilePath,
		String persistenceModelSourceCodeFilePath,
		String apiModelSourceCodeFilePath,
		String relationshipsSourceCodeFilePath
)
{
	public static GenerationInputs empty()
	{
		return new GenerationInputs(null, null, null, null, null);
	}

	public GenerationInputs(
			final String baseModelSourceCodeFilePath,
			final String domainModelSourceCodeFilePath,
			final String persistenceModelSourceCodeFilePath,
			final String apiModelSourceCodeFilePath)
	{
		this(baseModelSourceCodeFilePath, domainModelSourceCodeFilePath, persistenceModelSourceCodeFilePath,
				apiModelSourceCodeFilePath, null);
	}

	public GenerationInputs normalized()
	{
		return new GenerationInputs(
				normalize(baseModelSourceCodeFilePath),
				normalize(domainModelSourceCodeFilePath),
				normalize(persistenceModelSourceCodeFilePath),
				normalize(apiModelSourceCodeFilePath),
				normalize(relationshipsSourceCodeFilePath)
		);
	}

	private static String normalize(final String value)
	{
		return value == null || value.isBlank() ? null : value.trim();
	}
}
