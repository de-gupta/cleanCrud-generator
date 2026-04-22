package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.template.support.TemplateModelTestFixture;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AggregateCompositionTest
{
	@Test
	void separatesStandalonePropertiesFromRelationships()
	{
		AggregateComposition composition = TemplateModelTestFixture.createPersonTemplateModel().composition();

		assertTrue(composition.hasRelationships());
		assertEquals(List.of("addresses", "manager"),
				composition.relationshipPropertyNames().stream().sorted().toList());
		assertIterableEquals(
				List.of("name", "birthDate", "externalId", "status", "metadata"),
				composition.standaloneProperties().stream().map(property -> property.name()).toList());
		assertIterableEquals(
				List.of("name", "status", "metadata"),
				composition.requiredProperties().stream().map(property -> property.name()).toList());
		assertEquals("Optional<EXTERNAL_ID>", composition.declaredBuilderPropertyType(
				composition.standaloneProperties().stream()
				           .filter(property -> property.name().equals("externalId"))
				           .findFirst()
				           .orElseThrow()));
	}
}