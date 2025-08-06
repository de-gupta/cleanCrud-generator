package de.gupta.clean.crud.generator.api.service;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;

public interface CodeGenerationService
{
	int generateCode(final CodeGenerationConfiguration configuration);
}