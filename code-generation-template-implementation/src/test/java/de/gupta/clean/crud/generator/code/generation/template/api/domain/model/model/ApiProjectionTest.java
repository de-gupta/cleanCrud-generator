package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.template.support.TemplateModelTestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiProjectionTest
{
	@Test
	void projectsApiSpecificTypesAndImports()
	{
		TemplateModel model = TemplateModelTestFixture.createPersonTemplateModel();
		ApiProjection api = model.api();
		var externalId = model.composition().standaloneProperties().stream()
		                      .filter(property -> property.name().equals("externalId"))
		                      .findFirst()
		                      .orElseThrow();

		assertEquals("String", api.concreteType("EXTERNAL_ID"));
		assertEquals("Optional<String>", api.propertyType(externalId));
		assertEquals("String", api.boxedResolvedType("EXTERNAL_ID"));
		assertTrue(api.imports().contains(
				"de.gupta.clean.crud.generator.example.person.useCases.crud.common.dto.ManagerAPIModelUpdatePatch"));
		assertTrue(api.imports().contains("java.time.LocalDate"));
	}
}
