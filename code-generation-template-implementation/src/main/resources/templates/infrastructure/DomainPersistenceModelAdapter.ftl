<#-- Template for generating DomainPersistenceModelAdapter class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.model;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPort;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
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
import ${relationship.responseImport()?replace('.useCases.crud.common.dto.', '.domain.model.')?replace('APIModelResponse', 'DomainModel')};
import ${relationship.domainResponseImport(aggregate().basePackage())};
import ${relationship.responseImport()};
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
	private final AggregateFetchPort<${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModel> ${relationship.relationshipVariablePrefix()}AggregateFetchPort;
	private final DomainResponseBuilder<${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainResponseBuilder;
	private final DomainToAPIResponseAdapter<${relationship.responseType()}, ${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter;
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
		.with${relationship.propertyCapitalizedName()}Id(domainModel.${relationship.propertyName()}().stream().map(${relationship.responseType()}::id).toList())
		<#elseif relationship.optional()>
		.with${relationship.propertyCapitalizedName()}Id(domainModel.${relationship.propertyName()}().map(${relationship.responseType()}::id))
		<#else>
		.with${relationship.propertyCapitalizedName()}Id(domainModel.${relationship.propertyName()}().id())
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
		persistenceModel.set${relationship.propertyCapitalizedName()}Id(domainModel.${relationship.propertyName()}().stream().map(${relationship.responseType()}::id).toList());
		<#elseif relationship.optional()>
		persistenceModel.set${relationship.propertyCapitalizedName()}Id(domainModel.${relationship.propertyName()}().map(${relationship.responseType()}::id).orElse(null));
		<#else>
		persistenceModel.set${relationship.propertyCapitalizedName()}Id(domainModel.${relationship.propertyName()}().id());
		</#if>
</#list>
		return persistenceModel;
	}

<#list composition().relationships() as relationship>
	private java.util.Optional<${relationship.responseType()}> ${relationship.relationshipVariablePrefix()}(final ${relationship.satelliteApiIdType()} satelliteId)
	{
		return ${relationship.relationshipVariablePrefix()}AggregateFetchPort.findById(satelliteId)
		                                                         .map(satelliteDomainModel -> IdentifiedModel.of(
					                                                         satelliteId,
					                                                         ${relationship.relationshipVariablePrefix()}DomainResponseBuilder.toResponse(
						                                                         satelliteDomainModel.model())))
		                                                         .map(${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter::mapToAPIModelResponse);
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
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateFetchPort") final AggregateFetchPort<${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModel> ${relationship.relationshipVariablePrefix()}AggregateFetchPort,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainResponseBuilder") final DomainResponseBuilder<${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainResponseBuilder,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainToAPIResponseAdapter") final DomainToAPIResponseAdapter<${relationship.responseType()}, ${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter<#if relationship_has_next>,</#if>
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
		this.${relationship.relationshipVariablePrefix()}DomainResponseBuilder = ${relationship.relationshipVariablePrefix()}DomainResponseBuilder;
		this.${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter = ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter;
</#list>
	}
}



