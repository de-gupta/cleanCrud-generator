package de.gupta.clean.crud.generator.api.facade;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;

public interface CodeGenerationServiceFacade
{
	int generateCode(final CodeGenerationConfiguration configuration);
}