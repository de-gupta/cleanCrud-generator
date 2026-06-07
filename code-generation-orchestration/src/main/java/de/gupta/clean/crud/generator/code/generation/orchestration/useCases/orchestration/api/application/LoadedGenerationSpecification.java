package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration;

import java.util.List;

record LoadedGenerationSpecification(
		List<RelationshipGenerationConfiguration> relationships)
{
}
