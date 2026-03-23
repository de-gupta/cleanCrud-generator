package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.CodeGenerationOrchestrator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(
		name = "generate",
		description = "Generate Clean Architecture CRUD code from a model file",
		mixinStandardHelpOptions = true,
		footer = "%nExample: cleanCrud-generator generate path/to/DomainModel.java --historized"
)
public final class GenerateCommand implements Callable<Integer>
{
	private final CodeGenerationOrchestrator orchestrator;

	@CommandLine.Parameters(index = "0", description = "Model file path", arity = "0..1")
	private String modelFilePath;

	@CommandLine.Option(names = "--historized", description = "Generate historization-aware code where supported")
	private boolean historized;

	@Override
	public Integer call()
	{
		var configuration = CodeGenerationConfiguration.of(modelFilePath, historized);
		return orchestrator.generateCode(configuration);
	}

	GenerateCommand(final CodeGenerationOrchestrator orchestrator)
	{
		this.orchestrator = orchestrator;
	}
}