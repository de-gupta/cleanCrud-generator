package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipCardinality;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipReconciliationStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
final class RelationshipGenerationConfigurationValidator
{
	void validate(final Model model, final java.util.List<RelationshipGenerationConfiguration> relationships)
	{
		Map<String, Property> propertiesByName = model.properties()
		                                              .stream()
		                                              .collect(Collectors.toMap(Property::name,
															  Function.identity(),
															  (left, _) -> left));
		Set<String> configuredProperties = relationships.stream()
		                                                .map(RelationshipGenerationConfiguration::masterProperty)
		                                                .collect(Collectors.toSet());

		model.properties()
		     .stream()
		     .filter(property -> property.relationshipEligible(model.genericTypeParameters()))
		     .filter(property -> !configuredProperties.contains(property.name()))
		     .findFirst()
		     .ifPresent(property ->
			 {
				 throw new IllegalArgumentException(
						 "Property `" + property.name() + "` on model `" + model.modelName() +
								 "` uses generic placeholder `" + property.relationshipGenericPlaceholder(
								 model.genericTypeParameters()) +
								 "` and therefore requires an explicit relationship configuration or generation specification.");
			 });

		for (RelationshipGenerationConfiguration relationship : relationships)
		{
			Property property = propertiesByName.get(relationship.masterProperty());
			if (property == null)
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` does not exist on model `" +
								model.modelName() + "`");
			}
			if (!property.relationshipEligible(model.genericTypeParameters()))
			{
				throw new IllegalArgumentException(
						"Property `" + relationship.masterProperty() + "` on model `" + model.modelName() +
								"` is not a supported relationship candidate. Relationship properties must use a generic placeholder or Optional/Collection over one.");
			}
			RelationshipCardinality inferredCardinality =
					property.collectionValued() ? RelationshipCardinality.MANY : RelationshipCardinality.ONE;
			RelationshipCardinality effectiveCardinality =
					relationship.cardinality() == null ? inferredCardinality : relationship.cardinality();
			if (effectiveCardinality != inferredCardinality)
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` declares cardinality `" +
								effectiveCardinality + "` but the property shape implies `" +
								inferredCardinality + "`");
			}
			if (relationship.satelliteAggregate() == null || relationship.satelliteAggregate().isBlank())
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` must declare a satelliteAggregate");
			}
			if (relationship.satelliteBaseModelType() == null || relationship.satelliteBaseModelType().isBlank())
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` must declare a satelliteBaseModelType");
			}
			if (relationship.relationshipKind() == null)
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` must declare a relationshipKind");
			}
			if (relationship.satelliteApiIdType() == null || relationship.satelliteApiIdType().isBlank())
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` must declare a satelliteApiIdType");
			}
			if (relationship.satelliteDomainIdType() == null || relationship.satelliteDomainIdType().isBlank())
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` must declare a satelliteDomainIdType");
			}
			if (relationship.satellitePersistenceIdType() == null || relationship.satellitePersistenceIdType()
			                                                                     .isBlank())
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` must declare a satellitePersistenceIdType");
			}
			if (effectiveCardinality == RelationshipCardinality.ONE &&
					relationship.reconciliationStrategy() == RelationshipReconciliationStrategy.MERGE_BY_ID)
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() +
								"` cannot use MERGE_BY_ID with cardinality ONE");
			}
		}
	}
}