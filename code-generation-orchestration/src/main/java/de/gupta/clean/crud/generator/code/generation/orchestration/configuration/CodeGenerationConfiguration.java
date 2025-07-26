package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record CodeGenerationConfiguration(
		String domainModelSourceCodeFilePath,
		boolean forceOverwrite
)
{
}