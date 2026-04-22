package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.template.support.TemplateModelTestFixture;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceProjectionTest
{
	@Test
	void projectsPersistenceStorageSemantics()
	{
		TemplateModel model = TemplateModelTestFixture.createPersonTemplateModel();
		PersistenceProjection persistence = model.persistence();
		var metadata = model.composition().standaloneProperties().stream()
		                    .filter(property -> property.name().equals("metadata"))
		                    .findFirst()
		                    .orElseThrow();
		var externalId = model.composition().standaloneProperties().stream()
		                      .filter(property -> property.name().equals("externalId"))
		                      .findFirst()
		                      .orElseThrow();

		assertEquals("user_id", persistence.sqlIdentifier("user"));
		assertEquals("PersonPersistenceJpaConverters", persistence.jpaConvertersTypeName());
		assertEquals("person_persistence_model", persistence.modelTableName());
		assertEquals("person_persistence_model_history", persistence.historyTableName());
		assertEquals("person_domain_persistence_adapter_model", persistence.adapterTableName());
		assertEquals("person_domain_persistence_adapter_model_history", persistence.adapterHistoryTableName());
		assertEquals("Optional<Long>", persistence.propertyType(externalId));
		assertTrue(persistence.requiresJpaConverter(metadata));
		assertFalse(persistence.requiresJpaConverter(externalId));
		assertIterableEquals(List.of("metadata"),
				persistence.converterProperties().stream().map(property -> property.name()).toList());
		assertTrue(persistence.imports().contains("java.util.Collection"));
		assertTrue(persistence.imports().contains("java.util.Optional"));
	}
}