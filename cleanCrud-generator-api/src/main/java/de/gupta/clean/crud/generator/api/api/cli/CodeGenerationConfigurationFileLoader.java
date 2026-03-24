package de.gupta.clean.crud.generator.api.api.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
final class CodeGenerationConfigurationFileLoader
{
	CodeGenerationConfiguration load(final Path configurationFilePath)
	{
		try
		{
			CodeGenerationConfiguration configuration = objectMapperFor(configurationFilePath)
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
		Path modelPath = Path.of(configuration.domainModelSourceCodeFilePath());
		if (!modelPath.isAbsolute())
		{
			modelPath = configDirectory.resolve(modelPath).normalize();
		}
		return new CodeGenerationConfiguration(
				modelPath.toString(),
				configuration.domainConcreteTypes(),
				configuration.persistenceConcreteTypes(),
				configuration.apiConcreteTypes(),
				configuration.templateGroups(),
				configuration.forceOverwrite(),
				configuration.historized());
	}
}