<#-- Template for generating DomainPersistenceModelAdapter class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.model;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.function.Function;

<#if types().isGeneric() && domain().genericImports()?has_content>
<#list domain().genericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if types().isGeneric() && persistence().genericImports()?has_content>
<#list persistence().genericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>
<#list composition().relationships() as relationship>
import ${relationship.domainModelImport()};
</#list>

@Component
final class ${aggregate().baseName()}DomainPersistenceModelAdapter
		implements DomainPersistenceModelAdapter<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}PersistenceModel>
{
	private final ModelBuilderFactory<${aggregate().baseName()}DomainModel,
			${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder>
			domainModelBuilderFactory;
	private final ModelBuilderFactory<${aggregate().baseName()}PersistenceModel,
			${aggregate().baseName()}PersistenceModel.${aggregate().baseName()}PersistenceModelBuilder>
			persistenceModelBuilderFactory;

<#list types().persistenceDomainDifferingParameters() as param>
	private final Function<${domain().concreteType(param)}, ${persistence().concreteType(param)}> ${param?lower_case}DomainToPersistenceConverter;
	private final Function<${persistence().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}PersistenceToDomainConverter;
</#list>
<#list composition().relationships() as relationship>
	private final de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPort<${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}> ${relationship.relationshipVariablePrefix()}AggregateFetchPort;
	private final DomainPersistenceIDAdapter<${relationship.satelliteDomainIdType()}, ${relationship.satellitePersistenceIdType()}> ${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter;
</#list>

	@Override
	public ${aggregate().baseName()}PersistenceModel toPersistenceModel(final ${aggregate().baseName()}DomainModel domainModel)
	{
		return persistenceModelBuilderFactory.builder()
<#list composition().standaloneProperties() as property>
<#if property.optional()>
		<#if types().persistenceDomainDifferingParameters()?seq_contains(property.baseType())>
		.with${property.capitalizedName()}(domainModel.${property.getter()}().map(${property.baseType()?lower_case}DomainToPersistenceConverter))
		<#else>
		.with${property.capitalizedName()}(domainModel.${property.getter()}())
		</#if>
<#else>
		<#if types().persistenceDomainDifferingParameters()?seq_contains(property.baseType())>
		.with${property.capitalizedName()}(${property.baseType()?lower_case}DomainToPersistenceConverter.apply(domainModel.${property.getter()}()))
		<#else>
		.with${property.capitalizedName()}(domainModel.${property.getter()}())
		</#if>
</#if>
</#list>
<#list composition().relationships() as relationship>
		<#if relationship.many()>
		.with${relationship.propertyCapitalizedName()}(domainModel.${relationship.propertyName()}().stream().map(IdentifiedModel::id).map(${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter::toPersistenceID).flatMap(java.util.Optional::stream).toList())
		<#elseif relationship.optional()>
		.with${relationship.propertyCapitalizedName()}(domainModel.${relationship.propertyName()}().map(IdentifiedModel::id).flatMap(${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter::toPersistenceID))
		<#else>
		.with${relationship.propertyCapitalizedName()}(${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter.toPersistenceID(domainModel.${relationship.propertyName()}().id()).orElseThrow())
		</#if>
</#list>
		.build();
	}

	@Override
	public ${aggregate().baseName()}DomainModel toDomainModel(final ${aggregate().baseName()}PersistenceModel persistenceModel)
	{
		return domainModelBuilderFactory.builder()
<#list composition().standaloneProperties() as property>
<#if property.optional()>
		<#if types().persistenceDomainDifferingParameters()?seq_contains(property.baseType())>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}().map(${property.baseType()?lower_case}PersistenceToDomainConverter))
		<#else>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}())
		</#if>
<#else>
		<#if types().persistenceDomainDifferingParameters()?seq_contains(property.baseType())>
		.with${property.capitalizedName()}(${property.baseType()?lower_case}PersistenceToDomainConverter.apply(persistenceModel.${property.getter()}()))
		<#else>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}())
		</#if>
</#if>
</#list>
<#list composition().relationships() as relationship>
		<#if relationship.many()>
		.with${relationship.propertyCapitalizedName()}(persistenceModel.${relationship.persistenceIdPropertyName()}().stream()
		                                                                                   .map(this::${relationship.relationshipVariablePrefix()})
		                                                                                   .flatMap(java.util.Optional::stream)
		                                                                                   .toList())
		<#elseif relationship.optional()>
		.with${relationship.propertyCapitalizedName()}(persistenceModel.${relationship.persistenceIdPropertyName()}().flatMap(this::${relationship.relationshipVariablePrefix()}))
		<#else>
		.with${relationship.propertyCapitalizedName()}(${relationship.relationshipVariablePrefix()}(persistenceModel.${relationship.persistenceIdPropertyName()}()).orElse(null))
		</#if>
</#list>
		.build();
	}

	@Override
	public ${aggregate().baseName()}PersistenceModel updatePersistenceModel(
			final ${aggregate().baseName()}PersistenceModel persistenceModel,
			final ${aggregate().baseName()}DomainModel domainModel)
	{
<#list composition().standaloneProperties() as property>
<#if types().persistenceDomainDifferingParameters()?seq_contains(property.baseType())>
	<#if !property.optional()>
		persistenceModel.set${property.capitalizedName()}(${property.baseType()?lower_case}DomainToPersistenceConverter.apply(domainModel.${property.getter()}()));
	<#else>
		domainModel.${property.getter()}().map(${property.baseType()?lower_case}DomainToPersistenceConverter).ifPresent(persistenceModel::set${property.capitalizedName()});
	</#if>
<#else>
	<#if !property.optional()>
		persistenceModel.set${property.capitalizedName()}(domainModel.${property.getter()}());
	<#else>
		domainModel.${property.getter()}().ifPresent(persistenceModel::set${property.capitalizedName()});
	</#if>
</#if>
</#list>
<#list composition().relationships() as relationship>
		<#if relationship.many()>
		persistenceModel.set${relationship.propertyCapitalizedName()}(domainModel.${relationship.propertyName()}().stream().map(IdentifiedModel::id).map(${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter::toPersistenceID).flatMap(java.util.Optional::stream).toList());
		<#elseif relationship.optional()>
		persistenceModel.set${relationship.propertyCapitalizedName()}(domainModel.${relationship.propertyName()}().map(IdentifiedModel::id).flatMap(${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter::toPersistenceID).orElse(null));
		<#else>
		persistenceModel.set${relationship.propertyCapitalizedName()}(${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter.toPersistenceID(domainModel.${relationship.propertyName()}().id()).orElseThrow());
		</#if>
</#list>
		return persistenceModel;
	}

<#list composition().relationships() as relationship>
	private java.util.Optional<${relationship.identifiedDomainModelType()}> ${relationship.relationshipVariablePrefix()}(final ${relationship.satellitePersistenceIdType()} satelliteId)
	{
		return ${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter.toDomainID(satelliteId)
		                                                          .flatMap(${relationship.relationshipVariablePrefix()}AggregateFetchPort::findById)
		                                                          .map(satelliteDomainModel -> IdentifiedModel.of(
					                                                          satelliteDomainModel.id(),
					                                                          satelliteDomainModel.model()));
	}

</#list>
	${aggregate().baseName()}DomainPersistenceModelAdapter(
			final ModelBuilderFactory<${aggregate().baseName()}DomainModel,
			${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder> domainModelBuilderFactory,
			final ModelBuilderFactory<${aggregate().baseName()}PersistenceModel,
			${aggregate().baseName()}PersistenceModel.${aggregate().baseName()}PersistenceModelBuilder> persistenceModelBuilderFactory<#if types().persistenceDomainDifferingParameters()?has_content>,
<#list types().persistenceDomainDifferingParameters() as param>
			@Qualifier("${aggregate().beanNamePrefix()}${param}DomainToPersistenceConverter") final Function<${domain().concreteType(param)}, ${persistence().concreteType(param)}> ${param?lower_case}DomainToPersistenceConverter,
			@Qualifier("${aggregate().beanNamePrefix()}${param}PersistenceToDomainConverter") final Function<${persistence().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}PersistenceToDomainConverter<#if param_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#elseif composition().relationships()?has_content>,
</#if>
<#list composition().relationships() as relationship>
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateFetchPort") final de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPort<${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}> ${relationship.relationshipVariablePrefix()}AggregateFetchPort,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainPersistenceIDAdapter") final DomainPersistenceIDAdapter<${relationship.satelliteDomainIdType()}, ${relationship.satellitePersistenceIdType()}> ${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter<#if relationship_has_next>,</#if>
</#list>)
	{
		this.domainModelBuilderFactory = domainModelBuilderFactory;
		this.persistenceModelBuilderFactory = persistenceModelBuilderFactory;
<#list types().persistenceDomainDifferingParameters() as param>
		this.${param?lower_case}DomainToPersistenceConverter = ${param?lower_case}DomainToPersistenceConverter;
		this.${param?lower_case}PersistenceToDomainConverter = ${param?lower_case}PersistenceToDomainConverter;
</#list>
<#list composition().relationships() as relationship>
		this.${relationship.relationshipVariablePrefix()}AggregateFetchPort = ${relationship.relationshipVariablePrefix()}AggregateFetchPort;
		this.${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter = ${relationship.relationshipVariablePrefix()}DomainPersistenceIDAdapter;
</#list>
	}
}


