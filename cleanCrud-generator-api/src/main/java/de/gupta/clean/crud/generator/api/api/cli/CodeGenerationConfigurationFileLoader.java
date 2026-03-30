package de.gupta.clean.crud.generator.api.api.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
final class CodeGenerationConfigurationFileLoader
{
	private static final Pattern ENVIRONMENT_VARIABLE_PATTERN = Pattern.compile("\\$\\{([A-Za-z_][A-Za-z0-9_]*)}");

	CodeGenerationConfiguration load(final Path configurationFilePath)
	{
		try
		{
			var configuration = loadRawConfiguration(configurationFilePath);
			return normalize(configuration, configurationFilePath);
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException(
					"Failed to read configuration file `" + configurationFilePath + "`: " + e.getMessage(), e);
		}
	}

	private ObjectMapper objectMapperFor(final Path configurationFilePath)
	{
		String fileName = configurationFilePath.getFileName().toString().toLowerCase();
		return fileName.endsWith(".yaml") || fileName.endsWith(".yml")
				? new ObjectMapper(new YAMLFactory())
				: new ObjectMapper();
	}

	private CodeGenerationConfiguration loadRawConfiguration(final Path configurationFilePath) throws IOException
	{
		String fileName = configurationFilePath.getFileName().toString().toLowerCase();
		if (fileName.endsWith(".properties"))
		{
			return loadPropertiesConfiguration(configurationFilePath);
		}
		return objectMapperFor(configurationFilePath).readValue(configurationFilePath.toFile(),
				CodeGenerationConfiguration.class);
	}

	private CodeGenerationConfiguration loadPropertiesConfiguration(final Path configurationFilePath) throws IOException
	{
		Properties properties = new Properties();
		try (InputStream inputStream = java.nio.file.Files.newInputStream(configurationFilePath))
		{
			properties.load(inputStream);
		}

		return new CodeGenerationConfiguration(
				new GenerationInputs(
						properties.getProperty("inputs.baseModelSourceCodeFilePath"),
						properties.getProperty("inputs.domainModelSourceCodeFilePath"),
						properties.getProperty("inputs.persistenceModelSourceCodeFilePath"),
						properties.getProperty("inputs.apiModelSourceCodeFilePath")
				),
				new LayerConcreteTypes(
						extractPrefixedMap(properties, "genericTypes.domain."),
						extractPrefixedMap(properties, "genericTypes.persistence."),
						extractPrefixedMap(properties, "genericTypes.api.")
				),
				new GenerationSelection(
						extractCsvSet(properties, "generation.groups"),
						extractCsvSet(properties, "generation.templates"),
						extractCsvSet(properties, "generation.tags"),
						extractCsvSet(properties, "generation.excludeGroups"),
						extractCsvSet(properties, "generation.excludeTemplates"),
						extractCsvSet(properties, "generation.excludeTags")
				),
				new OwnershipConfiguration(
						parseOwnership(properties.getProperty("ownership.baseModel")),
						parseOwnership(properties.getProperty("ownership.domainModel")),
						parseOwnership(properties.getProperty("ownership.persistenceModel")),
						parseOwnership(properties.getProperty("ownership.apiModel")),
						extractOwnershipMap(properties, "ownership.groups."),
						extractOwnershipMap(properties, "ownership.templates."),
						extractOwnershipMap(properties, "ownership.tags.")
				),
				new OverwriteConfiguration(
						Boolean.parseBoolean(properties.getProperty("overwrite.defaultOverwrite", "false")),
						extractBooleanMap(properties, "overwrite.groups."),
						extractBooleanMap(properties, "overwrite.templates."),
						extractBooleanMap(properties, "overwrite.tags."),
						extractBooleanMap(properties, "overwrite.files.")
				),
				Boolean.parseBoolean(properties.getProperty("historized", "false"))
		);
	}

	private CodeGenerationConfiguration normalize(
			final CodeGenerationConfiguration configuration,
			final Path configurationFilePath)
	{
		Path configDirectory = configurationFilePath.toAbsolutePath().getParent();
		return new CodeGenerationConfiguration(
				normalizeInputs(configuration.inputs(), configDirectory),
				configuration.genericTypes(),
				configuration.generation(),
				configuration.ownership(),
				normalizeOverwrite(configuration.overwrite(), configDirectory),
				configuration.historized());
	}

