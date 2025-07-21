package de.gupta.clean.crud.generator.api.api.cli;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(
		name = "list-templates",
		description = "List all available templates",
		mixinStandardHelpOptions = true,
		footer = "%nExample: cleanCrud-generator list-templates"
)
public final class ListTemplatesCommand implements Callable<Integer>
{
	@Override
	public Integer call()
	{
		return 0;
	}
}