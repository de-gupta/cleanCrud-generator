package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.application.service;

import de.gupta.clean.crud.generator.code.generation.api.domain.model.Model;
import de.gupta.commons.utility.javaLanguage.code.CodeTypeAnalysisUtility;
import de.gupta.commons.utility.javaLanguage.packages.PackageExtractor;
import org.springframework.stereotype.Service;

@Service
final class DomainModelParserServiceImpl implements DomainModelParserService
{
	@Override
	public Model parseDomainModel(final String domainModelSourceCode)
	{
		final String modelName = CodeTypeAnalysisUtility.findUniqueTypeName(domainModelSourceCode);
		final String packageName = PackageExtractor.extractPackageName(domainModelSourceCode);
		return null;
	}
}