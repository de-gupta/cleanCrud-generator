package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

import java.util.Map;

public record LayerConcreteTypes(
		Map<String, String> domain,
		Map<String, String> persistence,
		Map<String, String> api
)
{
	public static LayerConcreteTypes defaults()
	{
		var map = defaultConcreteTypes();
		return new LayerConcreteTypes(map, map, map);
	}

	public static Map<String, String> defaultConcreteTypes()
	{
		return Map.of("U", "String", "V", "Long");
	}

	public LayerConcreteTypes normalized()
	{
		return new LayerConcreteTypes(
				normalizeConcreteTypes(domain),
				normalizeConcreteTypes(persistence),
				normalizeConcreteTypes(api)
		);
	}

	private static Map<String, String> normalizeConcreteTypes(final Map<String, String> concreteTypes)
	{
		return concreteTypes == null || concreteTypes.isEmpty() ? defaultConcreteTypes() : Map.copyOf(concreteTypes);
	}
}
