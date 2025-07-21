package de.gupta.clean.crud.generator.code.generation.model.useCases.parsing.facade;

import de.gupta.clean.crud.generator.code.generation.model.domain.model.Model;

public interface DomainModelParserServiceFacade
{
	Model parseDomainModel(String domainModelSourceCode);
}