package de.gupta.clean.crud.implementation.examples;

import de.gupta.clean.crud.implementation.examples.note.NoteModuleConfiguration;
import de.gupta.clean.crud.implementation.examples.person.PersonModuleConfiguration;
import de.gupta.clean.crud.implementation.examples.version.VersionModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(
		{
				NoteModuleConfiguration.class,
				VersionModuleConfiguration.class,
				PersonModuleConfiguration.class,
		}
)
public class ExampleApplication
{
	public static void main(final String[] args)
	{
		SpringApplication.run(ExampleApplication.class, args);
	}
}
