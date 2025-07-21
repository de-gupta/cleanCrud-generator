package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.facade;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.application.service.CodeGenerationOrchestratorService;
import org.springframework.stereotype.Component;

@Component
final class CodeGenerationOrchestratorServiceFacadeImpl implements CodeGenerationOrchestratorServiceFacade
{
	private final CodeGenerationOrchestratorService service;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		return service.generateCode(configuration);
	}

	CodeGenerationOrchestratorServiceFacadeImpl(final CodeGenerationOrchestratorService service)
	{
		this.service = service;
	}
}