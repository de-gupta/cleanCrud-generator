package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.application.service.parsing.CodeParser;
import org.springframework.stereotype.Component;

@Component
final class DomainModelParserImpl implements DomainModelParser
{
	private final CodeParser codeParser;

	@Override
	public Model parseDomainModel(final String filePath)
	{
		return Model.of(codeParser.typeName(filePath), codeParser.packageName(filePath),
				codeParser.contentRootPath(filePath), codeParser.genericTypesParameters(filePath),
				codeParser.properties(filePath));
	}

	DomainModelParserImpl(final CodeParser codeParser)
	{
		this.codeParser = codeParser;
	}
}