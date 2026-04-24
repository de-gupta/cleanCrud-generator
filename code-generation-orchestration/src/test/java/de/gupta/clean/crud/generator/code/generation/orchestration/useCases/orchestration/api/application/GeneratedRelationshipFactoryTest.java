package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipKind;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.GeneratedRelationship;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeneratedRelationshipFactoryTest
{
	private final GeneratedRelationshipFactory factory = new GeneratedRelationshipFactory();

	@Test
	void defaultsOneRelationshipSettingsFromPropertyShape()
	{
		var model = Model.of(
				"TaskModel",
				"example.task",
				Path.of("src/main/java"),
				List.of("V"),
				Set.of(Property.of("version", "Optional<V>", "java.util.Optional<V>")));

		var generated = factory.create(model, List.of(relationship(
				"version",
				"Version",
				"example.version.domain.model.VersionModel",
				RelationshipKind.OWNED,
				null,
				null,
				"java.lang.Long",
				"java.lang.Long",
				"java.util.UUID",
				true,
				true,
				false,
				false,
				true,
				true,
				true)));

		GeneratedRelationship relationship = generated.getFirst();
		assertEquals(1, generated.size());
		assertEquals("V", relationship.genericPlaceholder());
		assertEquals("ONE", relationship.cardinality());
		assertEquals("REPLACE", relationship.reconciliationStrategy());
		assertEquals("Task", relationship.masterAggregate());
	}

	@Test
	void defaultsManyRelationshipSettingsFromPropertyShape()
	{
		var model = Model.of(
				"TaskModel",
				"example.task",
				Path.of("src/main/java"),
				List.of("N"),
				Set.of(Property.of("notes", "Collection<N>", "java.util.Collection<N>")));

		var generated = factory.create(model, List.of(relationship(
				"notes",
				"Note",
				"example.note.domain.model.NoteModel",
				RelationshipKind.REFERENCED,
				null,
				null,
				"java.lang.Long",
				"java.lang.Long",
				"java.util.UUID",
				false,
				true,
				false,
				false,
				true,
				false,
				false)));

		assertEquals("MANY", generated.getFirst().cardinality());
		assertEquals("MERGE_BY_ID", generated.getFirst().reconciliationStrategy());
	}

	private static RelationshipGenerationConfiguration relationship(
			final String masterProperty,
			final String satelliteAggregate,
			final String satelliteBaseModelType,
			final RelationshipKind relationshipKind,
			final de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipCardinality cardinality,
			final de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipReconciliationStrategy reconciliationStrategy,
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
