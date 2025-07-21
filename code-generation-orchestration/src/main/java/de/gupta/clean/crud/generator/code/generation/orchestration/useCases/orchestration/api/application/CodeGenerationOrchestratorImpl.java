package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.facade.CodeGenerationOrchestratorServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class CodeGenerationOrchestratorImpl implements CodeGenerationOrchestrator
{
	private final CodeGenerationOrchestratorServiceFacade service;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		return service.generateCode(configuration);
	}

	CodeGenerationOrchestratorImpl(final CodeGenerationOrchestratorServiceFacade service)
	{
		this.service = service;
	}
}