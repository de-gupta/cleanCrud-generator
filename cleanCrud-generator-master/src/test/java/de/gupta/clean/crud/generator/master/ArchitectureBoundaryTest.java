package de.gupta.clean.crud.generator.master;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureBoundaryTest
{
	private final com.tngtech.archunit.core.domain.JavaClasses productionClasses =
			new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			                       .importPackages("de.gupta.clean.crud.generator");

	@Test
	void templateImplementationInternalsShouldNotLeakOutsideTemplateModule()
	{
		noClasses().that()
		           .resideOutsideOfPackages(
						   "..code.generation.template.implementation..",
						   "..master..")
		           .should()
		           .dependOnClassesThat()
		           .resideInAPackage("..code.generation.template.implementation..")
		           .check(productionClasses);
	}

	@Test
	void cliShouldNotDependOnImplementationPackages()
	{
		noClasses().that()
		           .resideInAPackage("..api.api.cli..")
		           .should()
		           .dependOnClassesThat()
		           .resideInAnyPackage(
						   "..code.generation.model.implementation..",
						   "..code.generation.template.implementation..",
						   "..code.generation.writing.implementation..")
		           .check(productionClasses);
	}

	@Test
	void orchestrationShouldNotDependOnImplementationPackages()
	{
		noClasses().that()
		           .resideInAnyPackage(
						   "..code.generation.orchestration.configuration..",
						   "..code.generation.orchestration.useCases..")
		           .should()
		           .dependOnClassesThat()
		           .resideInAnyPackage(
						   "..code.generation.model.implementation..",
						   "..code.generation.template.implementation..",
						   "..code.generation.writing.implementation..")
		           .check(productionClasses);
	}
}