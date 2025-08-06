package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.api.facade.CodeGenerationServiceFacade;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(
		name = "generate",
		description = "Generate Clean Architecture CRUD code from a model file",
		mixinStandardHelpOptions = true,
		footer = "%nExample: cleanCrud-generator generate path/to/DomainModel.java"
)
public final class GenerateCommand implements Callable<Integer>
{
	private final CodeGenerationServiceFacade service;

	@CommandLine.Parameters(index = "0", description = "Model file path", arity = "0..1")
	private String modelFilePath;

	@Override
	public Integer call()
	{
		var configuration = CodeGenerationConfiguration.of(modelFilePath);
		return service.generateCode(configuration);
	}

	GenerateCommand(final CodeGenerationServiceFacade service)
	{
		this.service = service;
	}
}