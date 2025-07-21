package de.gupta.clean.crud.generator.api;

import de.gupta.clean.crud.generator.api.api.cli.CleanCrudGeneratorCLI;
import de.gupta.clean.crud.generator.api.api.cli.GenerateCommand;
import de.gupta.clean.crud.generator.api.api.cli.ListTemplatesCommand;
import de.gupta.clean.crud.generator.code.generation.model.implementation.ModelImplementationModuleConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.OrchestrationModuleConfiguration;
import de.gupta.clean.crud.generator.code.generation.template.implementation.TemplateImplementationModuleConfiguration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import picocli.CommandLine;

import java.util.Arrays;

@SpringBootApplication
@Import(
		{
				ModelImplementationModuleConfiguration.class,
				TemplateImplementationModuleConfiguration.class,
				OrchestrationModuleConfiguration.class,
		}
)
class SpringBootMasterApplication
{
	public static void main(String[] args)
	{
		setSpringProfileFromArgs(args);

		System.exit(SpringApplication.exit(SpringApplication.run(SpringBootMasterApplication.class, args)));
	}

	private static void setSpringProfileFromArgs(String[] args)
	{
		Arrays.stream(args)
			  .filter(argument -> argument.startsWith("-s") || argument.startsWith("--spring-profile"))
			  .map(argument -> argument.split("=")[1])
			  .forEach(p -> System.setProperty("spring.profiles.active", p));
	}

	@Bean
	public ApplicationRunner runner(ApplicationContext context)
	{
		return arguments ->
		{
			var rootCommand = context.getBean(CleanCrudGeneratorCLI.class);
			CommandLine commandLine = new CommandLine(rootCommand);

			commandLine.addSubcommand("generate", context.getBean(GenerateCommand.class));
			commandLine.addSubcommand("list-templates", context.getBean(ListTemplatesCommand.class));

			commandLine.execute(arguments.getSourceArgs());
		};
	}
}