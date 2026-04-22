package de.gupta.clean.crud.generator.master;

import de.gupta.clean.crud.generator.api.APIModuleConfiguration;
import de.gupta.clean.crud.generator.code.generation.model.implementation.ModelImplementationModuleConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.OrchestrationModuleConfiguration;
import de.gupta.clean.crud.generator.code.generation.template.implementation.TemplateImplementationModuleConfiguration;
import de.gupta.clean.crud.generator.code.generation.writing.implementation.WritingImplementationModuleConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeneratedAppFixtureBootIT
{
	@Test
	void generatesPersonModuleIntoCopiedFixtureAndBoots(@TempDir final Path tempDir)
			throws Exception
	{
		try (var applicationContext = new AnnotationConfigApplicationContext(
				APIModuleConfiguration.class,
				ModelImplementationModuleConfiguration.class,
				TemplateImplementationModuleConfiguration.class,
				WritingImplementationModuleConfiguration.class,
				OrchestrationModuleConfiguration.class))
		{
			var harness = new GeneratedAppFixtureHarness(applicationContext);
			var project = harness.prepareGeneratedApp(tempDir);

			var result = harness.executeMaven(project.projectRoot(), "-q", "test");
			assertEquals(0, result.exitCode(),
					() -> "Generated app fixture did not boot successfully:\n" + result.output());
		}
	}
}