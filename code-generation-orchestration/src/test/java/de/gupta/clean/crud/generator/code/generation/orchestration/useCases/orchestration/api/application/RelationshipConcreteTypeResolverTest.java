package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.LayerConcreteTypes;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.GeneratedRelationship;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelationshipConcreteTypeResolverTest
{
	private final RelationshipConcreteTypeResolver resolver = new RelationshipConcreteTypeResolver();

	@Test
	void overlaysRelationshipDrivenConcreteTypesAcrossAllLayers()
	{
		var configuredTypes = new LayerConcreteTypes(
				Map.of("Q", "java.lang.String"),
				Map.of("Q", "java.lang.String"),
				Map.of("Q", "java.lang.String"));
		var relationships = List.of(
				new GeneratedRelationship(
						Property.of("version", "Optional<V>", "java.util.Optional<V>"),
						"V",
						"Task",
						"Version",
						"example.version.domain.model.VersionModel",
						"OWNED",
						"ONE",
						"REPLACE",
						"java.lang.Long",
						"java.lang.Long",
						"java.util.UUID",
						true,
						true,
						false,
						false,
						true,
						true,
						true),
				new GeneratedRelationship(
						Property.of("notes", "Collection<N>", "java.util.Collection<N>"),
						"N",
						"Task",
						"Note",
						"example.note.domain.model.NoteModel",
						"OWNED",
						"MANY",
						"MERGE_BY_ID",
						"java.lang.Long",
						"java.lang.Long",
						"java.util.UUID",
						true,
						true,
						false,
						false,
						true,
						true,
						true));

		var resolved = resolver.merge(configuredTypes, relationships);

		assertEquals("java.lang.String", resolved.domain().get("Q"));
		assertEquals(
				"de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel<java.lang.Long, VersionDomainModel>",
				resolved.domain().get("V"));
		assertEquals(
				"de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel<java.lang.Long, NoteDomainModel>",
				resolved.domain().get("N"));
		assertEquals("java.util.UUID", resolved.persistence().get("V"));
		assertEquals("java.util.UUID", resolved.persistence().get("N"));
		assertEquals("VersionAPIModelResponse", resolved.api().get("V"));
		assertEquals("NoteAPIModelResponse", resolved.api().get("N"));
	}
}
