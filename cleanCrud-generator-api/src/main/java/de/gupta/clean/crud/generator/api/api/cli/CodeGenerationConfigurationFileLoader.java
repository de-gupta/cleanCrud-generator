package de.gupta.clean.crud.generator.api.api.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GenerationInputs;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.OverwriteConfiguration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
final class CodeGenerationConfigurationFileLoader
{
	private static final Pattern ENVIRONMENT_VARIABLE_PATTERN = Pattern.compile("\\$\\{([A-Za-z_][A-Za-z0-9_]*)}");

	CodeGenerationConfiguration load(final Path configurationFilePath)
	{
		try
		{
			var configuration = objectMapperFor(configurationFilePath)
					.readValue(configurationFilePath.toFile(), CodeGenerationConfiguration.class);
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
}