	private GenerationInputs normalizeInputs(final GenerationInputs inputs, final Path configDirectory)
	{
		var normalized = inputs == null ? GenerationInputs.empty() : inputs.normalized();
		return new GenerationInputs(
				normalizePath(normalized.baseModelSourceCodeFilePath(), configDirectory),
				normalizePath(normalized.domainModelSourceCodeFilePath(), configDirectory),
				normalizePath(normalized.persistenceModelSourceCodeFilePath(), configDirectory),
				normalizePath(normalized.apiModelSourceCodeFilePath(), configDirectory)
		);
	}

	private OverwriteConfiguration normalizeOverwrite(
			final OverwriteConfiguration overwrite,
			final Path configDirectory)
	{
		var normalized = overwrite == null ? OverwriteConfiguration.defaults() : overwrite.normalized();
		return new OverwriteConfiguration(
				normalized.defaultOverwrite(),
				normalized.groups(),
				normalized.templates(),
				normalized.tags(),
				normalizeOverwriteFiles(normalized.files(), configDirectory)
		);
	}

	private Map<String, Boolean> normalizeOverwriteFiles(final Map<String, Boolean> files, final Path configDirectory)
	{
		if (files == null || files.isEmpty())
		{
			return Map.of();
		}
		return files.entrySet()
		            .stream()
		            .collect(java.util.stream.Collectors.toUnmodifiableMap(
							entry -> Path.of(expandEnvironmentVariables(entry.getKey())).isAbsolute()
									? Path.of(expandEnvironmentVariables(entry.getKey())).normalize().toString()
									: configDirectory.resolve(expandEnvironmentVariables(entry.getKey())).normalize()
						                             .toString(),
							Map.Entry::getValue,
							(left, right) -> right
					));
	}

	private String normalizePath(final String pathValue, final Path configDirectory)
	{
		if (pathValue == null || pathValue.isBlank())
		{
			return null;
		}
		Path path = Path.of(expandEnvironmentVariables(pathValue));
		if (!path.isAbsolute())
		{
			path = configDirectory.resolve(path).normalize();
		}
		return path.toString();
	}

	private String expandEnvironmentVariables(final String value)
	{
		Matcher matcher = ENVIRONMENT_VARIABLE_PATTERN.matcher(value);
		StringBuilder expanded = new StringBuilder();
		while (matcher.find())
		{
			String variableName = matcher.group(1);
			String environmentValue = System.getenv(variableName);
			if (environmentValue == null || environmentValue.isBlank())
			{
				throw new IllegalArgumentException(
						"Required environment variable `" + variableName + "` is not set for generator configuration value `" + value + "`");
			}
			matcher.appendReplacement(expanded, Matcher.quoteReplacement(environmentValue));
		}
		matcher.appendTail(expanded);
		return expanded.toString();
	}

	private Map<String, String> extractPrefixedMap(final Properties properties, final String prefix)
	{
		return properties.stringPropertyNames()
		                 .stream()
		                 .filter(name -> name.startsWith(prefix))
		                 .collect(Collectors.toMap(
								 name -> name.substring(prefix.length()),
								 properties::getProperty,
								 (left, right) -> right,
								 LinkedHashMap::new
						 ));
	}

	private Map<String, Boolean> extractBooleanMap(final Properties properties, final String prefix)
	{
		return properties.stringPropertyNames()
		                 .stream()
		                 .filter(name -> name.startsWith(prefix))
		                 .collect(Collectors.toMap(
								 name -> name.substring(prefix.length()),
								 name -> Boolean.parseBoolean(properties.getProperty(name)),
								 (left, right) -> right,
								 LinkedHashMap::new
						 ));
	}

	private Map<String, GeneratedArtifactOwnership> extractOwnershipMap(final Properties properties,
	                                                                    final String prefix)
	{
		return properties.stringPropertyNames()
		                 .stream()
		                 .filter(name -> name.startsWith(prefix))
		                 .collect(Collectors.toMap(
								 name -> name.substring(prefix.length()),
								 name -> GeneratedArtifactOwnership.valueOf(
										 properties.getProperty(name).trim().toUpperCase()),
								 (left, right) -> right,
								 LinkedHashMap::new
						 ));
	}

	private Set<String> extractCsvSet(final Properties properties, final String key)
	{
		String value = properties.getProperty(key);
		if (value == null || value.isBlank())
		{
			return Set.of();
		}
		return Arrays.stream(value.split(","))
		             .map(String::trim)
		             .filter(token -> !token.isBlank())
		             .collect(Collectors.toUnmodifiableSet());
	}

	private GeneratedArtifactOwnership parseOwnership(final String value)
	{
		if (value == null || value.isBlank())
		{
			return null;
		}
		return GeneratedArtifactOwnership.valueOf(value.trim().toUpperCase());
	}
}