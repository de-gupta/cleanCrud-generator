package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DirectCliConfigurationAssemblerTest
{
	private final DirectCliConfigurationAssembler assembler = new DirectCliConfigurationAssembler();
	private final CommandLine.Model.CommandSpec spec = new CommandLine(new TestCommand()).getCommandSpec();

	@Test
	void assemblesDirectCliOptionsIntoConfiguration()
	{
		var configuration = assembler.assemble(new DirectCliGenerationOptions(
				null,
				"src/main/java/example/TaskModel.java",
				null,
				null,
				null,
				List.of("U=String"),
				List.of("U=Long"),
				List.of("U=Integer"),
				Set.of("COMMON"),
				Set.of("BaseModel"),
				Set.of("adapter"),
				Set.of("SECURITY"),
				Set.of("PatchPolicy"),
				Set.of("internal"),
				GeneratedArtifactOwnership.USER,
				null,
				null,
				null,
				List.of("API_ADAPTERS=USER"),
				List.of("DomainModel=GENERATED"),
				List.of("adapter=USER"),
				Boolean.TRUE,
				List.of("COMMON=true"),
				List.of("BaseModel=false"),
				List.of("adapter=true"),
				List.of("src/main/java/example/BaseModel.java=false"),
				true
		), spec);

		assertEquals("src/main/java/example/TaskModel.java", configuration.inputs().baseModelSourceCodeFilePath());
		assertEquals("String", configuration.genericTypes().domain().get("U"));
		assertEquals("Long", configuration.genericTypes().persistence().get("U"));
		assertEquals("Integer", configuration.genericTypes().api().get("U"));
		assertTrue(configuration.generation().groups().contains("COMMON"));
		assertTrue(configuration.generation().templates().contains("BaseModel"));
		assertTrue(configuration.generation().tags().contains("adapter"));
		assertEquals(GeneratedArtifactOwnership.USER, configuration.ownership().baseModel());
		assertEquals(GeneratedArtifactOwnership.USER, configuration.ownership().groups().get("API_ADAPTERS"));
		assertTrue(configuration.overwrite().defaultOverwrite());
		assertEquals(Boolean.FALSE, configuration.overwrite().templates().get("BaseModel"));
		assertTrue(configuration.historized());
	}

	@Test
	void rejectsInvalidAssignments()
	{
		var options = new DirectCliGenerationOptions(
				null,
				"src/main/java/example/TaskModel.java",
				null,
				null,
				null,
				List.of("broken"),
				List.of(),
				List.of(),
				Set.of(),
				Set.of(),
				Set.of(),
				Set.of(),
				Set.of(),
				Set.of(),
				null,
				null,
				null,
				null,
				List.of(),
				List.of(),
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(),
				false
		);

		assertThrows(CommandLine.ParameterException.class, () -> assembler.assemble(options, spec));
	}

	@Test
	void rejectsMissingBaseModelWhenNoConfigurationFileIsProvided()
	{
		var options = new DirectCliGenerationOptions(
				null,
				null,
				null,
				null,
				null,
				List.of(),
				List.of(),
				List.of(),
				Set.of(),
				Set.of(),
				Set.of(),
				Set.of(),
				Set.of(),
				Set.of(),
				null,
				null,
				null,
				null,
				List.of(),
				List.of(),
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(),
				false
		);

		assertThrows(CommandLine.ParameterException.class, () -> assembler.assemble(options, spec));
	}

	@CommandLine.Command(name = "generate")
	private static final class TestCommand
	{
	}
}