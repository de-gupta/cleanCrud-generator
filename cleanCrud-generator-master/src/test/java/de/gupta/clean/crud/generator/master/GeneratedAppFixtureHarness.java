package de.gupta.clean.crud.generator.master;

import de.gupta.clean.crud.generator.api.api.cli.CleanCrudGeneratorCLI;
import de.gupta.clean.crud.generator.api.api.cli.GenerateCommand;
import de.gupta.clean.crud.generator.api.api.cli.ListTemplatesCommand;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

final class GeneratedAppFixtureHarness
{
	private static final String FIXTURE_RESOURCE_ROOT = "generated-app-fixutre";
	private static final String EXAMPLES_PACKAGE_PATH = "de/gupta/clean/crud/implementation/examples";
	private static final String CLEANCRUD_VERSION_TOKEN = "__CLEANCRUD_VERSION__";
	private static final String DEFAULT_CLEANCRUD_VERSION = System.getProperty("clean.crud.version", "0.8.1");
	private static final List<String> STATIC_MODULES = List.of("note", "version");
	private static final String GENERATED_MODULE = "person";

	private final ApplicationContext applicationContext;

	GeneratedAppProject prepareGeneratedApp(final Path tempDir)
			throws IOException
	{
		Path projectRoot = tempDir.resolve("generated-app-fixture");
		Path fixtureRoot = fixtureResourceRoot();
		Path sourceRoot = projectRoot.resolve("src/main/java").resolve(EXAMPLES_PACKAGE_PATH);

		copyFixtureScaffold(fixtureRoot, projectRoot);
		Files.createDirectories(sourceRoot);

		for (String module : STATIC_MODULES)
		{
			copyDirectory(fixtureRoot.resolve(module), sourceRoot.resolve(module));
		}
		copyDirectory(fixtureRoot.resolve(GENERATED_MODULE), sourceRoot.resolve(GENERATED_MODULE));

		replaceToken(projectRoot.resolve("pom.xml"), CLEANCRUD_VERSION_TOKEN, DEFAULT_CLEANCRUD_VERSION);

		Path configPath = projectRoot.resolve("generate-person-fixture.json");
		int exitCode = commandLine().execute("generate", "--config", configPath.toString());
		if (exitCode != 0)
		{
			throw new AssertionError("Generator CLI failed for generated-app fixture with exit code " + exitCode);
		}

		Path generatedPersonRoot = sourceRoot.resolve(GENERATED_MODULE);
		return new GeneratedAppProject(
				projectRoot,
				configPath,
				sourceRoot,
				generatedPersonRoot,
				generatedPersonRoot.resolve("PersonModuleConfiguration.java"));
	}

	MavenExecutionResult executeMaven(final Path projectRoot, final String... arguments)
			throws IOException, InterruptedException
	{
		List<String> command = new ArrayList<>(List.of("cmd.exe", "/c", "mvn"));
		command.addAll(List.of(arguments));

		Process process = new ProcessBuilder(command)
				.directory(projectRoot.toFile())
				.redirectErrorStream(true)
				.start();

		String output = new String(process.getInputStream().readAllBytes());
		int exitCode = process.waitFor();
		return new MavenExecutionResult(exitCode, output);
	}

	private CommandLine commandLine()
	{
		var rootCommand = applicationContext.getBean(CleanCrudGeneratorCLI.class);
		CommandLine commandLine = new CommandLine(rootCommand);
		commandLine.addSubcommand("generate", applicationContext.getBean(GenerateCommand.class));
		commandLine.addSubcommand("list-templates", applicationContext.getBean(ListTemplatesCommand.class));
		return commandLine;
	}

	private Path fixtureResourceRoot()
			throws IOException
	{
		try
		{
			var resource = GeneratedAppFixtureHarness.class.getClassLoader().getResource(FIXTURE_RESOURCE_ROOT);
			if (resource == null)
			{
				throw new IOException("Missing generated-app fixture resources at " + FIXTURE_RESOURCE_ROOT);
			}
			return Path.of(resource.toURI());
		}
		catch (URISyntaxException exception)
		{
			throw new IOException("Unable to resolve generated-app fixture root", exception);
		}
	}

	private void copyFixtureScaffold(final Path fixtureRoot, final Path projectRoot)
			throws IOException
	{
		try (Stream<Path> stream = Files.walk(fixtureRoot))
		{
			for (Path source : stream.toList())
			{
				Path relative = fixtureRoot.relativize(source);
				if (relative.toString().isEmpty() || belongsToModuleTree(relative))
				{
					continue;
				}

				Path target = projectRoot.resolve(relative.toString());
				if (Files.isDirectory(source))
				{
					Files.createDirectories(target);
				}
				else
				{
					Files.createDirectories(Objects.requireNonNull(target.getParent()));
					Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}
	}

	private boolean belongsToModuleTree(final Path relative)
	{
		if (relative.getNameCount() == 0)
		{
			return false;
		}

		String topLevelName = relative.getName(0).toString();
		return STATIC_MODULES.contains(topLevelName) || GENERATED_MODULE.equals(topLevelName);
	}

	private void copyDirectory(final Path sourceRoot, final Path targetRoot)
			throws IOException
	{
		try (Stream<Path> stream = Files.walk(sourceRoot))
		{
			for (Path source : stream.toList())
			{
				Path relative = sourceRoot.relativize(source);
				Path target = targetRoot.resolve(relative.toString());
				if (Files.isDirectory(source))
				{
					Files.createDirectories(target);
				}
				else
				{
					Files.createDirectories(Objects.requireNonNull(target.getParent()));
					Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}
	}

	private void replaceToken(final Path file, final String token, final String value)
			throws IOException
	{
		String content = Files.readString(file);
		Files.writeString(file, content.replace(token, value));
	}

	GeneratedAppFixtureHarness(final ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}

	record GeneratedAppProject(
			Path projectRoot,
			Path configPath,
			Path sourceRoot,
			Path personRoot,
			Path personModuleConfiguration)
	{
	}

	record MavenExecutionResult(int exitCode, String output)
	{
	}
}