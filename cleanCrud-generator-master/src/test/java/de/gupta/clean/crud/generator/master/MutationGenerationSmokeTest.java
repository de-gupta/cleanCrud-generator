package de.gupta.clean.crud.generator.master;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.CodeGenerationOrchestrator;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootMasterApplication.class)
class MutationGenerationSmokeTest
{
	private static final String PERSON_MODEL_SOURCE = """
			package de.gupta.clean.crud.implementation.examples.person.domain.model;
			
			import java.util.Optional;
			
			public interface PersonModel
			{
				String firstName();
				Optional<String> lastName();
			}
			""";
	private static final String CLEANCRUD_VERSION = System.getProperty("clean.crud.version", "0.9.1-SNAPSHOT");

	@Autowired
	private CodeGenerationOrchestrator orchestrator;

	@Test
	void generatesMutationConfigurationThatCompilesWithoutHandlers(@TempDir final Path tempDir)
			throws IOException, InterruptedException
	{
		Path contentRoot = tempDir.resolve("src/main/java");
		Path modelPath =
				contentRoot.resolve("de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java");
		Files.createDirectories(modelPath.getParent());
		Files.writeString(modelPath, PERSON_MODEL_SOURCE);

		var configuration = new CodeGenerationConfiguration(
				new GenerationInputs(modelPath.toString(), null, null, null, null),
				new LayerConcreteTypes(Map.of(), Map.of(), Map.of()),
				new GenerationSelection(
						Set.of(
								TemplateGroup.COMMON.name(),
								TemplateGroup.CONFIGURATION.name(),
								TemplateGroup.DOMAIN_MODELS.name(),
								TemplateGroup.DOMAIN_SUPPORT.name(),
								TemplateGroup.API_DTOS.name(),
								TemplateGroup.API_ADAPTERS.name(),
								TemplateGroup.API_CONTROLLERS.name(),
								TemplateGroup.PERSISTENCE_MODELS.name(),
								TemplateGroup.PERSISTENCE_ADAPTERS.name(),
								TemplateGroup.PERSISTENCE_REPOSITORIES.name(),
								TemplateGroup.PERSISTENCE_HISTORY.name(),
								TemplateGroup.SECURITY.name(),
								TemplateGroup.USE_CASE_FETCH.name(),
								TemplateGroup.USE_CASE_SAVE.name(),
								TemplateGroup.USE_CASE_UPDATE.name(),
								TemplateGroup.USE_CASE_DELETE.name(),
								TemplateGroup.USE_CASE_MUTATION.name()),
						Set.of(),
						Set.of(),
						Set.of(),
						Set.of(),
						Set.of()),
				java.util.List.of(),
				RootAggregateIdConfiguration.defaults(),
				OwnershipConfiguration.defaults(),
				new OverwriteConfiguration(true, Map.of(), Map.of(), Map.of(), Map.of()),
				PostCommitHookGenerationConfiguration.defaults(),
				SubprocessGenerationConfiguration.defaults(),
				true);

		assertEquals(0, orchestrator.generateCode(configuration));

		Path mutationConfiguration = contentRoot.resolve(
				"de/gupta/clean/crud/implementation/examples/person/useCases/mutation/configuration/PersonMutationConfiguration.java");
		assertTrue(Files.exists(mutationConfiguration));
		assertTrue(Files.readString(mutationConfiguration).contains("MutationHandlerRegistry.of(handlers)"));

		Files.writeString(tempDir.resolve("pom.xml"), pomXml(CLEANCRUD_VERSION));
		assertEquals(0, compileGeneratedProject(tempDir));
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
				
				    <parent>
				        <groupId>org.springframework.boot</groupId>
				        <artifactId>spring-boot-starter-parent</artifactId>
				        <version>3.5.13</version>
				        <relativePath/>
				    </parent>
				
				    <groupId>de.gupta.clean.crud.generator.smoke</groupId>
				    <artifactId>person-mutation-generated-smoke</artifactId>
				    <version>1.0.0-SNAPSHOT</version>
				
				    <properties>
				        <java.version>25</java.version>
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
				        <dependency>
				            <groupId>org.springframework.boot</groupId>
				            <artifactId>spring-boot-starter-test</artifactId>
				            <scope>test</scope>
				        </dependency>
				        <dependency>
				            <groupId>com.h2database</groupId>
				            <artifactId>h2</artifactId>
				            <scope>test</scope>
				        </dependency>
				    </dependencies>
				</project>
				""".formatted(cleanCrudVersion);
	}
}