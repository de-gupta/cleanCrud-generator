package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.infrastructure.specification.source;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.PostCommitHookGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.RootAggregateIdConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.SubprocessGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.GenerationSpecificationDescriptor;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.GenerationSpecificationLoader;
import de.gupta.clean.crud.template.domain.relationship.Relationship;
import de.gupta.clean.crud.template.domain.relationship.Relationships;
import de.gupta.clean.crud.template.generation.specification.AggregateGenerationSpec;
import de.gupta.clean.crud.template.generation.specification.CodeGenerationSpecification;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class JavaGenerationSpecificationLoader implements GenerationSpecificationLoader
{
	private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([\\w.]+)\\s*;");
	private static final Pattern TYPE_PATTERN =
			Pattern.compile("(?:public\\s+)?(?:final\\s+)?(?:class|record)\\s+(\\w+)");
	private final Supplier<JavaCompiler> compilerSupplier;

	public JavaGenerationSpecificationLoader()
	{
		this(ToolProvider::getSystemJavaCompiler);
	}

	@Override
	public GenerationSpecificationDescriptor load(
			final Path baseModelSourceFilePath,
			final Path generationSpecSourceFilePath)
	{
		JavaCompiler compiler = compilerSupplier.get();
		if (compiler == null)
		{
			throw new IllegalStateException(
					"No system Java compiler available. A JDK is required to load generation specs.");
		}
		try
		{
			Path sourceRoot = resolveSourceRoot(baseModelSourceFilePath);
			String className = determineFullyQualifiedClassName(generationSpecSourceFilePath);
			Path outputDirectory = Files.createTempDirectory("cleancrud-generator-spec");
			try
			{
				compileSpecification(compiler, baseModelSourceFilePath, generationSpecSourceFilePath, sourceRoot,
						outputDirectory);
				return loadSpecification(className, outputDirectory, generationSpecSourceFilePath);
			}
			finally
			{
				deleteDirectoryQuietly(outputDirectory);
			}
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException(
					"Failed to load relationships declaration from `" + generationSpecSourceFilePath + "`: " + e.getMessage(),
					e);
		}
		catch (ReflectiveOperationException e)
		{
			throw new IllegalArgumentException(
					"Failed to instantiate relationships declaration from `" + generationSpecSourceFilePath + "`: " + e.getMessage(),
					e);
		}
	}

	private void compileSpecification(
			final JavaCompiler compiler,
			final Path baseModelSourceFilePath,
			final Path generationSpecSourceFilePath,
			final Path sourceRoot,
			final Path outputDirectory) throws IOException
	{
		try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8))
		{
			var compilationUnits = fileManager.getJavaFileObjectsFromFiles(List.of(
					baseModelSourceFilePath.toFile(),
					generationSpecSourceFilePath.toFile()));
			List<String> options = new ArrayList<>(List.of(
					"-classpath", resolveCompilationClasspath(),
					"-sourcepath", sourceRoot.toString(),
					"-d", outputDirectory.toString()));
			String modulePath = System.getProperty("jdk.module.path");
			if (modulePath != null && !modulePath.isBlank())
			{
				options.add("--module-path");
				options.add(modulePath);
				options.add("--add-modules");
				options.add("ALL-MODULE-PATH");
			}
			boolean success = Boolean.TRUE.equals(compiler.getTask(null, fileManager, null, options, null,
					compilationUnits).call());
			if (!success)
			{
				throw new IllegalArgumentException(
						"Failed to compile relationships declaration source `" + generationSpecSourceFilePath + "`");
			}
		}
	}

	private String resolveCompilationClasspath()
	{
		var entries = new LinkedHashSet<String>();
		String javaClassPath = System.getProperty("java.class.path");
		if (javaClassPath != null && !javaClassPath.isBlank())
		{
			for (String entry : javaClassPath.split(Pattern.quote(System.getProperty("path.separator"))))
			{
				if (!entry.isBlank())
				{
					entries.add(entry);
				}
			}
		}

		addCodeSource(entries, Relationships.class);
		addCodeSource(entries, Relationship.class);
		addInstalledCleanCrudArtifact(entries);

		return String.join(System.getProperty("path.separator"), entries);
	}

	private void addCodeSource(final LinkedHashSet<String> entries, final Class<?> type)
	{
		try
		{
			var codeSource = type.getProtectionDomain().getCodeSource();
			if (codeSource == null || codeSource.getLocation() == null)
			{
				return;
			}
			Path path = Path.of(codeSource.getLocation().toURI());
			if (Files.exists(path))
			{
				entries.add(path.toString());
			}
		}
		catch (URISyntaxException ignored)
		{
		}
	}

	private void addInstalledCleanCrudArtifact(final LinkedHashSet<String> entries)
	{
		String version = Relationships.class.getPackage().getImplementationVersion();
		if (version != null && !version.isBlank())
		{
			Path artifact = Path.of(
					System.getProperty("user.home"),
					".m2",
					"repository",
					"io",
					"github",
					"de-gupta",
					"cleanCrud",
					version,
					"cleanCrud-" + version + ".jar");
			if (Files.exists(artifact))
			{
				entries.add(artifact.toString());
				return;
			}
		}

		Path repositoryRoot = Path.of(
				System.getProperty("user.home"),
				".m2",
				"repository",
				"io",
				"github",
				"de-gupta",
				"cleanCrud");
		if (!Files.isDirectory(repositoryRoot))
		{
			return;
		}
		try (var versions = Files.list(repositoryRoot))
		{
			Path artifact = versions
					.filter(Files::isDirectory)
					.sorted(Comparator.reverseOrder())
					.map(path -> path.resolve("cleanCrud-" + path.getFileName() + ".jar"))
					.filter(Files::exists)
					.findFirst()
					.orElse(null);
			if (artifact != null)
			{
				entries.add(artifact.toString());
			}
		}
		catch (IOException ignored)
		{
		}
	}

	private GenerationSpecificationDescriptor loadSpecification(
			final String className,
			final Path outputDirectory,
			final Path generationSpecSourceFilePath) throws ReflectiveOperationException, IOException
	{
		try (URLClassLoader classLoader = new URLClassLoader(new URL[]{outputDirectory.toUri().toURL()},
				Thread.currentThread().getContextClassLoader()))
		{
			Class<?> specificationClass = classLoader.loadClass(className);
			var constructor = specificationClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			Object instance = constructor.newInstance();
			if (instance instanceof CodeGenerationSpecification specification)
			{
				return toDescriptor(specification.specification());
			}
			if (instance instanceof Relationships relationships)
			{
				return new GenerationSpecificationDescriptor(
						relationships.baseModelClass(),
						List.copyOf(relationships.relationships()),
						RootAggregateIdConfiguration.defaults(),
						PostCommitHookGenerationConfiguration.defaults(),
						SubprocessGenerationConfiguration.defaults());
			}
			throw new IllegalArgumentException(
					"Generation spec `" + className + "` must implement `" + Relationships.class.getName() +
							"` or `" + CodeGenerationSpecification.class.getName() + "`");
		}
		catch (ClassNotFoundException e)
		{
			throw new IllegalArgumentException(
					"Failed to load relationships declaration from `" + generationSpecSourceFilePath + "`: " + e.getMessage(),
					e);
		}
	}

	private GenerationSpecificationDescriptor toDescriptor(final AggregateGenerationSpec specification)
	{
		return new GenerationSpecificationDescriptor(
				specification.baseModelClass(),
				specification.relationships(),
				new RootAggregateIdConfiguration(
						typeName(specification.rootApiIdType()),
						typeName(specification.rootDomainIdType()),
						typeName(specification.rootPersistenceIdType())),
				new PostCommitHookGenerationConfiguration(
						specification.postCommitHooks().save(),
						specification.postCommitHooks().update(),
						specification.postCommitHooks().delete()),
				new SubprocessGenerationConfiguration(
						specification.subprocesses().save(),
						specification.subprocesses().update(),
						specification.subprocesses().delete()));
	}

	private String typeName(final Class<?> type)
	{
		return type == null ? null : type.getCanonicalName();
	}

	private Path resolveSourceRoot(final Path baseModelSourceFilePath) throws IOException
	{
		Path current = baseModelSourceFilePath.toAbsolutePath().normalize().getParent();
		while (current != null)
		{
			String normalized = current.toString().replace('\\', '/');
			if (normalized.endsWith("/src/main/java") || normalized.endsWith("/src/test/java"))
			{
				return current;
			}
			current = current.getParent();
		}
		throw new IllegalArgumentException(
				"Could not determine Java source root for base model `" + baseModelSourceFilePath + "`");
	}

	private String determineFullyQualifiedClassName(final Path sourceFilePath) throws IOException
	{
		String source = Files.readString(sourceFilePath, StandardCharsets.UTF_8);
		Matcher packageMatcher = PACKAGE_PATTERN.matcher(source);
		Matcher typeMatcher = TYPE_PATTERN.matcher(source);
		if (!typeMatcher.find())
		{
			throw new IllegalArgumentException(
					"Could not determine public type name from generation spec `" + sourceFilePath + "`");
		}
		String typeName = typeMatcher.group(1);
		return packageMatcher.find() ? packageMatcher.group(1) + "." + typeName : typeName;
	}

	private void deleteDirectoryQuietly(final Path directory)
	{
		if (directory == null || !Files.exists(directory))
		{
			return;
		}
		try (var walk = Files.walk(directory))
		{
			walk.sorted(Comparator.reverseOrder()).forEach(path ->
			{
				try
				{
					Files.deleteIfExists(path);
				}
				catch (IOException ignored)
				{
				}
			});
		}
		catch (IOException ignored)
		{
		}
	}

	JavaGenerationSpecificationLoader(final Supplier<JavaCompiler> compilerSupplier)
	{
		this.compilerSupplier = compilerSupplier;
	}
}