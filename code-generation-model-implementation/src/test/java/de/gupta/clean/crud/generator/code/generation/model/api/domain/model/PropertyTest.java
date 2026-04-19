package de.gupta.clean.crud.generator.code.generation.model.api.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest
{
	@Test
	void detectsPlainAggregateRelationshipCandidate()
	{
		var property = Property.of(
				"version",
				"Version",
				"de.gupta.clean.crud.implementation.examples.version.domain.model.Version");

		assertFalse(property.optional());
		assertFalse(property.collectionValued());
		assertTrue(property.aggregateRelationshipCandidate());
		assertEquals("Version", property.candidateAggregateType());
	}

	@Test
	void detectsOptionalAggregateRelationshipCandidate()
	{
		var property = Property.of(
				"version",
				"Optional<Version>",
				"java.util.Optional<de.gupta.clean.crud.implementation.examples.version.domain.model.Version>");

		assertTrue(property.optional());
		assertFalse(property.collectionValued());
		assertTrue(property.aggregateRelationshipCandidate());
		assertEquals("Version", property.candidateAggregateType());
	}

	@Test
	void detectsCollectionAggregateRelationshipCandidate()
	{
		var property = Property.of(
				"notes",
				"Collection<Note>",
				"java.util.Collection<de.gupta.clean.crud.implementation.examples.note.domain.model.Note>");

		assertFalse(property.optional());
		assertTrue(property.collectionValued());
		assertTrue(property.aggregateRelationshipCandidate());
		assertEquals("Note", property.candidateAggregateType());
		assertEquals("Note", property.collectionElementType());
	}

	@Test
	void ignoresSimpleTypesAsRelationshipCandidates()
	{
		var property = Property.of("title", "String", "java.lang.String");

		assertFalse(property.aggregateRelationshipCandidate());
		assertEquals("", property.candidateAggregateType());
	}
}
