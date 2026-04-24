package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

public record GeneratedRelationship(
		Property property,
		String genericPlaceholder,
		String masterAggregate,
		String satelliteAggregate,
		String satelliteBaseModelType,
		String relationshipKind,
		String cardinality,
		String reconciliationStrategy,
		String satelliteApiIdType,
		String satelliteDomainIdType,
		String satellitePersistenceIdType,
		boolean cascadeCreate,
		boolean cascadeUpdate,
		boolean cascadeDelete,
		boolean orphanDelete,
		boolean hydrateOnFetch,
		boolean generateNestedCreate,
		boolean generateNestedUpdate)
{
	public String propertyName()
	{
		return property.name();
	}

	public String propertyCapitalizedName()
	{
		return property.capitalizedName();
	}

	public String relationshipBeanNamePrefix()
	{
		return propertyName();
	}

	public String relationshipVariablePrefix()
	{
		return propertyName();
	}

	public boolean many()
	{
		return "MANY".equals(cardinality);
	}

	public boolean one()
	{
		return "ONE".equals(cardinality);
	}

	public boolean owned()
	{
		return "OWNED".equals(relationshipKind);
	}

	public boolean referenced()
	{
		return "REFERENCED".equals(relationshipKind);
	}

	public boolean optional()
	{
		return !many() && property.optional();
	}

	public String satelliteBeanNamePrefix()
	{
		return Character.toLowerCase(satelliteAggregate.charAt(0)) + satelliteAggregate.substring(1);
	}

	public String satelliteQualifierPrefix()
	{
		return satelliteBeanNamePrefix();
	}

	public String satelliteBasePackage()
	{
		String canonical = satelliteBaseModelType;
		return canonical.endsWith(".domain.model." + satelliteAggregate + "Model")
				?
				canonical.substring(0, canonical.length() - (".domain.model." + satelliteAggregate + "Model").length())
				: canonical.substring(0, canonical.lastIndexOf('.'));
	}

	public String responseType()
	{
		return satelliteAggregate + "APIModelResponse";
	}

	public String responseImport()
	{
		return satelliteBasePackage() + ".useCases.crud.common.dto." + responseType();
	}

	public String apiResponseFieldType()
	{
		return wrapByCardinality(responseType());
	}

	public String createType()
	{
		return satelliteAggregate + "APIModelCreate";
	}

	public String createImport()
	{
		return satelliteBasePackage() + ".useCases.crud.common.dto." + createType();
	}

	public String updatePatchType()
	{
		return satelliteAggregate + "APIModelUpdatePatch";
	}

	public String updatePatchImport()
	{
		return satelliteBasePackage() + ".useCases.crud.common.dto." + updatePatchType();
	}

	public String domainCreateType()
	{
		return satelliteAggregate + "DomainModelCreate";
	}

	public String domainCreateImport(final String basePackage)
	{
		return satelliteBasePackage() + ".domain.model.dto." + domainCreateType();
	}

	public String domainUpdatePatchType()
	{
		return satelliteAggregate + "DomainModelUpdatePatch";
	}

	public String domainUpdatePatchImport(final String basePackage)
	{
		return satelliteBasePackage() + ".domain.model.dto." + domainUpdatePatchType();
	}

	public String domainResponseType()
	{
		return satelliteAggregate + "DomainModelResponse";
	}

	public String domainResponseImport(final String basePackage)
	{
		return satelliteBasePackage() + ".domain.model.dto." + domainResponseType();
	}

	public String domainModelType()
	{
		return satelliteAggregate + "DomainModel";
	}

	public String domainModelImport()
	{
		return satelliteBasePackage() + ".domain.model." + domainModelType();
	}

	public String responseFieldType()
	{
		return domainFieldType();
	}

	public String domainFieldType()
	{
		return wrapByCardinality(identifiedDomainModelType());
	}

	public String identifiedDomainModelType()
	{
		return normalized(
				"de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel<" + satelliteDomainIdType + ", " + domainModelType() + ">");
	}

	public String persistenceIdPropertyName()
	{
		return propertyName();
	}

	public String persistenceIdPropertyType()
	{
		return wrapByCardinality(satellitePersistenceIdType);
	}

	public String satelliteApiIdSimpleType()
	{
		return normalized(satelliteApiIdType);
	}

	public String satelliteDomainIdSimpleType()
	{
		return normalized(satelliteDomainIdType);
	}

	public String satellitePersistenceIdSimpleType()
	{
		return normalized(satellitePersistenceIdType);
	}

	public String createFieldType()
	{
		return apiCreateFieldType();
	}

	public String apiCreateFieldType()
	{
		if (referenced())
		{
			return wrapByCardinality(satelliteApiIdType);
		}
		return wrapByCardinality(createType());
	}

	public String domainCreateFieldType()
	{
		if (referenced())
		{
			return wrapByCardinality(satelliteDomainIdType);
		}
		return wrapByCardinality(domainCreateType());
	}

	public String apiUpdateFieldType()
	{
		if (referenced())
		{
			return many() ? normalized("Optional<Collection<" + satelliteApiIdType + ">>") :
					normalized("Optional<" + satelliteApiIdType + ">");
		}
		if (many())
		{
			return normalized(
					"Optional<Collection<de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.standard.SatelliteUpdatePatchItem<" + satelliteApiIdType + ", " + updatePatchType() + ">>>");
		}
		return normalized("Optional<" + updatePatchType() + ">");
	}

	public String domainUpdateFieldType()
	{
		if (referenced())
		{
			return many() ? normalized("Optional<Collection<" + satelliteDomainIdType + ">>") :
					normalized("Optional<" + satelliteDomainIdType + ">");
		}
		if (many())
		{
			return normalized(
					"Optional<Collection<de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.standard.SatelliteUpdatePatchItem<" + satelliteDomainIdType + ", " + domainUpdatePatchType() + ">>>");
		}
		return normalized("Optional<" + domainUpdatePatchType() + ">");
	}

	public String apiRemoveFieldType()
	{
		return normalized("Collection<" + satelliteApiIdType + ">");
	}

	public String domainRemoveFieldType()
	{
		return normalized("Collection<" + satelliteDomainIdType + ">");
	}

	public String removeFieldName()
	{
		return "remove" + singularPropertyCapitalizedName() + "Ids";
	}

	public String builderMethodName()
	{
		return owned()
				? one() ? "oneToOneSatellite" : "oneToManySatellite"
				: one() ? "oneToOneReferencedSatellite" : "oneToManyReferencedSatellite";
	}

	public String lowLevelIdentityResolverFieldType()
	{
		return normalized(
				"de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatelliteIdentityResolver<" + masterAggregate + "DomainModel, " + domainModelType() + ", " + satelliteDomainIdType + ">");
	}

	public String singularPropertyName()
	{
		return propertyName().endsWith("s") && propertyName().length() > 1
				? propertyName().substring(0, propertyName().length() - 1)
				: propertyName();
	}

	public String singularPropertyCapitalizedName()
	{
		String singularPropertyName = singularPropertyName();
		return Character.toUpperCase(singularPropertyName.charAt(0)) + singularPropertyName.substring(1);
	}

	private String wrapByCardinality(final String elementType)
	{
		return many()
				? normalized("Collection<" + elementType + ">")
				: optional() ? normalized("Optional<" + elementType + ">") : normalized(elementType);
	}

	private String normalized(final String declaredType)
	{
		return ProjectionSupport.normalizeGeneratedType(declaredType);
	}
}