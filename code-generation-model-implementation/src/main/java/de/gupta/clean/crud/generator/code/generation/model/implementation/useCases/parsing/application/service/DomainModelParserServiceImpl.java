package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.application.service;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.application.service.parsing.CodeParser;
import org.springframework.stereotype.Service;

@Service
final class DomainModelParserServiceImpl implements DomainModelParserService
{
	private final CodeParser codeParser;

	@Override
	public Model parseDomainModel(final String filePath)
	{
		return Model.of(codeParser.typeName(filePath), codeParser.packageName(filePath),
				codeParser.contentRootPath(filePath), codeParser.genericTypesParameters(filePath),
				codeParser.properties(filePath));
	}

	DomainModelParserServiceImpl(final CodeParser codeParser)
	{
		this.codeParser = codeParser;
	}
}