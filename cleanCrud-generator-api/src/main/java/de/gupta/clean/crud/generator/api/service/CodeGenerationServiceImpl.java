package de.gupta.clean.crud.generator.api.service;

import de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application.CodeGenerationOrchestrator;
import org.springframework.stereotype.Service;

@Service
final class CodeGenerationServiceImpl implements CodeGenerationService
{
	private final CodeGenerationOrchestrator orchestrator;

	@Override
	public int generateCode()
	{
		// TODO
		// build options object here to pass instead of null
		return orchestrator.generateCode(null);
	}

	CodeGenerationServiceImpl(final CodeGenerationOrchestrator orchestrator)
	{
		this.orchestrator = orchestrator;
	}
}