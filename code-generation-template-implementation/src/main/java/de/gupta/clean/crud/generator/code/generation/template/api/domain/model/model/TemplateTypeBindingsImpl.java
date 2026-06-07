package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import java.util.Map;
import java.util.SequencedCollection;

record TemplateTypeBindingsImpl(
		SequencedCollection<String> parameters,
		SequencedCollection<String> relationshipParameters,
		Map<String, String> domainConcreteTypes,
		Map<String, String> persistenceConcreteTypes,
		Map<String, String> apiConcreteTypes)
		implements TemplateTypeBindings
{
	@Override
	public boolean isGeneric()
	{
		return !parameters.isEmpty();
	}

	@Override
	public SequencedCollection<String> apiDomainDifferingParameters()
	{
		return parameters.stream()
		                 .filter(parameter -> !relationshipParameters.contains(parameter))
		                 .filter(parameter -> !apiConcreteTypes.get(parameter).equals(domainConcreteTypes.get(
								 parameter)))
		                 .toList();
	}

	@Override
	public SequencedCollection<String> persistenceDomainDifferingParameters()
	{
		return parameters.stream()
		                 .filter(parameter -> !relationshipParameters.contains(parameter))
		                 .filter(parameter -> !persistenceConcreteTypes.get(parameter).equals(domainConcreteTypes.get(
								 parameter)))
		                 .toList();
	}
}
