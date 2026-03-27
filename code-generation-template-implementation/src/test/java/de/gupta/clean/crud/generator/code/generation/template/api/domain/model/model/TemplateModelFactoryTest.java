package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateModelFactoryTest
{
	@Test
	void stripsDomainModelSuffixWhenPresent()
	{
		var templateModel = TemplateModelFactory.create(
				"de.gupta.clean.crud.implementation.examples.person.domain.model",
				"PersonModel");

		assertEquals("de.gupta.clean.crud.implementation.examples.person", templateModel.basePackage());
	}

	@Test
	void preservesPlainModelPackageWhenNoDomainModelSuffixExists()
	{
		var templateModel = TemplateModelFactory.create("de.gupta.jeeves.task", "TaskModel");

		assertEquals("de.gupta.jeeves.task", templateModel.basePackage());
	}
}
