package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.GeneratedRelationship;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
final class GeneratedRelationshipFactory
{
	List<GeneratedRelationship> create(
			final Model model,
			final List<de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration> relationships)
	{
		return relationships.stream()
		                    .map(relationship -> model.properties()
		                                              .stream()
		                                              .filter(property -> property.name().equals(
															  relationship.masterProperty()))
		                                              .findFirst()
		                                              .map(property -> createRelationship(model, property,
															  relationship.normalized()))
		                                              .orElseThrow())
		                    .toList();
	}

	private GeneratedRelationship createRelationship(
			final Model model,
			final de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property property,
			final de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration relationship)
	{
		var effectiveCardinality = relationship.cardinality() == null ? property.collectionValued() ? "MANY" : "ONE" :
				relationship.cardinality().name();
		var effectiveReconciliationStrategy = relationship.reconciliationStrategy() == null
				? "ONE".equals(effectiveCardinality) ? "REPLACE" : "MERGE_BY_ID"
				: relationship.reconciliationStrategy().name();
		return new GeneratedRelationship(
				property,
				property.relationshipGenericPlaceholder(model.genericTypeParameters()),
				model.modelName().endsWith("Model")
						? model.modelName().substring(0, model.modelName().length() - "Model".length())
						: model.modelName(),
				relationship.satelliteAggregate(),
				relationship.satelliteBaseModelType(),
				relationship.relationshipKind().name(),
				effectiveCardinality,
				effectiveReconciliationStrategy,
				relationship.satelliteApiIdType(),
				relationship.satelliteDomainIdType(),
				relationship.satellitePersistenceIdType(),
				relationship.cascadeCreate(),
				relationship.cascadeUpdate(),
				relationship.cascadeDelete(),
				relationship.orphanDelete(),
				relationship.hydrateOnFetch(),
				relationship.generateNestedCreate(),
				relationship.generateNestedUpdate());
	}
}
