package de.gupta.clean.crud.generator.code.generation.orchestration.configuration;

public record RootAggregateIdConfiguration(
		String apiIdType,
		String domainIdType,
		String persistenceIdType
)
{
	public static RootAggregateIdConfiguration defaults()
	{
		return new RootAggregateIdConfiguration("java.lang.Long", "java.lang.Long", "java.util.UUID");
	}

	public RootAggregateIdConfiguration normalized()
	{
		return new RootAggregateIdConfiguration(
				normalize(apiIdType, defaults().apiIdType()),
				normalize(domainIdType, defaults().domainIdType()),
				normalize(persistenceIdType, defaults().persistenceIdType())
		);
	}

	private static String normalize(final String value, final String fallback)
	{
		return value == null || value.isBlank() ? fallback : value.trim();
	}
}
