package de.gupta.clean.crud.generator.master;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootMasterApplication.class)
class GeneratedAppFixtureCompileTest
{
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void generatesPersonModuleIntoCopiedFixtureAndCompiles(@TempDir final Path tempDir)
			throws Exception
	{
		var harness = new GeneratedAppFixtureHarness(applicationContext);
		var project = harness.prepareGeneratedApp(tempDir);

		assertTrue(Files.exists(project.configPath()));
		assertTrue(Files.exists(project.personModuleConfiguration()));
		assertTrue(Files.exists(
				project.personRoot().resolve("useCases/crud/configuration/PersonCrudDefinitionConfiguration.java")));
		assertTrue(Files.exists(
				project.personRoot().resolve("useCases/crud/configuration/PersonCrudRelationshipConfiguration.java")));
		assertTrue(Files.exists(project.sourceRoot().resolve("note/NoteModuleConfiguration.java")));
		assertTrue(Files.exists(project.sourceRoot().resolve("version/VersionModuleConfiguration.java")));

		var result = harness.executeMaven(project.projectRoot(), "-q", "-Dmaven.test.skip=true", "compile");
		assertEquals(0, result.exitCode(), () -> "Generated app fixture did not compile:\n" + result.output());
	}
}
