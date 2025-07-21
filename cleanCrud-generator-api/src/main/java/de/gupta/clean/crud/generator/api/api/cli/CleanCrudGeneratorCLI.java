package de.gupta.clean.crud.generator.api.api.cli;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(
		name = "cleanCrud-generator",
		mixinStandardHelpOptions = true,
		version = "0.1.4",
		description = "Generates Clean Architecture CRUD code from a model file",
		usageHelpWidth = 100,
		headerHeading = "Usage:%n%n",
		synopsisHeading = "%n",
		descriptionHeading = "%nDescription:%n%n",
		parameterListHeading = "%nParameters:%n",
		optionListHeading = "%nOptions:%n",
		footer = "%nExample: cleanCrud-generator generate path/to/DomainModel.java"
)
public final class CleanCrudGeneratorCLI implements Callable<Integer>
{
	@Override
	public Integer call()
	{
		System.out.println("Clean Architecture CRUD code generator CLI. Must use of the subcommands");
		return 1;
	}
}