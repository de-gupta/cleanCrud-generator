package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.CodeGenerationOrchestrator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@Component
@CommandLine.Command(
		name = "generate",
		description = "Generate Clean Architecture CRUD code from a model file or configuration file",
		mixinStandardHelpOptions = true
)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class GenerateCommand implements Callable<Integer>
{
	private final CodeGenerationOrchestrator orchestrator;
	private final CodeGenerationConfigurationFileLoader configurationFileLoader;
	private final DirectCliConfigurationAssembler directCliConfigurationAssembler;
	@CommandLine.Option(names = "--domain-type", description = "Generic substitution for the domain layer, e.g. U=String", split = ",")
	private final List<String> domainTypes = new ArrayList<>();
	@CommandLine.Option(names = "--persistence-type", description = "Generic substitution for the persistence layer, e.g. V=UUID", split = ",")
	private final List<String> persistenceTypes = new ArrayList<>();
	@CommandLine.Option(names = "--api-type", description = "Generic substitution for the API layer, e.g. V=Long", split = ",")
	private final List<String> apiTypes = new ArrayList<>();
	@CommandLine.Option(names = "--group", description = "Generation group to include", split = ",")
	private final Set<String> includeGroups = new LinkedHashSet<>();
	@CommandLine.Option(names = "--template", description = "Template name to include", split = ",")
	private final Set<String> includeTemplates = new LinkedHashSet<>();
	@CommandLine.Option(names = "--tag", description = "Template tag to include", split = ",")
	private final Set<String> includeTags = new LinkedHashSet<>();
	@CommandLine.Option(names = "--exclude-group", description = "Generation group to exclude", split = ",")
	private final Set<String> excludeGroups = new LinkedHashSet<>();
	@CommandLine.Option(names = "--exclude-template", description = "Template name to exclude", split = ",")
	private final Set<String> excludeTemplates = new LinkedHashSet<>();
	@CommandLine.Option(names = "--exclude-tag", description = "Template tag to exclude", split = ",")
	private final Set<String> excludeTags = new LinkedHashSet<>();
	@CommandLine.Option(names = "--own-group", description = "Ownership rule for a group, e.g. API_ADAPTERS=USER", split = ",")
	private final List<String> ownershipGroupRules = new ArrayList<>();
	@CommandLine.Option(names = "--own-template", description = "Ownership rule for a template, e.g. DomainPersistenceModelAdapter=USER", split = ",")
	private final List<String> ownershipTemplateRules = new ArrayList<>();
	@CommandLine.Option(names = "--own-tag", description = "Ownership rule for a tag, e.g. adapter=USER", split = ",")
	private final List<String> ownershipTagRules = new ArrayList<>();
	@CommandLine.Option(names = "--overwrite-group", description = "Overwrite rule for a group, e.g. API_CONTROLLERS=true", split = ",")
	private final List<String> overwriteGroupRules = new ArrayList<>();
	@CommandLine.Option(names = "--overwrite-template", description = "Overwrite rule for a template, e.g. DomainModelImpl=true", split = ",")
	private final List<String> overwriteTemplateRules = new ArrayList<>();
	@CommandLine.Option(names = "--overwrite-tag", description = "Overwrite rule for a tag, e.g. controller=true", split = ",")
	private final List<String> overwriteTagRules = new ArrayList<>();
	@CommandLine.Option(names = "--overwrite-file", description = "Overwrite rule for a file path, e.g. src/main/java/.../Foo.java=false", split = ",")
	private final List<String> overwriteFileRules = new ArrayList<>();
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
	@CommandLine.Option(names = "--generation-spec", description = "Path to the Java generation specification source file")
	private String generationSpecPath;
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
	@CommandLine.Option(names = "--historized", description = "Generate historization-aware code where supported")
	private boolean historized;

	@Override
	public Integer call()
	{
		return orchestrator.generateCode(configuration());
	}

	private CodeGenerationConfiguration configuration()
	{
		var directCliOptions = directCliOptions();
		if (configurationFilePath != null)
		{
			if (directCliOptions.hasDirectOptions())
			{
				throw new CommandLine.ParameterException(spec.commandLine(),
						"`--config` cannot be combined with direct generation options");
			}
			return configurationFileLoader.load(configurationFilePath);
		}
		return directCliConfigurationAssembler.assemble(directCliOptions, spec);
	}

	private DirectCliGenerationOptions directCliOptions()
	{
		return new DirectCliGenerationOptions(
				positionalBaseModelPath,
				baseModelPath,
				domainModelPath,
				persistenceModelPath,
				apiModelPath,
				generationSpecPath,
				List.copyOf(domainTypes),
				List.copyOf(persistenceTypes),
				List.copyOf(apiTypes),
				Set.copyOf(includeGroups),
				Set.copyOf(includeTemplates),
				Set.copyOf(includeTags),
				Set.copyOf(excludeGroups),
				Set.copyOf(excludeTemplates),
				Set.copyOf(excludeTags),
				baseModelOwnership,
				domainModelOwnership,
				persistenceModelOwnership,
				apiModelOwnership,
				List.copyOf(ownershipGroupRules),
				List.copyOf(ownershipTemplateRules),
				List.copyOf(ownershipTagRules),
				overwriteDefault,
				List.copyOf(overwriteGroupRules),
				List.copyOf(overwriteTemplateRules),
				List.copyOf(overwriteTagRules),
				List.copyOf(overwriteFileRules),
				historized);
	}

	GenerateCommand(
			final CodeGenerationOrchestrator orchestrator,
			final CodeGenerationConfigurationFileLoader configurationFileLoader,
			final DirectCliConfigurationAssembler directCliConfigurationAssembler)
	{
		this.orchestrator = orchestrator;
		this.configurationFileLoader = configurationFileLoader;
		this.directCliConfigurationAssembler = directCliConfigurationAssembler;
	}
}