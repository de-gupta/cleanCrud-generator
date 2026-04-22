package ${aggregate().basePackage()}.useCases.crud.configuration;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
<#list composition().relationships() as relationship>
import ${relationship.responseImport()?replace('.useCases.crud.common.dto.', '.domain.model.')?replace('APIModelResponse', 'DomainModel')};
import ${relationship.domainCreateImport(aggregate().basePackage())};
import ${relationship.domainResponseImport(aggregate().basePackage())};
import ${relationship.domainUpdatePatchImport(aggregate().basePackage())};
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
class ${aggregate().baseName()}CrudRelationshipConfiguration
{
<#list composition().relationships() as relationship>
	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}LifecycleSemantics")
	LifecycleSemantics ${relationship.relationshipBeanNamePrefix()}LifecycleSemantics()
	{
		// TODO: Review the generated lifecycle defaults for `${relationship.propertyName()}` and change them if your ownership semantics differ.
		// TODO: For REFERENCED relationships, cascadeUpdate means relinking participation during master update, not mutation of the satellite aggregate.
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
	AggregateRelationshipDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}> ${relationship.relationshipBeanNamePrefix()}RelationshipDefinition(
			final ModelBuilderFactory<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder> ${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}LifecycleSemantics") final LifecycleSemantics lifecycleSemantics,
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}, ${relationship.domainResponseType()}> ${relationship.relationshipVariablePrefix()}AggregateCrudDefinition<#if relationship.owned()>,
			@Qualifier("${relationship.satelliteQualifierPrefix()}APIToDomainCreateAdapter") final APIToDomainCreateAdapter<${relationship.createType()}, ${relationship.domainCreateType()}> ${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter,
			@Qualifier("${relationship.satelliteQualifierPrefix()}APIToDomainUpdateAdapter") final APIToDomainUpdateAdapter<${relationship.updatePatchType()}, ${relationship.domainUpdatePatchType()}> ${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter</#if>,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainToAPIResponseAdapter") final DomainToAPIResponseAdapter<${relationship.responseType()}, ${relationship.satelliteApiIdType()}, ${relationship.domainResponseType()}> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter)
	{
		// TODO: Review reconciliation and lifecycle semantics for `${relationship.propertyName()}` before using this generated relationship in production.
		<#if relationship.referenced()>
		// TODO: Review whether `${relationship.propertyName()}` should remain reference-only or should become lifecycle-owned.
		// TODO: Review whether master update should be allowed to relink `${relationship.propertyName()}`.
		</#if>
		return AggregateRelationshipDefinitions
				.<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${relationship.satelliteApiIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}, ${relationship.domainResponseType()}<#if relationship.owned()>, ${relationship.createType()}, ${relationship.updatePatchType()}</#if>, ${relationship.responseType()}>${relationship.builderMethodName()}("${relationship.propertyName()}", ${relationship.relationshipVariablePrefix()}AggregateCrudDefinition)
				.lifecycleSemantics(lifecycleSemantics)
				<#if relationship.owned()>
				.createExtractor(<#if !relationship.generateNestedCreate()>ignored -> <#if relationship.many()>List.of()<#else>Optional.empty()</#if><#elseif relationship.many()>${aggregate().beanNamePrefix()}DomainModelCreate -> ${aggregate().beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}()<#elseif relationship.optional()>${aggregate().beanNamePrefix()}DomainModelCreate -> ${aggregate().beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}()<#else>${aggregate().beanNamePrefix()}DomainModelCreate -> Optional.ofNullable(${aggregate().beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}())</#if>)
				.createMapper(${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter::mapToDomainModelCreate)
				.patchExtractor(<#if relationship.many()><#if relationship.generateNestedUpdate()>${aggregate().beanNamePrefix()}DomainModelUpdatePatch -> ${aggregate().beanNamePrefix()}DomainModelUpdatePatch.${relationship.propertyName()}().orElse(List.of())<#else>ignored -> List.of()</#if><#else><#if relationship.generateNestedUpdate()>${aggregate().beanNamePrefix()}DomainModelUpdatePatch -> ${aggregate().beanNamePrefix()}DomainModelUpdatePatch.${relationship.propertyName()}().map(${relationship.propertyName()} ->
				{
					if (${relationship.propertyName()}.size() > 1)
					{
						throw InvalidRequestException.withMessage("${aggregate().baseName()} ${relationship.propertyName()} updates allow at most one nested ${relationship.propertyName()} mutation");
					}
					return ${relationship.propertyName()}.stream().findFirst();
				}).orElse(Optional.empty())<#else>ignored -> Optional.empty()</#if></#if>)
				.patchMapper(${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter::mapToDomainModelUpdatePatch)
				.patchCreateMapper(${relationship.updatePatchType()?uncap_first} -> ${relationship.domainCreateType()}.fromUpdatePatch(${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter.mapToDomainModelUpdatePatch(${relationship.updatePatchType()?uncap_first})))
				<#else>
				<#if relationship.many()>
				.createReferenceIdsExtractor(${aggregate().beanNamePrefix()}DomainModelCreate -> ${aggregate().beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}())
				.patchReferenceIdsExtractor(${aggregate().beanNamePrefix()}DomainModelUpdatePatch -> ${aggregate().beanNamePrefix()}DomainModelUpdatePatch.${relationship.propertyName()}().orElse(List.of()))
				<#else>
				.createReferenceIdExtractor(<#if relationship.optional()>${aggregate().beanNamePrefix()}DomainModelCreate -> ${aggregate().beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}()<#else>${aggregate().beanNamePrefix()}DomainModelCreate -> Optional.ofNullable(${aggregate().beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}())</#if>)
				.patchReferenceIdExtractor(${aggregate().baseName()}DomainModelUpdatePatch::${relationship.propertyName()})
				</#if>
				</#if>
				.removeIdExtractor(${aggregate().baseName()}DomainModelUpdatePatch::${relationship.removeFieldName()})
				<#if relationship.many()>
				.currentSatellites(${aggregate().baseName()}DomainModel::${relationship.propertyName()})
				.replaceSatellites((${aggregate().beanNamePrefix()}DomainModel, ${relationship.propertyName()}) -> rebuild${aggregate().baseName()}DomainModel(
						${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
						${aggregate().beanNamePrefix()}DomainModel,
<#list composition().relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()>${relationship.propertyName()}<#else>${aggregate().beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				))
				<#else>
				.currentSatellite(<#if relationship.optional()>${aggregate().baseName()}DomainModel::${relationship.propertyName()}<#else>${aggregate().beanNamePrefix()}DomainModel -> Optional.ofNullable(${aggregate().beanNamePrefix()}DomainModel.${relationship.propertyName()}())</#if>)
				.replaceSatellite((${aggregate().beanNamePrefix()}DomainModel, ${relationship.propertyName()}) -> rebuild${aggregate().baseName()}DomainModel(
						${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
						${aggregate().beanNamePrefix()}DomainModel,
<#list composition().relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()><#if relationship.optional()>${relationship.propertyName()}<#else>${relationship.propertyName()}.orElse(null)</#if><#else>${aggregate().beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				))
				</#if>
				.publicResponseMapper(${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter::mapToAPIModelResponse)
				<#if relationship.many() && relationship.reconciliationStrategy() == "REPLACE">.reconciliationStrategy(ReconciliationStrategy.REPLACE)</#if>
				.build();
	}

</#list>
	private ${aggregate().baseName()}DomainModel rebuild${aggregate().baseName()}DomainModel(
			final ModelBuilderFactory<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder> ${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
			final ${aggregate().baseName()}DomainModel ${aggregate().beanNamePrefix()}DomainModel,
<#list composition().relationships() as relationship>
			final ${relationship.responseFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>)
	{
		return ${aggregate().beanNamePrefix()}DomainModelBuilderFactory.builder()
<#list composition().standaloneProperties() as property>
				.with${property.capitalizedName()}(${aggregate().beanNamePrefix()}DomainModel.${property.getter()}())
</#list>
<#list composition().relationships() as relationship>
				.with${relationship.propertyCapitalizedName()}(${relationship.propertyName()})
</#list>
				.build();
	}
}
