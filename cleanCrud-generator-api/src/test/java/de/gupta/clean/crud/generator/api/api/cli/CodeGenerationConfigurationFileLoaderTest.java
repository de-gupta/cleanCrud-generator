package de.gupta.clean.crud.generator.api.api.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CodeGenerationConfigurationFileLoaderTest
{
	private final CodeGenerationConfigurationFileLoader loader = new CodeGenerationConfigurationFileLoader();

	@Test
	void loadsExtensionFlagsFromPropertiesConfiguration(@TempDir final Path tempDir) throws IOException
	{
		Path config = tempDir.resolve("generator.properties");
		Files.writeString(config, """
				inputs.baseModelSourceCodeFilePath=TaskModel.java
				postCommitHooks.save=true
				postCommitHooks.update=false
				postCommitHooks.delete=true
				subprocesses.save=false
				subprocesses.update=true
				subprocesses.delete=false
				""");

		var configuration = loader.load(config);

		assertTrue(configuration.postCommitHooks().save());
		assertFalse(configuration.postCommitHooks().update());
		assertTrue(configuration.postCommitHooks().delete());
		assertFalse(configuration.subprocesses().save());
		assertTrue(configuration.subprocesses().update());
		assertFalse(configuration.subprocesses().delete());
	}

	@Test
	void loadsExtensionFlagsFromJsonConfiguration(@TempDir final Path tempDir) throws IOException
	{
		Path config = tempDir.resolve("generator.json");
		Files.writeString(config, """
				{
				  "inputs": {
				    "baseModelSourceCodeFilePath": "TaskModel.java"
				  },
				  "postCommitHooks": {
				    "save": false,
				    "update": true,
				    "delete": false
				  },
				  "subprocesses": {
				    "save": true,
				    "update": false,
				    "delete": true
				  }
				}
				""");

		var configuration = loader.load(config);

		assertFalse(configuration.postCommitHooks().save());
		assertTrue(configuration.postCommitHooks().update());
		assertFalse(configuration.postCommitHooks().delete());
		assertTrue(configuration.subprocesses().save());
		assertFalse(configuration.subprocesses().update());
		assertTrue(configuration.subprocesses().delete());
	}
}
