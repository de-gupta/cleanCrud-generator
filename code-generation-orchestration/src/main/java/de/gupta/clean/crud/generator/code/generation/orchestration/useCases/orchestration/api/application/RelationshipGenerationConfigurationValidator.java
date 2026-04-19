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
		     .filter(Property::aggregateRelationshipCandidate)
		     .filter(property -> !configuredProperties.contains(property.name()))
		     .findFirst()
		     .ifPresent(property ->
			 {
				 throw new IllegalArgumentException(
						 "Property `" + property.name() + "` on model `" + model.modelName() +
								 "` looks like a relationship candidate (`" + property.candidateAggregateType() +
								 "`), but no explicit relationship configuration was provided. Relationship generation requires satelliteDomainIdType and lifecycle configuration.");
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
			if (!property.aggregateRelationshipCandidate())
			{
				throw new IllegalArgumentException(
						"Property `" + relationship.masterProperty() + "` on model `" + model.modelName() +
								"` is not a supported relationship candidate");
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
			if (!relationship.satelliteAggregate().equals(property.candidateAggregateType()))
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` declares satellite aggregate `" +
								relationship.satelliteAggregate() + "` but the property type implies `" +
								property.candidateAggregateType() + "`");
			}
			if (relationship.satelliteDomainIdType() == null || relationship.satelliteDomainIdType().isBlank())
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() +
								"` must declare a satelliteDomainIdType");
			}
			String expectedResponseType = relationship.satelliteAggregate() + "APIModelResponse";
			String actualResponseType =
					property.collectionValued() ? simpleTypeName(property.collectionElementType()) :
							simpleTypeName(property.baseType());
			if (!expectedResponseType.equals(actualResponseType))
			{
				throw new IllegalArgumentException(
						"Relationship `" + relationship.masterProperty() + "` must use `" + expectedResponseType +
								"` in the base model, but found `" + actualResponseType + "`");
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

	private String simpleTypeName(final String typeName)
	{
		int genericStart = typeName.indexOf('<');
		String rawType = genericStart >= 0 ? typeName.substring(0, genericStart) : typeName;
		int lastPackageSeparator = rawType.lastIndexOf('.');
		return lastPackageSeparator >= 0 ? rawType.substring(lastPackageSeparator + 1) : rawType;
	}
}