package de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;

public interface DomainModelParser
{
	Model parseDomainModel(String filePath);
}