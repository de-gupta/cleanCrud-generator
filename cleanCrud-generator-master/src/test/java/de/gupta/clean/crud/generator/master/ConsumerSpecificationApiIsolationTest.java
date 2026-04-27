package de.gupta.clean.crud.generator.master;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsumerSpecificationApiIsolationTest
{
	private static final Path SAMPLE_PERSON_SPEC = Path.of(
			"E:\\Projects\\Professional\\OpenSource\\java\\de-gupta\\crud\\cleanCrud-sampleImplementation\\src\\main\\java\\de\\gupta\\clean\\crud\\implementation\\examples\\person\\domain\\model\\PersonRelationships.java");
	private static final Path DUPLICATE_GENERATOR_SPEC_PACKAGE = Path.of(
			"E:\\Projects\\Professional\\OpenSource\\java\\de-gupta\\crud\\cleanCrud-generator\\cleanCrud-generator\\code-generation-orchestration\\src\\main\\java\\de\\gupta\\clean\\crud\\generator\\code\\generation\\orchestration\\configuration\\specification");

	@Test
	void sampleConsumerSpecDependsOnlyOnCleanCrudSpecificationApi() throws IOException
	{
		String source = Files.readString(SAMPLE_PERSON_SPEC);

		assertTrue(source.contains("de.gupta.clean.crud.template.domain.relationship"));
		assertFalse(source.contains(
				"de.gupta.clean.crud.generator.code.generation.orchestration.configuration.specification"));
	}

	@Test
	void generatorNoLongerShipsDuplicateConsumerSpecificationPackage()
			throws IOException
	{
		assertFalse(
				Files.exists(DUPLICATE_GENERATOR_SPEC_PACKAGE) &&
						Files.walk(DUPLICATE_GENERATOR_SPEC_PACKAGE)
						     .anyMatch(path -> path.toString().endsWith(".java")));
	}
}
