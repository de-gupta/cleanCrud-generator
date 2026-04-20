package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record RelationshipGenerationConfiguration(
		String masterProperty,
		String satelliteAggregate,
		RelationshipKind relationshipKind,
		RelationshipCardinality cardinality,
		RelationshipReconciliationStrategy reconciliationStrategy,
		String satelliteApiIdType,
		Boolean cascadeCreate,
		Boolean cascadeUpdate,
		Boolean cascadeDelete,
		Boolean orphanDelete,
		Boolean hydrateOnFetch,
		Boolean generateNestedCreate,
		Boolean generateNestedUpdate
)
{
	public RelationshipGenerationConfiguration normalized()
	{
		RelationshipKind normalizedRelationshipKind = relationshipKind;
		RelationshipCardinality normalizedCardinality = cardinality;
		return new RelationshipGenerationConfiguration(
				normalize(masterProperty),
				normalizeAggregateName(satelliteAggregate),
				normalizedRelationshipKind,
				normalizedCardinality,
				reconciliationStrategy == null && normalizedCardinality == RelationshipCardinality.ONE
						? RelationshipReconciliationStrategy.REPLACE
						: reconciliationStrategy == null && normalizedCardinality == RelationshipCardinality.MANY
						  ? RelationshipReconciliationStrategy.MERGE_BY_ID
						  : reconciliationStrategy,
				normalize(satelliteApiIdType),
				cascadeCreate != null
						? cascadeCreate
						: normalizedRelationshipKind == RelationshipKind.OWNED,
				cascadeUpdate == null || cascadeUpdate,
				cascadeDelete != null && cascadeDelete,
				orphanDelete != null && orphanDelete,
				hydrateOnFetch == null || hydrateOnFetch,
				generateNestedCreate == null || generateNestedCreate,
				generateNestedUpdate == null || generateNestedUpdate
		);
	}

	private static String normalize(final String value)
	{
		return value == null || value.isBlank() ? null : value.trim();
	}

	private static String normalizeAggregateName(final String value)
	{
		String normalized = normalize(value);
		if (normalized == null)
		{
			return null;
		}
		return normalized.endsWith("Model") ? normalized.substring(0, normalized.length() - "Model".length()) :
				normalized;
	}
}
