package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RootAggregateIdConfiguration;

import java.util.List;

record LoadedGenerationSpecification(
		RootAggregateIdConfiguration rootAggregateIds,
		List<RelationshipGenerationConfiguration> relationships)
{
}
