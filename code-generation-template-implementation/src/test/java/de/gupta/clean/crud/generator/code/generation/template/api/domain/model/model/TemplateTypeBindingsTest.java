package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.template.support.TemplateModelTestFixture;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateTypeBindingsTest
{
	@Test
	void tracksGenericParametersAndCrossLayerDifferences()
	{
		TemplateTypeBindings types = TemplateModelTestFixture.createPersonTemplateModel().types();

		assertTrue(types.isGeneric());
		assertEquals(List.of("EXTERNAL_ID"), types.parameters());
		assertEquals(List.of("EXTERNAL_ID"), types.apiDomainDifferingParameters());
		assertEquals(List.of("EXTERNAL_ID"), types.persistenceDomainDifferingParameters());
	}
}
