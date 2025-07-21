package de.gupta.clean.crud.generator.code.generation.api.useCases.parsing.api.application;

import de.gupta.clean.crud.generator.code.generation.api.domain.model.Model;

public interface DomainModelParser
{
	Model parseDomainModel(String domainModelSourceCode);
}