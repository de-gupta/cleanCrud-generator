package de.gupta.clean.crud.generator.api.service;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.CodeGenerationOrchestrator;
import org.springframework.stereotype.Service;

@Service
final class CodeGenerationServiceImpl implements CodeGenerationService
{
	private final CodeGenerationOrchestrator orchestrator;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		return orchestrator.generateCode(configuration);
	}

	CodeGenerationServiceImpl(final CodeGenerationOrchestrator orchestrator)
	{
		this.orchestrator = orchestrator;
	}
}