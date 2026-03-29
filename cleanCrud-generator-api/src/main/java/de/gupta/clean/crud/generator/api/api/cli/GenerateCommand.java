package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.CodeGenerationOrchestrator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Callable;

@Component
@CommandLine.Command(
		name = "generate",
		description = "Generate Clean Architecture CRUD code from a model file or configuration file",
		mixinStandardHelpOptions = true
)
public final class GenerateCommand implements Callable<Integer>
{
	private final CodeGenerationOrchestrator orchestrator;
	private final CodeGenerationConfigurationFileLoader configurationFileLoader;

	@CommandLine.Spec
	private CommandLine.Model.CommandSpec spec;

	@CommandLine.Parameters(index = "0", description = "Base model file path", arity = "0..1")
	private String positionalBaseModelPath;

	@CommandLine.Option(names = {"-c",
			"--config"}, description = "Path to a JSON or YAML generation configuration file")
	private Path configurationFilePath;

	@CommandLine.Option(names = "--base-model", description = "Path to the base model source file")
	private String baseModelPath;

	@CommandLine.Option(names = "--domain-model", description = "Path to an existing domain model source file")
	private String domainModelPath;

	@CommandLine.Option(names = "--persistence-model", description = "Path to an existing persistence model source file")
	private String persistenceModelPath;

	@CommandLine.Option(names = "--api-model", description = "Path to an existing API model source file")
	private String apiModelPath;

	@CommandLine.Option(names = "--domain-type", description = "Generic substitution for the domain layer, e.g. U=String", split = ",")
	private List<String> domainTypes = new ArrayList<>();

	@CommandLine.Option(names = "--persistence-type", description = "Generic substitution for the persistence layer, e.g. V=UUID", split = ",")
	private List<String> persistenceTypes = new ArrayList<>();

	@CommandLine.Option(names = "--api-type", description = "Generic substitution for the API layer, e.g. V=Long", split = ",")
	private List<String> apiTypes = new ArrayList<>();

	@CommandLine.Option(names = "--group", description = "Generation group to include", split = ",")
	private Set<String> includeGroups = new LinkedHashSet<>();

	@CommandLine.Option(names = "--template", description = "Template name to include", split = ",")
	private Set<String> includeTemplates = new LinkedHashSet<>();

	@CommandLine.Option(names = "--tag", description = "Template tag to include", split = ",")
	private Set<String> includeTags = new LinkedHashSet<>();

	@CommandLine.Option(names = "--exclude-group", description = "Generation group to exclude", split = ",")
	private Set<String> excludeGroups = new LinkedHashSet<>();

	@CommandLine.Option(names = "--exclude-template", description = "Template name to exclude", split = ",")
	private Set<String> excludeTemplates = new LinkedHashSet<>();

	@CommandLine.Option(names = "--exclude-tag", description = "Template tag to exclude", split = ",")
	private Set<String> excludeTags = new LinkedHashSet<>();

	@CommandLine.Option(names = "--own-base-model", description = "Ownership of the base model: ${COMPLETION-CANDIDATES}")
	private GeneratedArtifactOwnership baseModelOwnership;

	@CommandLine.Option(names = "--own-domain-model", description = "Ownership of the domain model: ${COMPLETION-CANDIDATES}")
	private GeneratedArtifactOwnership domainModelOwnership;

	@CommandLine.Option(names = "--own-persistence-model", description = "Ownership of the persistence model: ${COMPLETION-CANDIDATES}")
	private GeneratedArtifactOwnership persistenceModelOwnership;

	@CommandLine.Option(names = "--own-api-model", description = "Ownership of the API model: ${COMPLETION-CANDIDATES}")
	private GeneratedArtifactOwnership apiModelOwnership;

	@CommandLine.Option(names = "--overwrite-default", description = "Default overwrite behavior for generated files")
	private Boolean overwriteDefault;

	@CommandLine.Option(names = "--overwrite-group", description = "Overwrite rule for a group, e.g. API_CONTROLLERS=true", split = ",")
	private List<String> overwriteGroupRules = new ArrayList<>();

	@CommandLine.Option(names = "--overwrite-template", description = "Overwrite rule for a template, e.g. DomainModelImpl=true", split = ",")
	private List<String> overwriteTemplateRules = new ArrayList<>();

	@CommandLine.Option(names = "--overwrite-tag", description = "Overwrite rule for a tag, e.g. controller=true", split = ",")
	private List<String> overwriteTagRules = new ArrayList<>();

	@CommandLine.Option(names = "--overwrite-file", description = "Overwrite rule for a file path, e.g. src/main/java/.../Foo.java=false", split = ",")
	private List<String> overwriteFileRules = new ArrayList<>();

	@CommandLine.Option(names = "--historized", description = "Generate historization-aware code where supported")
	private boolean historized;

	@Override
	public Integer call()
	{
		try
		{
			return orchestrator.generateCode(configuration());
		}
		finally
		{
			resetState();
		}
	}

