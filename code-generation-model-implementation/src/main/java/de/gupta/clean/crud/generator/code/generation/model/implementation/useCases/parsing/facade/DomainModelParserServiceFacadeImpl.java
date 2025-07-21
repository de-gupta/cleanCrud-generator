package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.facade;

import de.gupta.clean.crud.generator.code.generation.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.application.service.DomainModelParserService;
import org.springframework.stereotype.Component;

@Component
final class DomainModelParserServiceFacadeImpl implements DomainModelParserServiceFacade
{
	private final DomainModelParserService service;

	@Override
	public Model parseDomainModel(final String domainModelSourceCode)
	{
		return service.parseDomainModel(domainModelSourceCode);
	}

	DomainModelParserServiceFacadeImpl(final DomainModelParserService service)
	{
		this.service = service;
	}
}