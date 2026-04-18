package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration.TemplateMetadataRegistry;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Comparator;
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
	@CommandLine.Spec
	private CommandLine.Model.CommandSpec commandSpec;

	@Override
	public Integer call()
	{
		TemplateMetadataRegistry.getAllTemplateMetadata()
		                        .keySet()
		                        .stream()
		                        .sorted(Comparator.naturalOrder())
		                        .forEach(commandSpec.commandLine().getOut()::println);
		return 0;
	}
}
