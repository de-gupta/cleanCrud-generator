package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record PostCommitHookGenerationConfiguration(
		boolean save,
		boolean update,
		boolean delete)
{
	public static PostCommitHookGenerationConfiguration defaults()
	{
		return new PostCommitHookGenerationConfiguration(false, false, false);
	}

	public boolean anyEnabled()
	{
		return save || update || delete;
	}

	public PostCommitHookGenerationConfiguration or(final PostCommitHookGenerationConfiguration other)
	{
		var normalized = other == null ? defaults() : other.normalized();
		return new PostCommitHookGenerationConfiguration(
				save || normalized.save,
				update || normalized.update,
				delete || normalized.delete);
	}

	public PostCommitHookGenerationConfiguration normalized()
	{
		return this;
	}
}
