package de.gupta.clean.crud.generator.code.generation.model.api.domain.model;

import java.nio.file.Path;
import java.util.Map;
import java.util.SequencedCollection;
import java.util.Set;

public record Model(
		String modelName,
		String packageName,
		Path contentRootPath,
		SequencedCollection<String> genericTypeParameters,
		Set<Property> properties
)
{
	public static Model of(final String modelName, final String packageName, final Path contentRootPath,
						   final SequencedCollection<String> genericTypeParameters, final Set<Property> properties)
	{
		return new Model(modelName, packageName, contentRootPath, genericTypeParameters, properties);
	}

	public String basePackage()
	{
		return packageName.substring(0, packageName.lastIndexOf(".")).substring(0, packageName.lastIndexOf("."));
	}

	public boolean isGeneric()
	{
		return !genericTypeParameters.isEmpty();
	}

	public Map<String, Object> asMap()
	{
		return Map.of();
	}
}