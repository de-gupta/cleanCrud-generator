package ${aggregate().basePackage()}.useCases.crud.configuration;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}Relationships;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
<#list composition().relationships() as relationship>
import ${relationship.domainModelImport()};
import ${relationship.domainCreateImport(aggregate().basePackage())};
import ${relationship.domainUpdatePatchImport(aggregate().basePackage())};
</#list>
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.useCases.crud.aggregate.builder.AggregateRelationshipDefinitions;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.AggregateCrudDefinition;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.AggregateRelationshipDefinition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Optional;

@Configuration
class ${aggregate().baseName()}CrudRelationshipConfiguration
{
<#list composition().relationships() as relationship>
	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}RelationshipDefinition")
	AggregateRelationshipDefinition<${aggregate().rootDomainIdType()}, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}> ${relationship.relationshipBeanNamePrefix()}RelationshipDefinition(
			final ModelBuilderFactory<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder> ${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}, ?> ${relationship.relationshipVariablePrefix()}AggregateCrudDefinition)
	{
		return AggregateRelationshipDefinitions
				.<${aggregate().rootDomainIdType()}, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}>fromRelationship(
						new ${aggregate().baseName()}Relationships().relationship("${relationship.propertyName()}"),
						${relationship.relationshipVariablePrefix()}AggregateCrudDefinition)
				<#if relationship.many()>
				.currentMany(${aggregate().baseName()}DomainModel::${relationship.propertyName()})
				.replaceMany((${aggregate().beanNamePrefix()}DomainModel, ${relationship.propertyName()}) ->
						rebuild${aggregate().baseName()}DomainModel(
								${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
								${aggregate().beanNamePrefix()}DomainModel,
<#list composition().relationships() as rebuildRelationship>
								<#if rebuildRelationship.propertyName() == relationship.propertyName()>${relationship.propertyName()}<#else>${aggregate().beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
						))
				<#elseif relationship.optional()>
				.current(${aggregate().baseName()}DomainModel::${relationship.propertyName()})
				.replace((${aggregate().beanNamePrefix()}DomainModel, ${relationship.propertyName()}) ->
						rebuild${aggregate().baseName()}DomainModel(
								${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
								${aggregate().beanNamePrefix()}DomainModel,
<#list composition().relationships() as rebuildRelationship>
								<#if rebuildRelationship.propertyName() == relationship.propertyName()>${relationship.propertyName()}<#else>${aggregate().beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
						))
				<#else>
				.current(${aggregate().beanNamePrefix()}DomainModel -> Optional.ofNullable(${aggregate().beanNamePrefix()}DomainModel.${relationship.propertyName()}()))
				.replace((${aggregate().beanNamePrefix()}DomainModel, ${relationship.propertyName()}) ->
						rebuild${aggregate().baseName()}DomainModel(
								${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
								${aggregate().beanNamePrefix()}DomainModel,
<#list composition().relationships() as rebuildRelationship>
								<#if rebuildRelationship.propertyName() == relationship.propertyName()>${relationship.propertyName()}.orElse(null)<#else>${aggregate().beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
						))
				</#if>
				.build();
	}

</#list>
	private ${aggregate().baseName()}DomainModel rebuild${aggregate().baseName()}DomainModel(
			final ModelBuilderFactory<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder> ${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
			final ${aggregate().baseName()}DomainModel ${aggregate().beanNamePrefix()}DomainModel,
<#list composition().relationships() as relationship>
			final ${relationship.domainFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
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
