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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootMasterApplication.class)
class GeneratorCliIntegrationTest
{
	private static final String PERSON_MODEL_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.person.domain.model;
			
			import java.util.Optional;
			
			public interface PersonModel<U, V>
			{
				U user();
			
				Optional<V> something();
			
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
	void listTemplatesSubcommandExecutesSuccessfully()
	{
		assertEquals(0, commandLine().execute("list-templates"));
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
						  "domainModelSourceCodeFilePath": "../src/main/java/de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java",
						  "domainConcreteTypes": {
						    "U": "String",
						    "V": "Integer"
						  },
						  "persistenceConcreteTypes": {
						    "U": "String",
						    "V": "Integer"
						  },
						  "apiConcreteTypes": {
						    "U": "String",
						    "V": "Integer"
						  },
						  "templateGroups": [],
						  "generateCommonFiles": false,
				"forceOverwrite": true,
						  "historized": true
						}
				""";
	}
}