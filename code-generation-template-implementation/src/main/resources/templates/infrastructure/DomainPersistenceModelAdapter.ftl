<#-- Template for generating DomainPersistenceModelAdapter class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.model;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPort;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.function.Function;

<#if isGeneric() && domainGenericImports()?has_content>
<#list domainGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric() && persistenceGenericImports()?has_content>
<#list persistenceGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>
<#list relationships() as relationship>
import ${relationship.responseImport()?replace('.useCases.crud.common.dto.', '.domain.model.')?replace('APIModelResponse', 'DomainModel')};
import ${relationship.domainResponseImport(basePackage())};
import ${relationship.responseImport()};
</#list>

@Component
final class ${modelBaseName()}DomainPersistenceModelAdapter
		implements DomainPersistenceModelAdapter<${modelBaseName()}DomainModel, ${modelBaseName()}PersistenceModel>
{
	private final ModelBuilderFactory<${modelBaseName()}DomainModel,
			${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder>
			domainModelBuilderFactory;
	private final ModelBuilderFactory<${modelBaseName()}PersistenceModel,
			${modelBaseName()}PersistenceModel.${modelBaseName()}PersistenceModelBuilder>
			persistenceModelBuilderFactory;

<#list persistenceDomainDifferingGenericTypeParameters() as param>
	private final Function<${domainConcreteType(param)}, ${persistenceConcreteType(param)}> ${param?lower_case}DomainToPersistenceConverter;
	private final Function<${persistenceConcreteType(param)}, ${domainConcreteType(param)}> ${param?lower_case}PersistenceToDomainConverter;
</#list>
<#list relationships() as relationship>
	private final AggregateFetchPort<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel> ${relationship.relationshipVariablePrefix()}AggregateFetchPort;
	private final DomainResponseBuilder<${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainResponseBuilder;
	private final DomainToAPIResponseAdapter<${relationship.responseType()}, ${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter;
</#list>

	@Override
	public ${modelBaseName()}PersistenceModel toPersistenceModel(final ${modelBaseName()}DomainModel domainModel)
	{
		return persistenceModelBuilderFactory.builder()
<#list standaloneProperties() as property>
<#if property.optional()>
		<#if persistenceAndDomainTypesDiffer(property.baseType())>
		.with${property.capitalizedName()}(domainModel.${property.getter()}().map(${property.baseType()?lower_case}DomainToPersistenceConverter))
		<#else>
		.with${property.capitalizedName()}(domainModel.${property.getter()}())
		</#if>
<#else>
		<#if persistenceAndDomainTypesDiffer(property.baseType())>
		.with${property.capitalizedName()}(${property.baseType()?lower_case}DomainToPersistenceConverter.apply(domainModel.${property.getter()}()))
		<#else>
		.with${property.capitalizedName()}(domainModel.${property.getter()}())
		</#if>
</#if>
</#list>
<#list relationships() as relationship>
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
	public ${modelBaseName()}DomainModel toDomainModel(final ${modelBaseName()}PersistenceModel persistenceModel)
	{
		return domainModelBuilderFactory.builder()
<#list standaloneProperties() as property>
<#if property.optional()>
		<#if persistenceAndDomainTypesDiffer(property.baseType())>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}().map(${property.baseType()?lower_case}PersistenceToDomainConverter))
		<#else>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}())
		</#if>
<#else>
		<#if persistenceAndDomainTypesDiffer(property.baseType())>
		.with${property.capitalizedName()}(${property.baseType()?lower_case}PersistenceToDomainConverter.apply(persistenceModel.${property.getter()}()))
		<#else>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}())
		</#if>
</#if>
</#list>
<#list relationships() as relationship>
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
	public ${modelBaseName()}PersistenceModel updatePersistenceModel(
			final ${modelBaseName()}PersistenceModel persistenceModel,
			final ${modelBaseName()}DomainModel domainModel)
	{
<#list standaloneProperties() as property>
<#if persistenceAndDomainTypesDiffer(property.baseType())>
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
<#list relationships() as relationship>
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

<#list relationships() as relationship>
	private java.util.Optional<${relationship.responseType()}> ${relationship.relationshipVariablePrefix()}(final ${relationship.satelliteDomainIdType()} satelliteId)
	{
		return ${relationship.relationshipVariablePrefix()}AggregateFetchPort.findById(satelliteId)
		                                                         .map(satelliteDomainModel -> IdentifiedModel.of(
					                                                         satelliteId,
					                                                         ${relationship.relationshipVariablePrefix()}DomainResponseBuilder.toResponse(
						                                                         satelliteDomainModel.model())))
		                                                         .map(${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter::mapToAPIModelResponse);
	}

</#list>
	${modelBaseName()}DomainPersistenceModelAdapter(
			final ModelBuilderFactory<${modelBaseName()}DomainModel,
			${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder> domainModelBuilderFactory,
			final ModelBuilderFactory<${modelBaseName()}PersistenceModel,
			${modelBaseName()}PersistenceModel.${modelBaseName()}PersistenceModelBuilder> persistenceModelBuilderFactory<#if persistenceDomainDifferingGenericTypeParameters()?has_content>,
<#list persistenceDomainDifferingGenericTypeParameters() as param>
			@Qualifier("${beanNamePrefix()}${param}DomainToPersistenceConverter") final Function<${domainConcreteType(param)}, ${persistenceConcreteType(param)}> ${param?lower_case}DomainToPersistenceConverter,
			@Qualifier("${beanNamePrefix()}${param}PersistenceToDomainConverter") final Function<${persistenceConcreteType(param)}, ${domainConcreteType(param)}> ${param?lower_case}PersistenceToDomainConverter<#if param_has_next || relationships()?has_content>,</#if>
</#list>
<#elseif relationships()?has_content>,
</#if>
<#list relationships() as relationship>
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateFetchPort") final AggregateFetchPort<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel> ${relationship.relationshipVariablePrefix()}AggregateFetchPort,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainResponseBuilder") final DomainResponseBuilder<${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainResponseBuilder,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainToAPIResponseAdapter") final DomainToAPIResponseAdapter<${relationship.responseType()}, ${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter<#if relationship_has_next>,</#if>
</#list>)
	{
		this.domainModelBuilderFactory = domainModelBuilderFactory;
		this.persistenceModelBuilderFactory = persistenceModelBuilderFactory;
<#list persistenceDomainDifferingGenericTypeParameters() as param>
		this.${param?lower_case}DomainToPersistenceConverter = ${param?lower_case}DomainToPersistenceConverter;
		this.${param?lower_case}PersistenceToDomainConverter = ${param?lower_case}PersistenceToDomainConverter;
</#list>
<#list relationships() as relationship>
		this.${relationship.relationshipVariablePrefix()}AggregateFetchPort = ${relationship.relationshipVariablePrefix()}AggregateFetchPort;
		this.${relationship.relationshipVariablePrefix()}DomainResponseBuilder = ${relationship.relationshipVariablePrefix()}DomainResponseBuilder;
		this.${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter = ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter;
</#list>
	}
}


