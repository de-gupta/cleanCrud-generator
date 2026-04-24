package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.template.generation.specification.AggregateGenerationSpec;
import de.gupta.clean.crud.template.generation.specification.CodeGenerationSpecification;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
final class JavaGenerationSpecificationLoader
{
	private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([\\w.]+)\\s*;");
	private static final Pattern TYPE_PATTERN =
			Pattern.compile("(?:public\\s+)?(?:final\\s+)?(?:class|record)\\s+(\\w+)");

	AggregateGenerationSpec load(final Path baseModelSourceFilePath, final Path generationSpecSourceFilePath)
	{
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
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
			try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null,
					StandardCharsets.UTF_8))
			{
				var compilationUnits = fileManager.getJavaFileObjectsFromFiles(List.of(
						baseModelSourceFilePath.toFile(),
						generationSpecSourceFilePath.toFile()));
				List<String> options = List.of(
						"-classpath", System.getProperty("java.class.path"),
						"-sourcepath", sourceRoot.toString(),
						"-d", outputDirectory.toString());
				boolean success = Boolean.TRUE.equals(compiler.getTask(null, fileManager, null, options, null,
						compilationUnits).call());
				if (!success)
				{
					throw new IllegalArgumentException(
							"Failed to compile generation specification source `" + generationSpecSourceFilePath + "`");
				}
			}
			try (URLClassLoader classLoader = new URLClassLoader(new URL[]{outputDirectory.toUri().toURL()},
					Thread.currentThread().getContextClassLoader()))
			{
				Class<?> specificationClass = classLoader.loadClass(className);
				var constructor = specificationClass.getDeclaredConstructor();
				constructor.setAccessible(true);
				Object instance = constructor.newInstance();
				if (!(instance instanceof CodeGenerationSpecification specification))
				{
					throw new IllegalArgumentException(
							"Generation specification `" + className + "` must implement `" + CodeGenerationSpecification.class.getName() + "`");
				}
				return specification.specification();
			}
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException(
					"Failed to load generation specification from `" + generationSpecSourceFilePath + "`: " + e.getMessage(),
					e);
		}
		catch (ReflectiveOperationException e)
		{
			throw new IllegalArgumentException(
					"Failed to instantiate generation specification from `" + generationSpecSourceFilePath + "`: " + e.getMessage(),
					e);
		}
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
}