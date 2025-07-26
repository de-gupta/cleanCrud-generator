package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.facade;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;

public interface DomainModelParserServiceFacade
{
	Model parseDomainModel(String filePath);
}