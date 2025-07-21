package de.gupta.clean.crud.generator.code.generation.model.useCases.parsing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.domain.model.Model;

public interface DomainModelParser
{
	Model parseDomainModel(String domainModelSourceCode);
}