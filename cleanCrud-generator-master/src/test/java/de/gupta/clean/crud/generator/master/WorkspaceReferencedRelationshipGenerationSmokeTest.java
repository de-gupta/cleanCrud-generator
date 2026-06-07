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
class WorkspaceReferencedRelationshipGenerationSmokeTest
{
	private static final String ORGANISATION_MODEL_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.organisation.domain.model;
			
			public interface OrganisationModel
			{
				String name();
			}
			""";
	private static final String WORKSPACE_MODEL_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.workspace.domain.model;
			
			import java.util.Optional;
			
			public interface WorkspaceModel<O>
			{
				String name();
				Optional<O> organisation();
			}
			""";
	private static final String WORKSPACE_RELATIONSHIPS_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.workspace.domain.model;
			
			import de.gupta.clean.crud.template.domain.relationship.Relationship;
			import de.gupta.clean.crud.template.domain.relationship.Relationships;
			import de.gupta.clean.crud.implementation.examples.organisation.domain.model.OrganisationModel;
			
			import java.util.List;
			
			public final class WorkspaceRelationships implements Relationships
			{
				@Override
				public Class<?> baseModelClass()
				{
					return WorkspaceModel.class;
				}
			
				@Override
				public List<Relationship> relationships()
				{
					return List.of(Relationship.referenced("organisation", OrganisationModel.class)
					                           .satelliteApiIdType(Long.class)
					                           .satelliteDomainIdType(Long.class)
					                           .satellitePersistenceIdType(java.util.UUID.class)
					                           .build());
				}
			}
			""";
	private static final String CLEANCRUD_VERSION = System.getProperty("clean.crud.version", "0.8.3-SNAPSHOT");

	@Autowired
	private CodeGenerationOrchestrator orchestrator;

	@Test
	void generatesReferencedRelationshipModuleThatCompiles(@TempDir final Path tempDir)
			throws IOException, InterruptedException
	{
		Path contentRoot = tempDir.resolve("src/main/java");
		Path organisationModelPath = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/organisation/domain/model/OrganisationModel.java");
		Path workspaceModelPath = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/workspace/domain/model/WorkspaceModel.java");
		Path workspaceSpecPath = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/workspace/domain/model/WorkspaceRelationships.java");
		Files.createDirectories(organisationModelPath.getParent());
		Files.createDirectories(workspaceModelPath.getParent());
		Files.writeString(organisationModelPath, ORGANISATION_MODEL_SOURCE);
		Files.writeString(workspaceModelPath, WORKSPACE_MODEL_SOURCE);
		Files.writeString(workspaceSpecPath, WORKSPACE_RELATIONSHIPS_SOURCE);

		assertEquals(0, orchestrator.generateCode(standaloneConfiguration(organisationModelPath)));
		assertEquals(0, orchestrator.generateCode(specDrivenConfiguration(workspaceModelPath, workspaceSpecPath)));

		Path workspaceRelationshipConfiguration = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/workspace/useCases/crud/configuration/WorkspaceCrudRelationshipConfiguration.java");
		Path workspaceApiCreate = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/workspace/useCases/crud/common/dto/WorkspaceAPIModelCreate.java");
		Path workspaceApiUpdatePatch = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/workspace/useCases/crud/common/dto/WorkspaceAPIModelUpdatePatch.java");
		Path workspaceDomainModel = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/workspace/domain/model/WorkspaceDomainModel.java");
		Path workspacePersistenceModel = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/workspace/infrastructure/persistence/model/WorkspacePersistenceModel.java");

		assertTrue(Files.readString(workspaceRelationshipConfiguration).contains("fromRelationship("));
		assertTrue(Files.readString(workspaceRelationshipConfiguration)
		                .contains("new WorkspaceRelationships().relationship(\"organisation\")"));
		assertTrue(Files.readString(workspaceRelationshipConfiguration).contains(".current("));
		assertTrue(Files.readString(workspaceRelationshipConfiguration).contains(".replace("));
		assertTrue(Files.readString(workspaceApiCreate).contains("Optional<Long> organisation"));
		assertTrue(Files.readString(workspaceApiUpdatePatch).contains("Optional<Long> organisation"));
		assertTrue(Files.readString(workspaceApiUpdatePatch).contains("Collection<Long> removeOrganisationIds"));
		assertFalse(Files.readString(workspaceApiUpdatePatch).contains("SatelliteUpdatePatchItem"));
		assertTrue(Files.readString(workspaceDomainModel)
		                .contains("WorkspaceModel<IdentifiedModel<Long, OrganisationDomainModel>>"));
		assertTrue(Files.readString(workspacePersistenceModel)
		                .contains("void setOrganisation(final UUID organisation);"));
		Files.writeString(tempDir.resolve("pom.xml"), pomXml(CLEANCRUD_VERSION));
		assertEquals(0, compileGeneratedProject(tempDir));
	}

	private CodeGenerationConfiguration standaloneConfiguration(final Path modelPath)
	{
		return new CodeGenerationConfiguration(
				new GenerationInputs(modelPath.toString(), null, null, null, null),
				new LayerConcreteTypes(Map.of(), Map.of(), Map.of()),
				GenerationSelection.defaults(),
				List.of(),
				RootAggregateIdConfiguration.defaults(),
				OwnershipConfiguration.defaults(),
				new OverwriteConfiguration(true, Map.of(), Map.of(), Map.of(), Map.of()),
				true);
	}

	private CodeGenerationConfiguration specDrivenConfiguration(final Path modelPath, final Path specPath)
	{
		return new CodeGenerationConfiguration(
				new GenerationInputs(modelPath.toString(), null, null, null, specPath.toString()),
				new LayerConcreteTypes(Map.of(), Map.of(), Map.of()),
				GenerationSelection.defaults(),
				List.of(),
				RootAggregateIdConfiguration.defaults(),
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
				    <artifactId>workspace-referenced-relationship-generated-smoke</artifactId>
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
