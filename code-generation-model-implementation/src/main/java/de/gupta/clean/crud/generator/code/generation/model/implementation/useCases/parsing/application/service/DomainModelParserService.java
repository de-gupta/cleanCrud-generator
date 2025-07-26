package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.application.service;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;

public interface DomainModelParserService
{
	Model parseDomainModel(String filePath);
}