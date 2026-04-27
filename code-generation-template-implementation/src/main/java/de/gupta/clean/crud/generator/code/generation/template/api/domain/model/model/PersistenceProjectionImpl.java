package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

record PersistenceProjectionImpl(
		AggregateDescriptor aggregate,
		AggregateComposition composition,
		Map<String, String> concreteTypes,
		Set<String> domainGenericImports)
		implements PersistenceProjection
{
	private static final Map<String, String> RESERVED_SQL_IDENTIFIERS = Map.ofEntries(
			Map.entry("user", "user_id"),
			Map.entry("order", "order_value"),
			Map.entry("group", "group_value"),
			Map.entry("key", "key_value"),
			Map.entry("value", "value_value")
	);
	private static final Set<String> SIMPLE_PERSISTENCE_TYPES = Set.of(
			"String",
			"Boolean",
			"boolean",
			"Byte",
			"byte",
			"Short",
			"short",
			"Integer",
			"int",
			"Long",
			"long",
			"Float",
			"float",
			"Double",
			"double",
			"Character",
			"char",
			"UUID",
			"LocalDate",
			"LocalDateTime",
			"LocalTime",
			"Instant",
			"OffsetDateTime",
			"ZonedDateTime",
			"BigDecimal",
			"BigInteger"
	);

	@Override
	public Set<String> genericImports()
	{
		return PersistenceProjectionSupport.genericImports(domainGenericImports, concreteTypes);
	}

	@Override
	public Set<String> imports()
	{
		return PersistenceProjectionSupport.imports(aggregate, composition, domainGenericImports, concreteTypes);
	}

	@Override
	public Set<String> interfaceImports()
	{
		return PersistenceProjectionSupport.interfaceImports(aggregate, composition, domainGenericImports,
				concreteTypes);
	}

	@Override
	public String concreteType(final String genericType)
	{
		return TypeNameSupport.concreteType(concreteTypes, genericType);
	}

	@Override
	public String resolvedType(final String declaredType)
	{
		return TypeNameSupport.resolvedType(concreteTypes, declaredType);
	}

	@Override
	public String boxedResolvedType(final String declaredType)
	{
		return TypeNameSupport.boxedResolvedType(concreteTypes, declaredType);
	}

	@Override
	public String propertyType(final Property property)
	{
		return TypeNameSupport.valueType(concreteTypes, property);
	}

	@Override
	public String builderPropertyType(final Property property)
	{
		return TypeNameSupport.valueType(concreteTypes, property);
	}

	@Override
	public String sqlIdentifier(final String identifier)
	{
		return RESERVED_SQL_IDENTIFIERS.getOrDefault(identifier.toLowerCase(Locale.ROOT), identifier);
	}

	@Override
	public String sqlColumnName(final Property property)
	{
		return sqlIdentifier(property.name());
	}

	@Override
	public String modelTableName()
	{
		return sqlIdentifier(aggregate.baseName().toLowerCase(Locale.ROOT) + "_persistence_model");
	}

	@Override
	public String historyTableName()
	{
		return sqlIdentifier(aggregate.baseName().toLowerCase(Locale.ROOT) + "_persistence_model_history");
	}

	@Override
	public String adapterTableName()
	{
		return sqlIdentifier(aggregate.baseName().toLowerCase(Locale.ROOT) + "_domain_persistence_adapter_model");
	}

	@Override
	public String adapterHistoryTableName()
	{
		return sqlIdentifier(
				aggregate.baseName().toLowerCase(Locale.ROOT) + "_domain_persistence_adapter_model_history");
	}

	@Override
	public String jpaConvertersTypeName()
	{
		return aggregate.baseName() + "PersistenceJpaConverters";
	}

	@Override
	public boolean requiresJpaConverter(final Property property)
	{
		if (property.isEnum())
		{
			return false;
		}
		return !SIMPLE_PERSISTENCE_TYPES.contains(TypeNameSupport.rawTypeName(resolvedType(property.baseType())));
	}

	@Override
	public java.util.SequencedCollection<Property> converterProperties()
	{
		return composition.standaloneProperties().stream().filter(this::requiresJpaConverter).toList();
	}

	@Override
	public String converterNestedClassName(final Property property)
	{
		return property.capitalizedName() + "JpaConverter";
	}
}