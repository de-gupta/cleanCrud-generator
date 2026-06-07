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
						properties.getProperty("inputs.apiModelSourceCodeFilePath"),
						properties.getProperty("inputs.relationshipsSourceCodeFilePath")
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
				extractRelationshipConfigurations(properties),
				new RootAggregateIdConfiguration(
						properties.getProperty("rootAggregateIds.apiIdType"),
						properties.getProperty("rootAggregateIds.domainIdType"),
						properties.getProperty("rootAggregateIds.persistenceIdType")),
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
				configuration.relationships(),
				configuration.rootAggregateIds(),
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
				normalizePath(normalized.apiModelSourceCodeFilePath(), configDirectory),
				normalizePath(normalized.relationshipsSourceCodeFilePath(), configDirectory)
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

	private java.util.List<RelationshipGenerationConfiguration> extractRelationshipConfigurations(
			final Properties properties)
	{
		var relationshipIndexes = properties.stringPropertyNames()
		                                    .stream()
		                                    .filter(name -> name.startsWith("relationships."))
		                                    .map(name -> name.substring("relationships.".length()))
		                                    .map(name -> name.split("\\.", 2)[0])
		                                    .filter(index -> index.matches("\\d+"))
		                                    .map(Integer::parseInt)
		                                    .collect(java.util.stream.Collectors.toCollection(
													java.util.TreeSet::new));
		var relationships = new java.util.ArrayList<RelationshipGenerationConfiguration>();
		for (Integer index : relationshipIndexes)
		{
			String prefix = "relationships." + index + ".";
			relationships.add(new RelationshipGenerationConfiguration(
					properties.getProperty(prefix + "masterProperty"),
					properties.getProperty(prefix + "satelliteAggregate"),
					properties.getProperty(prefix + "satelliteBaseModelType"),
					parseEnum(properties.getProperty(prefix + "relationshipKind"), RelationshipKind.class),
					parseEnum(properties.getProperty(prefix + "cardinality"), RelationshipCardinality.class),
					parseEnum(properties.getProperty(prefix + "reconciliationStrategy"),
							RelationshipReconciliationStrategy.class),
					properties.getProperty(prefix + "satelliteApiIdType"),
					properties.getProperty(prefix + "satelliteDomainIdType"),
					properties.getProperty(prefix + "satellitePersistenceIdType"),
					parseBooleanObject(properties.getProperty(prefix + "cascadeCreate")),
					parseBooleanObject(properties.getProperty(prefix + "cascadeUpdate")),
					parseBooleanObject(properties.getProperty(prefix + "cascadeDelete")),
					parseBooleanObject(properties.getProperty(prefix + "orphanDelete")),
					parseBooleanObject(properties.getProperty(prefix + "hydrateOnFetch")),
					parseBooleanObject(properties.getProperty(prefix + "generateNestedCreate")),
					parseBooleanObject(properties.getProperty(prefix + "generateNestedUpdate"))
			));
		}
		return java.util.List.copyOf(relationships);
	}

	private <T extends Enum<T>> T parseEnum(final String value, final Class<T> enumClass)
	{
		if (value == null || value.isBlank())
		{
			return null;
		}
		return Enum.valueOf(enumClass, value.trim().toUpperCase(java.util.Locale.ROOT));
	}

	private Boolean parseBooleanObject(final String value)
	{
		return value == null || value.isBlank() ? null : Boolean.parseBoolean(value.trim());
	}
}