	private CodeGenerationConfiguration configuration()
	{
		if (configurationFilePath != null)
		{
			if (hasDirectOptions())
			{
				throw new CommandLine.ParameterException(spec.commandLine(),
						"`--config` cannot be combined with direct generation options");
			}
			return configurationFileLoader.load(configurationFilePath);
		}

		var resolvedBaseModel = firstNonBlank(baseModelPath, positionalBaseModelPath);
		if (resolvedBaseModel == null)
		{
			throw new CommandLine.ParameterException(spec.commandLine(),
					"Please provide either `--config <file>` or a base model via positional path / `--base-model`");
		}

		return new CodeGenerationConfiguration(
				new GenerationInputs(resolvedBaseModel, domainModelPath, persistenceModelPath, apiModelPath),
				new LayerConcreteTypes(parseAssignments(domainTypes), parseAssignments(persistenceTypes),
						parseAssignments(apiTypes)),
				new GenerationSelection(includeGroups, includeTemplates, includeTags, excludeGroups, excludeTemplates,
						excludeTags),
				new OwnershipConfiguration(baseModelOwnership, domainModelOwnership, persistenceModelOwnership,
						apiModelOwnership),
				new OverwriteConfiguration(
						Boolean.TRUE.equals(overwriteDefault),
						parseBooleanAssignments(overwriteGroupRules),
						parseBooleanAssignments(overwriteTemplateRules),
						parseBooleanAssignments(overwriteTagRules),
						parseBooleanAssignments(overwriteFileRules)
				),
				historized
		);
	}

	private boolean hasDirectOptions()
	{
		return firstNonBlank(positionalBaseModelPath, baseModelPath, domainModelPath, persistenceModelPath,
				apiModelPath) != null
				|| historized
				|| !domainTypes.isEmpty()
				|| !persistenceTypes.isEmpty()
				|| !apiTypes.isEmpty()
				|| !includeGroups.isEmpty()
				|| !includeTemplates.isEmpty()
				|| !includeTags.isEmpty()
				|| !excludeGroups.isEmpty()
				|| !excludeTemplates.isEmpty()
				|| !excludeTags.isEmpty()
				|| baseModelOwnership != null
				|| domainModelOwnership != null
				|| persistenceModelOwnership != null
				|| apiModelOwnership != null
				|| overwriteDefault != null
				|| !overwriteGroupRules.isEmpty()
				|| !overwriteTemplateRules.isEmpty()
				|| !overwriteTagRules.isEmpty()
				|| !overwriteFileRules.isEmpty();
	}

	private String firstNonBlank(final String... values)
	{
		return Arrays.stream(values)
		             .filter(Objects::nonNull)
		             .map(String::trim)
		             .filter(value -> !value.isBlank())
		             .findFirst()
		             .orElse(null);
	}

	private Map<String, String> parseAssignments(final List<String> assignments)
	{
		if (assignments == null || assignments.isEmpty())
		{
			return Map.of();
		}
		var result = new LinkedHashMap<String, String>();
		assignments.stream()
		           .filter(Objects::nonNull)
		           .map(String::trim)
		           .filter(value -> !value.isBlank())
		           .forEach(assignment ->
				   {
					   var split = assignment.split("=", 2);
					   if (split.length != 2 || split[0].isBlank() || split[1].isBlank())
					   {
						   throw new CommandLine.ParameterException(spec.commandLine(),
								   "Invalid assignment `" + assignment + "`. Expected KEY=VALUE.");
					   }
					   result.put(split[0].trim(), split[1].trim());
				   });
		return Map.copyOf(result);
	}

	private Map<String, Boolean> parseBooleanAssignments(final List<String> assignments)
	{
		if (assignments == null || assignments.isEmpty())
		{
			return Map.of();
		}
		var result = new LinkedHashMap<String, Boolean>();
		assignments.stream()
		           .filter(Objects::nonNull)
		           .map(String::trim)
		           .filter(value -> !value.isBlank())
		           .forEach(assignment ->
				   {
					   var split = assignment.split("=", 2);
					   if (split.length != 2 || split[0].isBlank() || split[1].isBlank())
					   {
						   throw new CommandLine.ParameterException(spec.commandLine(),
								   "Invalid overwrite rule `" + assignment + "`. Expected KEY=true|false.");
					   }
					   result.put(split[0].trim(), Boolean.parseBoolean(split[1].trim()));
				   });
		return Map.copyOf(result);
	}

	private void resetState()
	{
		positionalBaseModelPath = null;
		configurationFilePath = null;
		baseModelPath = null;
		domainModelPath = null;
		persistenceModelPath = null;
		apiModelPath = null;
		domainTypes = new ArrayList<>();
		persistenceTypes = new ArrayList<>();
		apiTypes = new ArrayList<>();
		includeGroups = new LinkedHashSet<>();
		includeTemplates = new LinkedHashSet<>();
		includeTags = new LinkedHashSet<>();
		excludeGroups = new LinkedHashSet<>();
		excludeTemplates = new LinkedHashSet<>();
		excludeTags = new LinkedHashSet<>();
		baseModelOwnership = null;
		domainModelOwnership = null;
		persistenceModelOwnership = null;
		apiModelOwnership = null;
		overwriteDefault = null;
		overwriteGroupRules = new ArrayList<>();
		overwriteTemplateRules = new ArrayList<>();
		overwriteTagRules = new ArrayList<>();
		overwriteFileRules = new ArrayList<>();
		historized = false;
	}

	GenerateCommand(
			final CodeGenerationOrchestrator orchestrator,
			final CodeGenerationConfigurationFileLoader configurationFileLoader)
	{
		this.orchestrator = orchestrator;
		this.configurationFileLoader = configurationFileLoader;
	}
}