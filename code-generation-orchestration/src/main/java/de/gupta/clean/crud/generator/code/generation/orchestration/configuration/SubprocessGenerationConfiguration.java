package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record SubprocessGenerationConfiguration(
		boolean save,
		boolean update,
		boolean delete)
{
	public static SubprocessGenerationConfiguration defaults()
	{
		return new SubprocessGenerationConfiguration(false, false, false);
	}

	public boolean anyEnabled()
	{
		return save || update || delete;
	}

	public SubprocessGenerationConfiguration or(final SubprocessGenerationConfiguration other)
	{
		var normalized = other == null ? defaults() : other.normalized();
		return new SubprocessGenerationConfiguration(
				save || normalized.save,
				update || normalized.update,
				delete || normalized.delete);
	}

	public SubprocessGenerationConfiguration normalized()
	{
		return this;
	}
}
