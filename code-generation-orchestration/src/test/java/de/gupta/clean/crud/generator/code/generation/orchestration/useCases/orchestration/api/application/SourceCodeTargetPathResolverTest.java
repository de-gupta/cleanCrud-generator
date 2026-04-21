package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCode;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceCodeTargetPathResolverTest
{
	private final SourceCodeTargetPathResolver resolver = new SourceCodeTargetPathResolver();

	@Test
	void resolvesTargetPathFromPackageDeclaration()
	{
		var file = SourceCodeFile.with("TaskDomainModel.java", SourceCode.with("""
				package example.task.domain.model;
				
				public class TaskDomainModel {}
				"""));

		assertEquals(Path.of("src/main/java/example/task/domain/model/TaskDomainModel.java"),
				resolver.resolve(Path.of("src/main/java"), file));
	}

	@Test
	void fallsBackToContentRootWhenPackageIsMissing()
	{
		var file = SourceCodeFile.with("TaskDomainModel.java", SourceCode.with("public class TaskDomainModel {}"));

		assertEquals(Path.of("src/main/java/TaskDomainModel.java"),
				resolver.resolve(Path.of("src/main/java"), file));
	}
}
