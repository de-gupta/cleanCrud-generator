package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.api.application;

import de.gupta.clean.crud.generator.code.generation.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.facade.DomainModelParserServiceFacade;
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