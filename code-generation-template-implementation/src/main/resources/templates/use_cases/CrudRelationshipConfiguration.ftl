package ${basePackage()}.useCases.crud.configuration;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
<#list relationships() as relationship>
import ${relationship.responseImport()?replace('.useCases.crud.common.dto.', '.domain.model.')?replace('APIModelResponse', 'DomainModel')};
import ${relationship.domainCreateImport(basePackage())};
import ${relationship.domainResponseImport(basePackage())};
import ${relationship.domainUpdatePatchImport(basePackage())};
import ${relationship.createImport()};
import ${relationship.updatePatchImport()};
import ${relationship.responseImport()};
</#list>
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.domain.model.exceptions.operation.InvalidRequestException;
import de.gupta.clean.crud.template.useCases.crud.aggregate.builder.AggregateRelationshipDefinitions;
import de.gupta.clean.crud.template.useCases.crud.aggregate.builder.LifecycleSemanticsBuilder;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.AggregateCrudDefinition;
import de.gupta.clean.crud.template.useCases.crud.aggregate.lifecycle.LifecycleSemantics;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.AggregateRelationshipDefinition;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.ReconciliationStrategy;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Configuration
class ${modelBaseName()}CrudRelationshipConfiguration
{
<#list relationships() as relationship>
	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}LifecycleSemantics")
	LifecycleSemantics ${relationship.relationshipBeanNamePrefix()}LifecycleSemantics()
	{
		// TODO: Review the generated lifecycle defaults for `${relationship.propertyName()}` and change them if your ownership semantics differ.
		return LifecycleSemanticsBuilder.lifecycleSemantics()
				<#if relationship.cascadeCreate()>.cascadeCreate()
				</#if><#if relationship.cascadeUpdate()>.cascadeUpdate()
				</#if><#if relationship.cascadeDelete()>.cascadeDelete()
				</#if><#if relationship.orphanDelete()>.orphanDelete()
				</#if><#if relationship.hydrateOnFetch()>.hydrateOnFetch()
				</#if>.build();
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}RelationshipDefinition")
	AggregateRelationshipDefinition<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}> ${relationship.relationshipBeanNamePrefix()}RelationshipDefinition(
			final ModelBuilderFactory<${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder> ${beanNamePrefix()}DomainModelBuilderFactory,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}LifecycleSemantics") final LifecycleSemantics lifecycleSemantics,
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}, ${relationship.domainResponseType()}> ${relationship.relationshipVariablePrefix()}AggregateCrudDefinition,
			@Qualifier("${relationship.satelliteQualifierPrefix()}APIToDomainCreateAdapter") final APIToDomainCreateAdapter<${relationship.createType()}, ${relationship.domainCreateType()}> ${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter,
			@Qualifier("${relationship.satelliteQualifierPrefix()}APIToDomainUpdateAdapter") final APIToDomainUpdateAdapter<${relationship.updatePatchType()}, ${relationship.domainUpdatePatchType()}> ${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainToAPIResponseAdapter") final DomainToAPIResponseAdapter<${relationship.responseType()}, ${relationship.satelliteApiIdType()}, ${relationship.domainResponseType()}> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter)
	{
		// TODO: Review reconciliation and lifecycle semantics for `${relationship.propertyName()}` before using this generated relationship in production.
		return AggregateRelationshipDefinitions
				.<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}, ${relationship.domainResponseType()}, ${relationship.createType()}, ${relationship.updatePatchType()}, ${relationship.responseType()}><#if relationship.many()>oneToManySatellite<#else>oneToOneSatellite</#if>("${relationship.propertyName()}", ${relationship.relationshipVariablePrefix()}AggregateCrudDefinition)
				.lifecycleSemantics(lifecycleSemantics)
				.createExtractor(<#if !relationship.generateNestedCreate()>ignored -> <#if relationship.many()>List.of()<#else>Optional.empty()</#if><#elseif relationship.many()>${beanNamePrefix()}DomainModelCreate -> ${beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}()<#elseif relationship.optional()>${beanNamePrefix()}DomainModelCreate -> ${beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}()<#else>${beanNamePrefix()}DomainModelCreate -> Optional.ofNullable(${beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}())</#if>)
				.createMapper(${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter::mapToDomainModelCreate)
				.patchExtractor(<#if relationship.many()><#if relationship.generateNestedUpdate()>${beanNamePrefix()}DomainModelUpdatePatch -> ${beanNamePrefix()}DomainModelUpdatePatch.${relationship.propertyName()}().orElse(List.of())<#else>ignored -> List.of()</#if><#else><#if relationship.generateNestedUpdate()>${beanNamePrefix()}DomainModelUpdatePatch -> ${beanNamePrefix()}DomainModelUpdatePatch.${relationship.propertyName()}().map(${relationship.propertyName()} ->
				{
					if (${relationship.propertyName()}.size() > 1)
					{
						throw InvalidRequestException.withMessage("${modelBaseName()} ${relationship.propertyName()} updates allow at most one nested ${relationship.propertyName()} mutation");
					}
					return ${relationship.propertyName()}.stream().findFirst();
				}).orElse(Optional.empty())<#else>ignored -> Optional.empty()</#if></#if>)
				.patchMapper(${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter::mapToDomainModelUpdatePatch)
				.patchCreateMapper(${relationship.updatePatchType()?uncap_first} -> ${relationship.domainCreateType()}.fromUpdatePatch(${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter.mapToDomainModelUpdatePatch(${relationship.updatePatchType()?uncap_first})))
				.removeIdExtractor(${modelBaseName()}DomainModelUpdatePatch::${relationship.removeFieldName()})
				<#if relationship.many()>
				.currentSatellites(${modelBaseName()}DomainModel::${relationship.propertyName()})
				.replaceSatellites((${beanNamePrefix()}DomainModel, ${relationship.propertyName()}) -> rebuild${modelBaseName()}DomainModel(
						${beanNamePrefix()}DomainModelBuilderFactory,
						${beanNamePrefix()}DomainModel,
<#list relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()>${relationship.propertyName()}<#else>${beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				))
				<#else>
				.currentSatellite(<#if relationship.optional()>${modelBaseName()}DomainModel::${relationship.propertyName()}<#else>${beanNamePrefix()}DomainModel -> Optional.ofNullable(${beanNamePrefix()}DomainModel.${relationship.propertyName()}())</#if>)
				.replaceSatellite((${beanNamePrefix()}DomainModel, ${relationship.propertyName()}) -> rebuild${modelBaseName()}DomainModel(
						${beanNamePrefix()}DomainModelBuilderFactory,
						${beanNamePrefix()}DomainModel,
<#list relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()><#if relationship.optional()>${relationship.propertyName()}<#else>${relationship.propertyName()}.orElse(null)</#if><#else>${beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				))
				</#if>
				.publicResponseMapper(${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter::mapToAPIModelResponse)
				<#if relationship.many() && relationship.reconciliationStrategy() == "REPLACE">.reconciliationStrategy(ReconciliationStrategy.REPLACE)</#if>
				.build();
	}

</#list>
	private ${modelBaseName()}DomainModel rebuild${modelBaseName()}DomainModel(
			final ModelBuilderFactory<${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder> ${beanNamePrefix()}DomainModelBuilderFactory,
			final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel,
<#list relationships() as relationship>
			final ${relationship.responseFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>)
	{
		return ${beanNamePrefix()}DomainModelBuilderFactory.builder()
<#list standaloneProperties() as property>
				.with${property.capitalizedName()}(${beanNamePrefix()}DomainModel.${property.getter()}())
</#list>
<#list relationships() as relationship>
				.with${relationship.propertyCapitalizedName()}(${relationship.propertyName()})
</#list>
				.build();
	}
}





