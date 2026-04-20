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
				List.of(),
				Set.of(Property.of(
						"version",
						"Optional<VersionAPIModelResponse>",
						"java.util.Optional<de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelResponse>")));

		assertDoesNotThrow(() -> validator.validate(model, List.of(new RelationshipGenerationConfiguration(
				"version",
				"Version",
				RelationshipKind.OWNED,
				RelationshipCardinality.ONE,
				RelationshipReconciliationStrategy.REPLACE,
				"Long",
				true,
				true,
				true,
				true,
				true,
				true,
				true
		))));
	}

	@Test
	void rejectsUnconfiguredRelationshipCandidates()
	{
		var model = Model.of(
				"TaskModel",
				"de.gupta.clean.crud.implementation.examples.task.domain.model",
				Path.of("src/main/java"),
				List.of(),
				Set.of(Property.of(
						"version",
						"Optional<VersionAPIModelResponse>",
						"java.util.Optional<de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelResponse>")));

		assertThrows(IllegalArgumentException.class, () -> validator.validate(model, List.of()));
	}

	@Test
	void rejectsMissingRelationshipKind()
	{
		var model = Model.of(
				"TaskModel",
				"de.gupta.clean.crud.implementation.examples.task.domain.model",
				Path.of("src/main/java"),
				List.of(),
				Set.of(Property.of(
						"version",
						"Optional<VersionAPIModelResponse>",
						"java.util.Optional<de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelResponse>")));

		assertThrows(IllegalArgumentException.class, () -> validator.validate(model, List.of(
				new RelationshipGenerationConfiguration(
						"version",
						"Version",
						null,
						RelationshipCardinality.ONE,
						RelationshipReconciliationStrategy.REPLACE,
						"Long",
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
				List.of(),
				Set.of(Property.of(
						"notes",
						"Collection<NoteAPIModelResponse>",
						"java.util.Collection<de.gupta.clean.crud.implementation.examples.note.useCases.crud.common.dto.NoteAPIModelResponse>")));

		assertThrows(IllegalArgumentException.class, () -> validator.validate(model, List.of(
				new RelationshipGenerationConfiguration(
						"notes",
						"Note",
						RelationshipKind.OWNED,
						RelationshipCardinality.ONE,
						RelationshipReconciliationStrategy.REPLACE,
						"Long",
						true,
						true,
						true,
						true,
						true,
						true,
						true
				))));
	}

	@Test
	void rejectsMergeByIdForOneRelationship()
	{
		var model = Model.of(
				"TaskModel",
				"de.gupta.clean.crud.implementation.examples.task.domain.model",
				Path.of("src/main/java"),
				List.of(),
				Set.of(Property.of(
						"version",
						"VersionAPIModelResponse",
						"de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelResponse")));

		assertThrows(IllegalArgumentException.class, () -> validator.validate(model, List.of(
				new RelationshipGenerationConfiguration(
						"version",
						"Version",
						RelationshipKind.OWNED,
						RelationshipCardinality.ONE,
						RelationshipReconciliationStrategy.MERGE_BY_ID,
						"Long",
						true,
						true,
						true,
						true,
						true,
						true,
						true
				))));
	}

	@Test
	void normalizesReferencedDefaults()
	{
		var normalized = new RelationshipGenerationConfiguration(
				"organisation",
				"Organisation",
				RelationshipKind.REFERENCED,
				RelationshipCardinality.ONE,
				null,
				"Long",
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
}
