package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.infrastructure.specification.source;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.JavaCompiler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JavaGenerationSpecificationLoaderTest
{
	@Test
	void loadsValidSpecificationSource(@TempDir final Path tempDir) throws IOException
	{
		Path sourceRoot = tempDir.resolve("src/main/java");
		Path baseModel = sourceRoot.resolve("example/workspace/domain/model/WorkspaceModel.java");
		Path spec = sourceRoot.resolve("example/workspace/domain/model/WorkspaceRelationships.java");
		Files.createDirectories(baseModel.getParent());
		Files.writeString(baseModel, """
				package example.workspace.domain.model;
				
				public interface WorkspaceModel<O>
				{
				}
				""");
		Files.writeString(spec, """
				package example.workspace.domain.model;
				
				import de.gupta.clean.crud.template.domain.relationship.Relationship;
				import de.gupta.clean.crud.template.domain.relationship.Relationships;
				
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
				
				interface OrganisationModel
				{
				}
				""");

		var specification = new JavaGenerationSpecificationLoader().load(baseModel, spec);
		var relationship = List.copyOf(specification.relationships()).getFirst();

		assertEquals(1, specification.relationships().size());
		assertEquals("organisation", relationship.propertyName());
		assertEquals("WorkspaceModel", specification.baseModelClass().getSimpleName());
	}

	@Test
	void failsClearlyWhenNoSystemCompilerIsAvailable(@TempDir final Path tempDir) throws IOException
	{
		Path sourceRoot = tempDir.resolve("src/main/java");
		Path baseModel = sourceRoot.resolve("example/task/domain/model/TaskModel.java");
		Path spec = sourceRoot.resolve("example/task/domain/model/TaskRelationships.java");
		Files.createDirectories(baseModel.getParent());
		Files.writeString(baseModel, "package example.task.domain.model; public interface TaskModel<V> {}");
		Files.writeString(spec, "package example.task.domain.model; public final class TaskRelationships {}");
		var loader = new JavaGenerationSpecificationLoader(() -> null);

		var error = assertThrows(IllegalStateException.class, () -> loader.load(baseModel, spec));

		assertEquals("No system Java compiler available. A JDK is required to load generation specs.",
				error.getMessage());
	}

	@Test
	void failsClearlyWhenSpecificationSourceDoesNotCompile(@TempDir final Path tempDir) throws IOException
	{
		Path sourceRoot = tempDir.resolve("src/main/java");
		Path baseModel = sourceRoot.resolve("example/task/domain/model/TaskModel.java");
		Path spec = sourceRoot.resolve("example/task/domain/model/TaskRelationships.java");
		Files.createDirectories(baseModel.getParent());
		Files.writeString(baseModel, "package example.task.domain.model; public interface TaskModel<V> {}");
		Files.writeString(spec, """
				package example.task.domain.model;
				
				public final class TaskRelationships
				{
				    public void broken(
				}
				""");
		var loader = new JavaGenerationSpecificationLoader(JavaGenerationSpecificationLoaderTest::compiler);

		var error = assertThrows(IllegalArgumentException.class, () -> loader.load(baseModel, spec));

		assertEquals(
				"Failed to compile relationships declaration source `" + spec + "`",
				error.getMessage());
	}

	private static JavaCompiler compiler()
	{
		return javax.tools.ToolProvider.getSystemJavaCompiler();
	}
}
