package de.gupta.clean.crud.generator.code.generation.model.useCases.parsing.application.service;

import de.gupta.clean.crud.generator.code.generation.model.domain.model.Model;

public interface DomainModelParserService
{
	Model parseDomainModel(String domainModelSourceCode);
}