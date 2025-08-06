package de.gupta.clean.crud.generator.api.facade;

import de.gupta.clean.crud.generator.api.service.CodeGenerationService;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import org.springframework.stereotype.Component;

@Component
final class CodeGenerationServiceFacadeImpl implements CodeGenerationServiceFacade
{
	private final CodeGenerationService service;

	@Override
	public int generateCode(final CodeGenerationConfiguration configuration)
	{
		return service.generateCode(configuration);
	}

	CodeGenerationServiceFacadeImpl(final CodeGenerationService service)
	{
		this.service = service;
	}
}