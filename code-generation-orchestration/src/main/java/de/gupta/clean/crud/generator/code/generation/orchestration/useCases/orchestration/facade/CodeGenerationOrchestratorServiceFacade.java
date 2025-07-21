package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.facade;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;

public interface CodeGenerationOrchestratorServiceFacade
{
	int generateCode(final CodeGenerationConfiguration configuration);
}