package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipCardinality;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipReconciliationStrategy;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RootAggregateIdConfiguration;
import de.gupta.clean.crud.template.domain.relationship.ReconciliationStrategy;
import de.gupta.clean.crud.template.domain.relationship.Relationship;
import de.gupta.clean.crud.template.domain.relationship.RelationshipKind;
import de.gupta.clean.crud.template.domain.relationship.Relationships;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
final class GenerationSpecificationConfigurationAssembler
{
	LoadedGenerationSpecification assemble(
			final Model model,
			final Relationships relationshipsDeclaration,
			final RootAggregateIdConfiguration rootAggregateIds)
	{
		validateBaseModel(model, relationshipsDeclaration);
		validateRootIds(rootAggregateIds);
		Map<String, Property> propertiesByName = model.properties().stream().collect(Collectors.toMap(
				Property::name,
				Function.identity(),
				(left, _) -> left,
				LinkedHashMap::new));
		validateRelationshipCoverage(model, relationshipsDeclaration, propertiesByName);
		validatePlaceholderConsistency(model, relationshipsDeclaration, propertiesByName);

		List<RelationshipGenerationConfiguration> relationships = relationshipsDeclaration.relationships().stream()
		                                                                                  .map(relationship -> toConfiguration(
																								  propertiesByName.get(
																										  relationship.propertyName()),
																								  relationship))
		                                                                                  .toList();
		return new LoadedGenerationSpecification(relationships);
	}

	private void validateBaseModel(final Model model, final Relationships relationshipsDeclaration)
	{
		String specificationBaseModel = relationshipsDeclaration.baseModelClass().getSimpleName();
		if (!model.modelName().equals(specificationBaseModel))
		{
			throw new IllegalArgumentException(
					"Relationships declaration targets base model `" + specificationBaseModel + "` but the configured base model source is `" + model.modelName() + "`");
		}
	}

	private void validateRootIds(final RootAggregateIdConfiguration rootAggregateIds)
	{
		if (rootAggregateIds.apiIdType() == null || rootAggregateIds.apiIdType().isBlank() ||
				rootAggregateIds.domainIdType() == null || rootAggregateIds.domainIdType().isBlank() ||
				rootAggregateIds.persistenceIdType() == null || rootAggregateIds.persistenceIdType().isBlank())
		{
			throw new IllegalArgumentException(
					"Generator configuration must declare api/domain/persistence id types for the root aggregate");
		}
	}

	private void validateRelationshipCoverage(
			final Model model,
			final Relationships relationshipsDeclaration,
			final Map<String, Property> propertiesByName)
	{
		var configuredProperties =
				relationshipsDeclaration.relationships().stream().map(Relationship::propertyName)
				                        .collect(Collectors.toSet());
		model.properties().stream()
		     .filter(property -> property.relationshipEligible(model.genericTypeParameters()))
		     .filter(property -> !configuredProperties.contains(property.name()))
		     .findFirst()
		     .ifPresent(property ->
			 {
				 throw new IllegalArgumentException(
						 "Property `" + property.name() + "` on model `" + model.modelName() +
								 "` uses generic placeholder `" + property.relationshipGenericPlaceholder(
								 model.genericTypeParameters()) +
								 "` and therefore requires an explicit relationship declaration");
			 });
		for (Relationship relationship : relationshipsDeclaration.relationships())
		{
			Property property = propertiesByName.get(relationship.propertyName());
			if (property == null)
			{
				throw new IllegalArgumentException(
						"Relationship declaration references unknown property `" + relationship.propertyName() + "` on model `" + model.modelName() + "`");
			}
			if (!property.relationshipEligible(model.genericTypeParameters()))
			{
				throw new IllegalArgumentException(
						"Property `" + relationship.propertyName() + "` on model `" + model.modelName() +
								"` is not a supported relationship property. Relationship properties must be direct generic placeholders or Optional/Collection over one.");
			}
			if (relationship.reconciliationStrategy() == ReconciliationStrategy.MERGE_BY_ID && !property.collectionValued())
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.propertyName() + "` cannot use MERGE_BY_ID with cardinality ONE");
			}
		}
	}

	private void validatePlaceholderConsistency(
			final Model model,
			final Relationships relationshipsDeclaration,
			final Map<String, Property> propertiesByName)
	{
		Map<String, Relationship> byPlaceholder = new LinkedHashMap<>();
		for (Relationship relationship : relationshipsDeclaration.relationships())
		{
			Property property = propertiesByName.get(relationship.propertyName());
			String placeholder = property.relationshipGenericPlaceholder(model.genericTypeParameters());
			Relationship existing = byPlaceholder.putIfAbsent(placeholder, relationship);
			if (existing == null)
			{
				continue;
			}
			if (!existing.satelliteBaseModelClass().equals(relationship.satelliteBaseModelClass()) ||
					!existing.satelliteApiIdType().equals(relationship.satelliteApiIdType()) ||
					!existing.satelliteDomainIdType().equals(relationship.satelliteDomainIdType()) ||
					!existing.satellitePersistenceIdType().equals(relationship.satellitePersistenceIdType()))
			{
				throw new IllegalArgumentException(
						"Generic placeholder `" + placeholder + "` is reused by multiple relationship properties on model `" + model.modelName() +
								"`, but those properties do not agree on the satellite aggregate and id types");
			}
		}
	}

	private RelationshipGenerationConfiguration toConfiguration(final Property property,
	                                                            final Relationship relationship)
	{
		RelationshipCardinality cardinality =
				property.collectionValued() ? RelationshipCardinality.MANY : RelationshipCardinality.ONE;
		RelationshipReconciliationStrategy reconciliation = relationship.reconciliationStrategy() == null
				? cardinality == RelationshipCardinality.ONE ? RelationshipReconciliationStrategy.REPLACE :
				  RelationshipReconciliationStrategy.MERGE_BY_ID
				: relationship.reconciliationStrategy() == ReconciliationStrategy.REPLACE
				  ? RelationshipReconciliationStrategy.REPLACE
				  : RelationshipReconciliationStrategy.MERGE_BY_ID;
		String satelliteAggregate = relationship.satelliteBaseModelClass().getSimpleName();
		if (satelliteAggregate.endsWith("Model"))
		{
			satelliteAggregate = satelliteAggregate.substring(0, satelliteAggregate.length() - "Model".length());
		}
		boolean owned = relationship.relationshipKind() == RelationshipKind.OWNED;
		de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipKind relationshipKind =
				relationship.relationshipKind() == RelationshipKind.OWNED
						?
						de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipKind.OWNED
						:
						de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipKind.REFERENCED;
		return new RelationshipGenerationConfiguration(
				relationship.propertyName(),
				satelliteAggregate,
				relationship.satelliteBaseModelClass().getCanonicalName(),
				relationshipKind,
				cardinality,
				reconciliation,
				relationship.satelliteApiIdType().getCanonicalName(),
				relationship.satelliteDomainIdType().getCanonicalName(),
				relationship.satellitePersistenceIdType().getCanonicalName(),
				relationship.lifecycleSemantics().cascadeCreate(),
				relationship.lifecycleSemantics().cascadeUpdate(),
				relationship.lifecycleSemantics().cascadeDelete(),
				relationship.lifecycleSemantics().orphanDelete(),
				relationship.lifecycleSemantics().hydrateOnFetch(),
				owned,
				owned);
	}
}