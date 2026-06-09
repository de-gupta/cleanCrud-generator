package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TemplateModelFactoryTest
{
	@Test
	void stripsDomainModelSuffixWhenPresent()
	{
		var templateModel = TemplateModelFactory.create(
				"de.gupta.clean.crud.implementation.examples.person.domain.model",
				"PersonModel");

		assertEquals("de.gupta.clean.crud.implementation.examples.person", templateModel.aggregate().basePackage());
	}

	@Test
	void preservesPlainModelPackageWhenNoDomainModelSuffixExists()
	{
		var templateModel = TemplateModelFactory.create("de.gupta.jeeves.task", "TaskModel");

		assertEquals("de.gupta.jeeves.task", templateModel.aggregate().basePackage());
	}

	@Test
	void createsSemanticCompositionRoot()
	{
		var templateModel = TemplateModelFactory.create("de.gupta.jeeves.task.domain.model", "TaskModel");

		assertNotNull(templateModel.aggregate());
		assertNotNull(templateModel.composition());
		assertNotNull(templateModel.domain());
		assertNotNull(templateModel.persistence());
		assertNotNull(templateModel.api());
		assertNotNull(templateModel.types());
		assertNotNull(templateModel.postCommitHooks());
		assertNotNull(templateModel.subprocesses());
	}
}
