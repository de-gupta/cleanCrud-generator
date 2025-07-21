package de.gupta.clean.crud.generator.api.facade;

import de.gupta.clean.crud.generator.api.service.CodeGenerationService;
import org.springframework.stereotype.Component;

@Component
final class CodeGenerationServiceFacadeImpl implements CodeGenerationServiceFacade
{
	private final CodeGenerationService service;

	@Override
	public int generateCode()
	{
		return service.generateCode();
	}

	CodeGenerationServiceFacadeImpl(final CodeGenerationService service)
	{
		this.service = service;
	}
}