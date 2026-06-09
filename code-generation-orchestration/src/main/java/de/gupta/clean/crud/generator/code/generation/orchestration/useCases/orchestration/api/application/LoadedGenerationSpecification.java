package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.PostCommitHookGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RootAggregateIdConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.SubprocessGenerationConfiguration;

import java.util.List;

record LoadedGenerationSpecification(
		List<RelationshipGenerationConfiguration> relationships,
		RootAggregateIdConfiguration rootAggregateIds,
		PostCommitHookGenerationConfiguration postCommitHooks,
		SubprocessGenerationConfiguration subprocesses)
{
}
