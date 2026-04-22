package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.template.support.TemplateModelTestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AggregateDescriptorTest
{
	@Test
	void exposesAggregateIdentityAndNaming()
	{
		AggregateDescriptor aggregate = TemplateModelTestFixture.createPersonTemplateModel().aggregate();

		assertEquals("de.gupta.clean.crud.generator.example.person.domain.model", aggregate.packageName());
		assertEquals("de.gupta.clean.crud.generator.example.person", aggregate.basePackage());
		assertEquals("PersonModel", aggregate.modelName());
		assertEquals("Person", aggregate.baseName());
		assertEquals("person", aggregate.beanNamePrefix());
		assertEquals("personDomainModelBuilder", aggregate.qualifier("DomainModelBuilder"));
		assertEquals("PersonDuplicateKey", aggregate.duplicateKeyTypeName());
		assertTrue(aggregate.historized());
	}
}
