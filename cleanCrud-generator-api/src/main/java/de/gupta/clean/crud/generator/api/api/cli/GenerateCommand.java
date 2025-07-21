package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.api.facade.CodeGenerationServiceFacade;
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

	@Override
	public Integer call()
	{
		return service.generateCode();
	}

	GenerateCommand(final CodeGenerationServiceFacade service)
	{
		this.service = service;
	}
}