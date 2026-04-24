package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipCardinality;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipKind;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipReconciliationStrategy;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipGenerationConfigurationValidatorTest
{
	private final RelationshipGenerationConfigurationValidator validator =
			new RelationshipGenerationConfigurationValidator();

	@Test
	void acceptsMatchingOneRelationshipConfiguration()
	{
		var model = Model.of(
				"TaskModel",
				"de.gupta.clean.crud.implementation.examples.task.domain.model",
				Path.of("src/main/java"),
				List.of("V"),
				Set.of(Property.of("version", "Optional<V>", "java.util.Optional<V>")));

		assertDoesNotThrow(() -> validator.validate(model, List.of(relationship(
				"version",
				"Version",
				"de.gupta.clean.crud.implementation.examples.version.domain.model.VersionModel",
				RelationshipKind.OWNED,
				RelationshipCardinality.ONE,
				RelationshipReconciliationStrategy.REPLACE,
				"java.lang.Long",
				"java.lang.Long",
				"java.util.UUID",
				true,
				true,
				true,
				true,
				true,
				true,
				true))));
	}

	@Test
	void rejectsUnconfiguredRelationshipCandidates()
	{
		var model = Model.of(
				"TaskModel",
				"de.gupta.clean.crud.implementation.examples.task.domain.model",
				Path.of("src/main/java"),
				List.of("V"),
				Set.of(Property.of("version", "Optional<V>", "java.util.Optional<V>")));

		assertThrows(IllegalArgumentException.class, () -> validator.validate(model, List.of()));
	}

	@Test
	void rejectsMissingRelationshipKind()
	{
		var model = Model.of(
				"TaskModel",
				"de.gupta.clean.crud.implementation.examples.task.domain.model",
				Path.of("src/main/java"),
				List.of("V"),
				Set.of(Property.of("version", "Optional<V>", "java.util.Optional<V>")));

		assertThrows(IllegalArgumentException.class, () -> validator.validate(model, List.of(
				relationship(
						"version",
						"Version",
						"de.gupta.clean.crud.implementation.examples.version.domain.model.VersionModel",
						null,
						RelationshipCardinality.ONE,
						RelationshipReconciliationStrategy.REPLACE,
						"java.lang.Long",
						"java.lang.Long",
						"java.util.UUID",
						true,
						true,
						true,
						true,
						true,
						true,
						true))));
	}

	@Test
	void rejectsCardinalityMismatch()
	{
		var model = Model.of(
				"TaskModel",
				"de.gupta.clean.crud.implementation.examples.task.domain.model",
				Path.of("src/main/java"),
				List.of("N"),
				Set.of(Property.of("notes", "Collection<N>", "java.util.Collection<N>")));

		assertThrows(IllegalArgumentException.class, () -> validator.validate(model, List.of(
				relationship(
						"notes",
						"Note",
						"de.gupta.clean.crud.implementation.examples.note.domain.model.NoteModel",
						RelationshipKind.OWNED,
						RelationshipCardinality.ONE,
						RelationshipReconciliationStrategy.REPLACE,
						"java.lang.Long",
						"java.lang.Long",
						"java.util.UUID",
						true,
						true,
						true,
						true,
						true,
						true,
						true))));
	}

	@Test
	void rejectsMergeByIdForOneRelationship()
	{
		var model = Model.of(
				"TaskModel",
				"de.gupta.clean.crud.implementation.examples.task.domain.model",
				Path.of("src/main/java"),
				List.of("V"),
				Set.of(Property.of("version", "V", "V")));

		assertThrows(IllegalArgumentException.class, () -> validator.validate(model, List.of(
				relationship(
						"version",
						"Version",
						"de.gupta.clean.crud.implementation.examples.version.domain.model.VersionModel",
						RelationshipKind.OWNED,
						RelationshipCardinality.ONE,
						RelationshipReconciliationStrategy.MERGE_BY_ID,
						"java.lang.Long",
						"java.lang.Long",
						"java.util.UUID",
						true,
						true,
						true,
						true,
						true,
						true,
						true))));
	}

	@Test
	void normalizesReferencedDefaults()
	{
		var normalized = relationship(
				"organisation",
				"Organisation",
				"de.gupta.clean.crud.implementation.examples.organisation.domain.model.OrganisationModel",
				RelationshipKind.REFERENCED,
				RelationshipCardinality.ONE,
				null,
				"java.lang.Long",
				"java.lang.Long",
				"java.util.UUID",
				null,
				null,
				null,
				null,
				null,
				null,
				null)
				.normalized();

		assertEquals(RelationshipReconciliationStrategy.REPLACE, normalized.reconciliationStrategy());
		assertFalse(normalized.cascadeCreate());
		assertTrue(normalized.cascadeUpdate());
		assertFalse(normalized.cascadeDelete());
		assertFalse(normalized.orphanDelete());
		assertTrue(normalized.hydrateOnFetch());
	}

	private static RelationshipGenerationConfiguration relationship(
			final String masterProperty,
			final String satelliteAggregate,
			final String satelliteBaseModelType,
			final RelationshipKind relationshipKind,
			final RelationshipCardinality cardinality,
			final RelationshipReconciliationStrategy reconciliationStrategy,
			final String satelliteApiIdType,
			final String satelliteDomainIdType,
			final String satellitePersistenceIdType,
			final Boolean cascadeCreate,
			final Boolean cascadeUpdate,
			final Boolean cascadeDelete,
			final Boolean orphanDelete,
			final Boolean hydrateOnFetch,
			final Boolean generateNestedCreate,
			final Boolean generateNestedUpdate)
	{
		return new RelationshipGenerationConfiguration(
				masterProperty,
				satelliteAggregate,
				satelliteBaseModelType,
				relationshipKind,
				cardinality,
				reconciliationStrategy,
				satelliteApiIdType,
				satelliteDomainIdType,
				satellitePersistenceIdType,
				cascadeCreate,
				cascadeUpdate,
				cascadeDelete,
				orphanDelete,
				hydrateOnFetch,
				generateNestedCreate,
				generateNestedUpdate);
	}
}
