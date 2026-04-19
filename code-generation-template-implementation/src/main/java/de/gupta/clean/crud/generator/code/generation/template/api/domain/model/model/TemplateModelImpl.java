package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.Map;
import java.util.SequencedCollection;
import java.util.Set;

record TemplateModelImpl(
		String packageName,
		String modelName,
		SequencedCollection<String> genericTypeParameters,
		Set<Property> properties,
		Map<String, String> domainConcreteTypes,
		Map<String, String> persistenceConcreteTypes,
		Map<String, String> apiConcreteTypes,
		Set<String> domainGenericImports,
		boolean historized,
		java.util.List<GeneratedRelationship> relationships
)
		implements TemplateModel
{
	@Override
	public String basePackage()
	{
		return packageName.endsWith(".domain.model") ?
				packageName.substring(0, packageName.length() - ".domain.model".length()) :
				packageName;
	}

	@Override
	public boolean isGeneric()
	{
		return !genericTypeParameters.isEmpty();
	}
}
