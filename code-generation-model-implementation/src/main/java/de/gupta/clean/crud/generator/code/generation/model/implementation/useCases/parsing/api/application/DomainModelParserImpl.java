package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.facade.DomainModelParserServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class DomainModelParserImpl implements DomainModelParser
{
	private final DomainModelParserServiceFacade service;

	@Override
	public Model parseDomainModel(final String filePath)
	{
		return service.parseDomainModel(filePath);
	}

	DomainModelParserImpl(final DomainModelParserServiceFacade service)
	{
		this.service = service;
	}
}