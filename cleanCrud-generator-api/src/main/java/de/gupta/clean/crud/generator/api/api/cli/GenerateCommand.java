package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.CodeGenerationOrchestrator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@Component
@CommandLine.Command(
		name = "generate",
		description = "Generate Clean Architecture CRUD code from a model file or configuration file",
		mixinStandardHelpOptions = true,
		footer = "%nExamples:%n  cleanCrud-generator generate path/to/DomainModel.java --historized%n  cleanCrud-generator generate --config path/to/generator-config.json"
)
public final class GenerateCommand implements Callable<Integer>
{
	private final CodeGenerationOrchestrator orchestrator;
	private final CodeGenerationConfigurationFileLoader configurationFileLoader;

	@CommandLine.Spec
	private CommandLine.Model.CommandSpec spec;

	@CommandLine.Parameters(index = "0", description = "Model file path", arity = "0..1")
	private String modelFilePath;

	@CommandLine.Option(names = {"-c",
			"--config"}, description = "Path to a JSON or YAML generation configuration file")
	private Path configurationFilePath;

	@CommandLine.Option(names = "--historized", description = "Generate historization-aware code where supported")
	private boolean historized;

	@Override
	public Integer call()
	{
		return orchestrator.generateCode(configuration());
	}

	private CodeGenerationConfiguration configuration()
	{
		if (configurationFilePath != null)
		{
			if (modelFilePath != null || historized)
			{
				throw new CommandLine.ParameterException(spec.commandLine(),
						"`--config` cannot be combined with the model file path or `--historized`");
			}
			return configurationFileLoader.load(configurationFilePath);
		}

		if (modelFilePath == null || modelFilePath.isBlank())
		{
			throw new CommandLine.ParameterException(spec.commandLine(),
					"Please provide either a model file path or `--config <file>`");
		}
		return CodeGenerationConfiguration.of(modelFilePath, historized);
	}

	GenerateCommand(
			final CodeGenerationOrchestrator orchestrator,
			final CodeGenerationConfigurationFileLoader configurationFileLoader)
	{
		this.orchestrator = orchestrator;
		this.configurationFileLoader = configurationFileLoader;
	}
}