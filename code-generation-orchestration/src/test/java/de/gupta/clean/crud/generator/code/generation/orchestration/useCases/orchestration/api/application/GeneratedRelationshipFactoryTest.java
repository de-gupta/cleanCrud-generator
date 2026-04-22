package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RelationshipKind;
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
				List.of(),
				Set.of(Property.of(
						"version",
						"Optional<VersionAPIModelResponse>",
						"java.util.Optional<example.version.VersionAPIModelResponse>")));

		var generated = factory.create(model, List.of(new RelationshipGenerationConfiguration(
				"version",
				"Version",
				RelationshipKind.OWNED,
				null,
				null,
				"Long",
				true,
				true,
				false,
				false,
				true,
				true,
				true
		)));

		assertEquals(1, generated.size());
		assertEquals("ONE", generated.getFirst().cardinality());
		assertEquals("REPLACE", generated.getFirst().reconciliationStrategy());
		assertEquals("Task", generated.getFirst().masterAggregate());
	}

	@Test
	void defaultsManyRelationshipSettingsFromPropertyShape()
	{
		var model = Model.of(
				"TaskModel",
				"example.task",
				Path.of("src/main/java"),
				List.of(),
				Set.of(Property.of(
						"notes",
						"Collection<NoteAPIModelResponse>",
						"java.util.Collection<example.note.NoteAPIModelResponse>")));

		var generated = factory.create(model, List.of(new RelationshipGenerationConfiguration(
				"notes",
				"Note",
				RelationshipKind.REFERENCED,
				null,
				null,
				"Long",
				false,
				true,
				false,
				false,
				true,
				false,
				false
		)));

		assertEquals("MANY", generated.getFirst().cardinality());
		assertEquals("MERGE_BY_ID", generated.getFirst().reconciliationStrategy());
	}
}
