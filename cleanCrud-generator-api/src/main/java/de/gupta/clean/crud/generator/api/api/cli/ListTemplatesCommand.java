package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.TemplateCatalog;
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
	private final TemplateCatalog templateCatalog;

	@CommandLine.Spec
	private CommandLine.Model.CommandSpec commandSpec;

	@Override
	public Integer call()
	{
		templateCatalog.allTemplates()
		                        .stream()
		                        .map(template -> template.templateName())
		                        .sorted(Comparator.naturalOrder())
		                        .forEach(commandSpec.commandLine().getOut()::println);
		return 0;
	}

	ListTemplatesCommand(final TemplateCatalog templateCatalog)
	{
		this.templateCatalog = templateCatalog;
	}
}
