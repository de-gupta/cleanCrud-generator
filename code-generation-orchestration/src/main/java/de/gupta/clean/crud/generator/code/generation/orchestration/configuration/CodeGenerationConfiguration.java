package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record CodeGenerationConfiguration(
		GenerationInputs inputs,
		LayerConcreteTypes genericTypes,
		GenerationSelection generation,
		java.util.List<RelationshipGenerationConfiguration> relationships,
		RootAggregateIdConfiguration rootAggregateIds,
		OwnershipConfiguration ownership,
		OverwriteConfiguration overwrite,
		PostCommitHookGenerationConfiguration postCommitHooks,
		SubprocessGenerationConfiguration subprocesses,
		boolean historized
)
{
	public static CodeGenerationConfiguration of(final String baseModelSourceCodeFilePath, final boolean historized)
	{
		return new CodeGenerationConfiguration(
				new GenerationInputs(baseModelSourceCodeFilePath, null, null, null, null),
				LayerConcreteTypes.defaults(),
				GenerationSelection.defaults(),
				java.util.List.of(),
				RootAggregateIdConfiguration.defaults(),
				OwnershipConfiguration.defaults(),
				OverwriteConfiguration.defaults(),
				PostCommitHookGenerationConfiguration.defaults(),
				SubprocessGenerationConfiguration.defaults(),
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
		relationships = relationships == null ? java.util.List.of() :
				relationships.stream().map(RelationshipGenerationConfiguration::normalized).toList();
		rootAggregateIds =
				rootAggregateIds == null ? RootAggregateIdConfiguration.defaults() : rootAggregateIds.normalized();
		ownership =
				ownership == null ? OwnershipConfiguration.defaults().normalized(inputs) : ownership.normalized(inputs);
		overwrite = overwrite == null ? OverwriteConfiguration.defaults() : overwrite.normalized();
		postCommitHooks = postCommitHooks == null ? PostCommitHookGenerationConfiguration.defaults() :
				postCommitHooks.normalized();
		subprocesses = subprocesses == null ? SubprocessGenerationConfiguration.defaults() :
				subprocesses.normalized();
	}
}
