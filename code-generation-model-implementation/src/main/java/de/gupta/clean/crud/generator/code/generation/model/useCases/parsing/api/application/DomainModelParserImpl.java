package de.gupta.clean.crud.generator.code.generation.model.useCases.parsing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.useCases.parsing.facade.DomainModelParserServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class DomainModelParserImpl implements DomainModelParser
{
	private final DomainModelParserServiceFacade service;

	@Override
	public Model parseDomainModel(final String domainModelSourceCode)
	{
		return service.parseDomainModel(domainModelSourceCode);
	}

	DomainModelParserImpl(final DomainModelParserServiceFacade service)
	{
		this.service = service;
	}
}