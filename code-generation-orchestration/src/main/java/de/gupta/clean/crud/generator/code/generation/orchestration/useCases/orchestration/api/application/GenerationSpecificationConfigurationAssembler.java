package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import de.gupta.clean.crud.template.generation.specification.AggregateGenerationSpec;
import de.gupta.clean.crud.template.generation.specification.RelationshipSpec;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.ReconciliationStrategy;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
final class GenerationSpecificationConfigurationAssembler
{
	LoadedGenerationSpecification assemble(final Model model, final AggregateGenerationSpec specification)
	{
		validateBaseModel(model, specification);
		validateRootIds(specification);
		Map<String, Property> propertiesByName = model.properties().stream().collect(Collectors.toMap(
				Property::name,
				Function.identity(),
				(left, _) -> left,
				LinkedHashMap::new));
		validateRelationshipCoverage(model, specification, propertiesByName);
		validatePlaceholderConsistency(model, specification, propertiesByName);

		List<RelationshipGenerationConfiguration> relationships = specification.relationships().stream()
		                                                                       .map(relationship -> toConfiguration(
																					   propertiesByName.get(
																							   relationship.propertyName()),
																					   relationship))
		                                                                       .toList();
		return new LoadedGenerationSpecification(
				new RootAggregateIdConfiguration(
						specification.rootAggregateIdTypes().apiIdType().getCanonicalName(),
						specification.rootAggregateIdTypes().domainIdType().getCanonicalName(),
						specification.rootAggregateIdTypes().persistenceIdType().getCanonicalName()),
				relationships);
	}

	private void validateBaseModel(final Model model, final AggregateGenerationSpec specification)
	{
		String specificationBaseModel = specification.baseModelClass().getSimpleName();
		if (!model.modelName().equals(specificationBaseModel))
		{
			throw new IllegalArgumentException(
					"Generation spec targets base model `" + specificationBaseModel + "` but the configured base model source is `" + model.modelName() + "`");
		}
	}

	private void validateRootIds(final AggregateGenerationSpec specification)
	{
		if (specification.rootAggregateIdTypes().apiIdType() == null ||
				specification.rootAggregateIdTypes().domainIdType() == null ||
				specification.rootAggregateIdTypes().persistenceIdType() == null)
		{
			throw new IllegalArgumentException(
					"Generation spec must declare api/domain/persistence id types for the root aggregate");
		}
	}

	private void validateRelationshipCoverage(
			final Model model,
			final AggregateGenerationSpec specification,
			final Map<String, Property> propertiesByName)
	{
		var configuredProperties =
				specification.relationships().stream().map(RelationshipSpec::propertyName).collect(Collectors.toSet());
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
								 "` and therefore requires an explicit relationship spec");
			 });
		for (RelationshipSpec relationship : specification.relationships())
		{
			Property property = propertiesByName.get(relationship.propertyName());
			if (property == null)
			{
				throw new IllegalArgumentException(
						"Relationship spec references unknown property `" + relationship.propertyName() + "` on model `" + model.modelName() + "`");
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
			final AggregateGenerationSpec specification,
			final Map<String, Property> propertiesByName)
	{
		Map<String, RelationshipSpec> byPlaceholder = new LinkedHashMap<>();
		for (RelationshipSpec relationship : specification.relationships())
		{
			Property property = propertiesByName.get(relationship.propertyName());
			String placeholder = property.relationshipGenericPlaceholder(model.genericTypeParameters());
			RelationshipSpec existing = byPlaceholder.putIfAbsent(placeholder, relationship);
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
	                                                            final RelationshipSpec relationship)
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
		RelationshipKind relationshipKind =
				relationship.relationshipKind() == de.gupta.clean.crud.template.generation.specification.RelationshipKind.OWNED
						? RelationshipKind.OWNED
						: RelationshipKind.REFERENCED;
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
				relationship.cascadeCreate(),
				relationship.cascadeUpdate(),
				relationship.cascadeDelete(),
				relationship.orphanDelete(),
				relationship.hydrateOnFetch(),
				relationshipKind == RelationshipKind.OWNED,
				relationshipKind == RelationshipKind.OWNED);
	}
}