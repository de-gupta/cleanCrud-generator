package de.gupta.clean.crud.generator.master;

import de.gupta.clean.crud.generator.api.api.cli.CleanCrudGeneratorCLI;
import de.gupta.clean.crud.generator.api.api.cli.GenerateCommand;
import de.gupta.clean.crud.generator.api.api.cli.ListTemplatesCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootMasterApplication.class)
class GeneratorCliIntegrationTest
{
	private static final String PERSON_MODEL_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.person.domain.model;
			
			import java.util.Optional;
			
			public interface PersonModel
			{
				Optional<String> title();
				String firstName();
				Optional<String> lastName();
			}
			""";

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void generateSubcommandAcceptsConfigurationFileAndWritesIntoSampleStyleTree(@TempDir final Path tempDir)
			throws IOException
	{
		Path repoRoot = tempDir.resolve("cleanCrud-sampleImplementation-copy");
		Path contentRoot = repoRoot.resolve("src/main/java");
		Path modelPath =
				contentRoot.resolve("de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java");
		Path configPath = repoRoot.resolve(".run/person-generator-config.json");
		Files.createDirectories(modelPath.getParent());
		Files.createDirectories(configPath.getParent());
		Files.writeString(modelPath, PERSON_MODEL_SOURCE);
		Files.writeString(configPath, configJson());

		int exitCode = commandLine().execute("generate", "--config", configPath.toString());
		assertEquals(0, exitCode);

		try (Stream<Path> stream = Files.walk(contentRoot))
		{
			var generatedFiles = stream.filter(path -> path.toString().endsWith(".java"))
			                           .sorted(Comparator.naturalOrder())
			                           .toList();

			assertTrue(generatedFiles.size() > 20, "CLI generation should create a non-trivial tree");
			assertTrue(generatedFiles.stream().allMatch(path -> path.startsWith(contentRoot)),
					"Generated files should stay inside the copied sample repo source tree");
			assertTrue(generatedFiles.stream().anyMatch(path -> path.toString().contains("useCases\\crud\\fetch")),
					"Expected generated CRUD use-case files in the copied sample tree");
		}
	}

	@Test
	void generateSubcommandAcceptsPropertiesConfigurationFile(@TempDir final Path tempDir)
			throws IOException
	{
		Path repoRoot = tempDir.resolve("cleanCrud-sampleImplementation-copy");
		Path contentRoot = repoRoot.resolve("src/main/java");
		Path modelPath =
				contentRoot.resolve("de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java");
		Path configPath = repoRoot.resolve(".run/person-generator-config.properties");
		Files.createDirectories(modelPath.getParent());
		Files.createDirectories(configPath.getParent());
		Files.writeString(modelPath, PERSON_MODEL_SOURCE);
		Files.writeString(configPath, propertiesConfig());

		int exitCode = commandLine().execute("generate", "--config", configPath.toString());
		assertEquals(0, exitCode);
		assertTrue(Files.exists(contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/person/domain/model/PersonDomainModel.java")));
		assertTrue(Files.exists(contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/person/useCases/crud/configuration/PersonCrudServicesConfiguration.java")));
		assertFalse(Files.exists(contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/person/useCases/crud/save/application/service/PersonSaveService.java")));
	}

	@Test
	void listTemplatesSubcommandExecutesSuccessfully()
	{
		var standardOut = new ByteArrayOutputStream();
		var standardErr = new ByteArrayOutputStream();
		var commandLine = commandLine();
		commandLine.setOut(new PrintWriter(standardOut, true));
		commandLine.setErr(new PrintWriter(standardErr, true));

		assertEquals(0, commandLine.execute("list-templates"));
		var templateNames = standardOut.toString().lines().filter(line -> !line.isBlank()).toList();
		assertEquals(templateNames.stream().sorted().toList(), templateNames);
		assertTrue(templateNames.contains("CrudPortsConfiguration"));
		assertTrue(templateNames.contains("CrudDefinitionConfiguration"));
		assertTrue(templateNames.contains("CrudServicesConfiguration"));
		assertTrue(templateNames.contains("CrudRelationshipConfiguration"));
		assertFalse(templateNames.contains("SaveService"));
		assertFalse(templateNames.contains("FetchService"));
		assertFalse(templateNames.contains("UpdateService"));
		assertFalse(templateNames.contains("DeleteService"));
	}

	@Test
	void generateSubcommandAcceptsDirectOptions(@TempDir final Path tempDir)
			throws IOException
	{
		Path repoRoot = tempDir.resolve("cleanCrud-sampleImplementation-copy");
		Path contentRoot = repoRoot.resolve("src/main/java");
		Path modelPath =
				contentRoot.resolve("de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java");
		Files.createDirectories(modelPath.getParent());
		Files.writeString(modelPath, PERSON_MODEL_SOURCE);

		int exitCode = commandLine().execute(
				"generate",
				"--base-model", modelPath.toString(),
				"--group", "COMMON",
				"--group", "CONFIGURATION",
				"--group", "DOMAIN_MODELS",
				"--group", "DOMAIN_SUPPORT",
				"--group", "SECURITY",
				"--group", "USE_CASE_FETCH",
				"--group", "USE_CASE_SAVE",
				"--group", "USE_CASE_UPDATE",
				"--group", "USE_CASE_DELETE",
				"--group", "PERSISTENCE_ADAPTERS",
				"--group", "PERSISTENCE_MODELS",
				"--overwrite-default"
		);
		assertEquals(0, exitCode);
		assertTrue(Files.exists(contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/person/domain/model/PersonDomainModel.java")));
		assertTrue(Files.exists(contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/person/useCases/crud/configuration/PersonCrudDefinitionConfiguration.java")));
		assertFalse(Files.exists(contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/person/useCases/crud/save/application/service/PersonSaveService.java")));
	}

	@Test
	void generateSubcommandDoesNotLeakStateBetweenExecutions(@TempDir final Path tempDir)
			throws IOException
	{
		Path repoRoot = tempDir.resolve("cleanCrud-sampleImplementation-copy");
		Path contentRoot = repoRoot.resolve("src/main/java");
		Path modelPath =
				contentRoot.resolve("de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java");
		Path configPath = repoRoot.resolve(".run/person-generator-config.json");
		Files.createDirectories(modelPath.getParent());
		Files.createDirectories(configPath.getParent());
		Files.writeString(modelPath, PERSON_MODEL_SOURCE);
		Files.writeString(configPath, configJson());

		assertEquals(0, commandLine().execute(
				"generate",
				"--base-model", modelPath.toString(),
				"--group", "COMMON",
				"--overwrite-default"));
		assertEquals(0, commandLine().execute("generate", "--config", configPath.toString()));
	}

	private CommandLine commandLine()
	{
		var rootCommand = applicationContext.getBean(CleanCrudGeneratorCLI.class);
		CommandLine commandLine = new CommandLine(rootCommand);
		commandLine.addSubcommand("generate", applicationContext.getBean(GenerateCommand.class));
		commandLine.addSubcommand("list-templates", applicationContext.getBean(ListTemplatesCommand.class));
		return commandLine;
	}

	private String configJson()
	{
		return """
						{
						  "inputs": {
						    "baseModelSourceCodeFilePath": "../src/main/java/de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java"
						  },
						  "genericTypes": {
						    "domain": {},
						    "persistence": {},
						    "api": {}
						  },
						  "generation": {},
						  "ownership": {},
						  "overwrite": {
						    "defaultOverwrite": true
						  },
						  "historized": true
						}
				""";
	}

	private String propertiesConfig()
	{
		return """
				inputs.baseModelSourceCodeFilePath=../src/main/java/de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java
				overwrite.defaultOverwrite=true
				historized=true
				""";
	}
}