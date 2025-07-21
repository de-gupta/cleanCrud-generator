package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;

public interface CodeGenerationOrchestrator
{
	int generateCode(final CodeGenerationConfiguration configuration);
}