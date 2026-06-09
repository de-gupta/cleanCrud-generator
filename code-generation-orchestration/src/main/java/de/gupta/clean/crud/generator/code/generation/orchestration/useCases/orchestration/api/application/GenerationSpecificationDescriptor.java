package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.PostCommitHookGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RootAggregateIdConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.SubprocessGenerationConfiguration;
import de.gupta.clean.crud.template.domain.relationship.Relationship;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record GenerationSpecificationDescriptor(
		Class<?> baseModelClass,
		Collection<Relationship> relationships,
		RootAggregateIdConfiguration rootAggregateIds,
		PostCommitHookGenerationConfiguration postCommitHooks,
		SubprocessGenerationConfiguration subprocesses)
{
	public GenerationSpecificationDescriptor
	{
		Objects.requireNonNull(baseModelClass, "baseModelClass");
		relationships = List.copyOf(Objects.requireNonNull(relationships, "relationships"));
		rootAggregateIds = rootAggregateIds == null ? RootAggregateIdConfiguration.defaults() : rootAggregateIds;
		postCommitHooks = postCommitHooks == null ? PostCommitHookGenerationConfiguration.defaults() : postCommitHooks;
		subprocesses = subprocesses == null ? SubprocessGenerationConfiguration.defaults() : subprocesses;
	}
}
