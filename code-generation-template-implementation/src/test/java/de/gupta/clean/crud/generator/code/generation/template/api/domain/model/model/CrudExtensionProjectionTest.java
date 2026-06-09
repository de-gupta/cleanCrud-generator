package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.template.support.TemplateModelTestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrudExtensionProjectionTest
{
	@Test
	void exposesPostCommitHookProjectionSemantics()
	{
		var projection = TemplateModelTestFixture.createPersonTemplateModel().postCommitHooks();

		assertTrue(projection.anyEnabled());
		assertTrue(projection.saveEnabled());
		assertFalse(projection.updateEnabled());
		assertTrue(projection.deleteEnabled());
		assertEquals("de.gupta.clean.crud.generator.example.person.useCases.crud.postcommit.save",
				projection.savePackage());
		assertEquals("PersonSavePostCommitMutation", projection.saveTypeName());
		assertEquals("personDeletePostCommitMutation", projection.deleteQualifier());
	}

	@Test
	void exposesSubprocessProjectionSemantics()
	{
		var projection = TemplateModelTestFixture.createPersonTemplateModel().subprocesses();

		assertTrue(projection.anyEnabled());
		assertFalse(projection.saveEnabled());
		assertTrue(projection.updateEnabled());
		assertFalse(projection.deleteEnabled());
		assertEquals("de.gupta.clean.crud.generator.example.person.useCases.process.crud.update",
				projection.updatePackage());
		assertEquals("PersonUpdateSubprocessExecutor", projection.updateExecutorTypeName());
		assertEquals("personUpdateSubprocessDefinition", projection.updateDefinitionQualifier());
		assertEquals("subprocess.person.update", projection.updateProcessType());
	}
}