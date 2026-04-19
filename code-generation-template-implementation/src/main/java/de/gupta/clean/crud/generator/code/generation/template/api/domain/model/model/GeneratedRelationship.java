package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

public record GeneratedRelationship(
		Property property,
		String masterAggregate,
		String satelliteAggregate,
		String cardinality,
		String reconciliationStrategy,
		String satelliteApiIdType,
		boolean cascadeCreate,
		boolean cascadeUpdate,
		boolean cascadeDelete,
		boolean orphanDelete,
		boolean hydrateOnFetch,
		boolean generateNestedCreate,
		boolean generateNestedUpdate
)
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

	public String responseType()
	{
		return satelliteAggregate + "APIModelResponse";
	}

	public String responseImport()
	{
		return property.collectionValued() ? property.collectionElementQualifiedTypeName() :
				property.baseTypeQualifiedName();
	}

	public String responseFieldType()
	{
		return many()
				? "Collection<" + responseType() + ">"
				: optional() ? "Optional<" + responseType() + ">" : responseType();
	}

	public String createType()
	{
		return satelliteAggregate + "APIModelCreate";
	}

	public String createImport()
	{
		return property.collectionValued()
				? property.collectionElementQualifiedTypeName().replace("APIModelResponse", "APIModelCreate")
				: property.baseTypeQualifiedName().replace("APIModelResponse", "APIModelCreate");
	}

	public String updatePatchType()
	{
		return satelliteAggregate + "APIModelUpdatePatch";
	}

	public String updatePatchImport()
	{
		return property.collectionValued()
				? property.collectionElementQualifiedTypeName().replace("APIModelResponse", "APIModelUpdatePatch")
				: property.baseTypeQualifiedName().replace("APIModelResponse", "APIModelUpdatePatch");
	}

	public String domainCreateType()
	{
		return satelliteAggregate + "DomainModelCreate";
	}

	public String domainCreateImport(final String basePackage)
	{
		return createImport()
				.replace(".useCases.crud.common.dto.", ".domain.model.dto.")
				.replace("APIModelCreate", "DomainModelCreate");
	}

	public String domainUpdatePatchType()
	{
		return satelliteAggregate + "DomainModelUpdatePatch";
	}

	public String domainUpdatePatchImport(final String basePackage)
	{
		return updatePatchImport()
				.replace(".useCases.crud.common.dto.", ".domain.model.dto.")
				.replace("APIModelUpdatePatch", "DomainModelUpdatePatch");
	}

	public String domainResponseType()
	{
		return satelliteAggregate + "DomainModelResponse";
	}

	public String domainResponseImport(final String basePackage)
	{
		return responseImport()
				.replace(".useCases.crud.common.dto.", ".domain.model.dto.")
				.replace("APIModelResponse", "DomainModelResponse");
	}

	public String persistenceIdPropertyName()
	{
		return many() ? singularPropertyName() + "Ids" : propertyName() + "Id";
	}

	public String persistenceIdPropertyType()
	{
		return many()
				? "Collection<" + satelliteApiIdType + ">"
				: optional() ? "Optional<" + satelliteApiIdType + ">" : satelliteApiIdType;
	}

	public String createFieldType()
	{
		return many()
				? "Collection<" + createType() + ">"
				: optional() ? "Optional<" + createType() + ">" : createType();
	}

	public String apiUpdateFieldType()
	{
		return "Optional<Collection<SatelliteUpdatePatchItem<" + satelliteApiIdType + ", " + updatePatchType() + ">>>";
	}

	public String domainUpdateFieldType()
	{
		return "Optional<Collection<SatelliteUpdatePatchItem<" + satelliteApiIdType + ", " + updatePatchType() + ">>>";
	}

	public String removeFieldName()
	{
		return "remove" + singularPropertyCapitalizedName() + "Ids";
	}

	public String removeFieldType()
	{
		return "Collection<" + satelliteApiIdType + ">";
	}

	private String singularPropertyName()
	{
		return propertyName().endsWith("s") && propertyName().length() > 1
				? propertyName().substring(0, propertyName().length() - 1)
				: propertyName();
	}

	private String singularPropertyCapitalizedName()
	{
		String singularPropertyName = singularPropertyName();
		return Character.toUpperCase(singularPropertyName.charAt(0)) + singularPropertyName.substring(1);
	}
}

