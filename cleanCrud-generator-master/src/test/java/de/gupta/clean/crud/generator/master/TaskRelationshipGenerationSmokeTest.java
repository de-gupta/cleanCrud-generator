package de.gupta.clean.crud.generator.master;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.CodeGenerationOrchestrator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootMasterApplication.class)
class TaskRelationshipGenerationSmokeTest
{
	private static final String VERSION_MODEL_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.version.domain.model;
			
			public interface VersionModel
			{
				Long version();
			}
			""";
	private static final String NOTE_MODEL_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.note.domain.model;
			
			public interface NoteModel
			{
				String note();
			}
			""";
	private static final String TASK_MODEL_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.task.domain.model;
			
			import de.gupta.clean.crud.implementation.examples.note.useCases.crud.common.dto.NoteAPIModelResponse;
			import de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelResponse;
			
			import java.util.Collection;
			import java.util.Optional;
			
			public interface TaskModel
			{
				String title();
			
				Optional<String> description();
			
				Optional<VersionAPIModelResponse> version();
			
				Collection<NoteAPIModelResponse> notes();
			}
			""";
	private static final String CLEANCRUD_VERSION = System.getProperty("clean.crud.version", "0.7.3-SNAPSHOT");

	@Autowired
	private CodeGenerationOrchestrator orchestrator;

	@Test
	void generatesRelationshipAwareTaskModuleThatCompiles(@TempDir final Path tempDir)
			throws IOException, InterruptedException
	{
		Path contentRoot = tempDir.resolve("src/main/java");
		Path versionModelPath = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/version/domain/model/VersionModel.java");
		Path noteModelPath =
				contentRoot.resolve("de/gupta/clean/crud/implementation/examples/note/domain/model/NoteModel.java");
		Path taskModelPath =
				contentRoot.resolve("de/gupta/clean/crud/implementation/examples/task/domain/model/TaskModel.java");
		Files.createDirectories(versionModelPath.getParent());
		Files.createDirectories(noteModelPath.getParent());
		Files.createDirectories(taskModelPath.getParent());
		Files.writeString(versionModelPath, VERSION_MODEL_SOURCE);
		Files.writeString(noteModelPath, NOTE_MODEL_SOURCE);
		Files.writeString(taskModelPath, TASK_MODEL_SOURCE);

		assertEquals(0, orchestrator.generateCode(standaloneConfiguration(versionModelPath)));
		assertEquals(0, orchestrator.generateCode(standaloneConfiguration(noteModelPath)));
		assertEquals(0, orchestrator.generateCode(taskConfiguration(taskModelPath)));

		Path taskRelationshipConfiguration = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/task/useCases/crud/configuration/TaskCrudRelationshipConfiguration.java");
		Path taskCrudDefinitionConfiguration = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/task/useCases/crud/configuration/TaskCrudDefinitionConfiguration.java");
		Path taskApiUpdatePatch = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/task/useCases/crud/common/dto/TaskAPIModelUpdatePatch.java");
		Path noteApiUpdatePatch = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/note/useCases/crud/common/dto/NoteAPIModelUpdatePatch.java");
		Path versionApiUpdatePatch = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/version/useCases/crud/common/dto/VersionAPIModelUpdatePatch.java");

		assertTrue(Files.exists(taskRelationshipConfiguration));
		assertTrue(Files.readString(taskCrudDefinitionConfiguration)
		                .contains("relationshipDefinition(versionRelationshipDefinition)"));
		assertTrue(Files.readString(taskCrudDefinitionConfiguration)
		                .contains("relationshipDefinition(notesRelationshipDefinition)"));
		assertTrue(Files.readString(taskApiUpdatePatch).contains("record VersionItem("));
		assertTrue(Files.readString(taskApiUpdatePatch).contains("record NotesItem("));
		assertFalse(Files.readString(noteApiUpdatePatch).contains("Optional<Long> id"));
		assertFalse(Files.readString(versionApiUpdatePatch).contains("Optional<Long> id"));

		Files.writeString(tempDir.resolve("pom.xml"), pomXml(CLEANCRUD_VERSION));
		assertEquals(0, compileGeneratedProject(tempDir));
	}

	private CodeGenerationConfiguration standaloneConfiguration(final Path modelPath)
	{
		return new CodeGenerationConfiguration(
				new GenerationInputs(modelPath.toString(), null, null, null),
				new LayerConcreteTypes(Map.of(), Map.of(), Map.of()),
				GenerationSelection.defaults(),
				List.of(),
				OwnershipConfiguration.defaults(),
				new OverwriteConfiguration(true, Map.of(), Map.of(), Map.of(), Map.of()),
				true);
	}

	private CodeGenerationConfiguration taskConfiguration(final Path modelPath)
	{
		return new CodeGenerationConfiguration(
				new GenerationInputs(modelPath.toString(), null, null, null),
				new LayerConcreteTypes(Map.of(), Map.of(), Map.of()),
				GenerationSelection.defaults(),
				List.of(
						new RelationshipGenerationConfiguration(
								"version",
								"Version",
								RelationshipCardinality.ONE,
								RelationshipReconciliationStrategy.REPLACE,
								"Long",
								true,
								true,
								false,
								false,
								true,
								true,
								true),
						new RelationshipGenerationConfiguration(
								"notes",
								"Note",
								RelationshipCardinality.MANY,
								RelationshipReconciliationStrategy.MERGE_BY_ID,
								"Long",
								true,
								true,
								false,
								false,
								true,
								true,
								true)),
				OwnershipConfiguration.defaults(),
				new OverwriteConfiguration(true, Map.of(), Map.of(), Map.of(), Map.of()),
				true);
	}

	private int compileGeneratedProject(final Path projectRoot) throws IOException, InterruptedException
	{
		Process process = new ProcessBuilder("cmd.exe", "/c", "mvn", "-q", "-DskipTests", "compile")
				.directory(projectRoot.toFile())
				.redirectErrorStream(true)
				.start();
		String output = new String(process.getInputStream().readAllBytes());
		int exitCode = process.waitFor();
		assertEquals(0, exitCode, () -> "Generated project did not compile:\n" + output);
		return exitCode;
	}

	private String pomXml(final String cleanCrudVersion)
	{
		return """
				<project xmlns="http://maven.apache.org/POM/4.0.0"
				         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
				    <modelVersion>4.0.0</modelVersion>
				    <groupId>de.gupta.clean.crud.generator.smoke</groupId>
				    <artifactId>task-relationship-generated-smoke</artifactId>
				    <version>1.0.0-SNAPSHOT</version>
				
				    <properties>
				        <maven.compiler.release>25</maven.compiler.release>
				        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
				    </properties>
				
				    <dependencies>
				        <dependency>
				            <groupId>io.github.de-gupta</groupId>
				            <artifactId>cleanCrud</artifactId>
				            <version>%s</version>
				        </dependency>
				        <dependency>
				            <groupId>org.springdoc</groupId>
				            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
				            <version>2.8.9</version>
				        </dependency>
				    </dependencies>
				</project>
				""".formatted(cleanCrudVersion);
	}
}