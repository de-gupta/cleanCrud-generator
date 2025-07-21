package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.application.service;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;

public interface CodeGenerationOrchestratorService
{
	int generateCode(final CodeGenerationConfiguration configuration);
}