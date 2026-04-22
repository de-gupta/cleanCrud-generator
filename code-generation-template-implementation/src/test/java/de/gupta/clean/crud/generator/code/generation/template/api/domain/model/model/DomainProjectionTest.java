package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.template.support.TemplateModelTestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainProjectionTest
{
	@Test
	void projectsDomainTypesAndImports()
	{
		TemplateModel model = TemplateModelTestFixture.createPersonTemplateModel();
		DomainProjection domain = model.domain();
		var externalId = model.composition().standaloneProperties().stream()
		                      .filter(property -> property.name().equals("externalId"))
		                      .findFirst()
		                      .orElseThrow();

		assertEquals("UUID", domain.concreteType("EXTERNAL_ID"));
		assertEquals("UUID", domain.resolvedType("EXTERNAL_ID"));
		assertEquals("Optional<UUID>", domain.propertyType(externalId));
		assertEquals("Optional<UUID>", domain.builderPropertyType(externalId));
		assertTrue(domain.genericImports().contains("java.util.UUID"));
		assertTrue(domain.imports().contains(
				"de.gupta.clean.crud.generator.example.person.useCases.crud.common.dto.AddressAPIModelResponse"));
		assertTrue(domain.imports().contains("java.time.LocalDate"));
	}
}
