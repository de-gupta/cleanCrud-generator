<#-- Template for generating APIToDomainUpdateAdapter class -->
package ${aggregate().basePackage()}.useCases.crud.common.adapter;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelUpdatePatch;
<#list composition().relationships() as relationship>
import ${relationship.updatePatchImport()};
import ${relationship.domainUpdatePatchImport(aggregate().basePackage())};
</#list>
<#if types().apiDomainDifferingParameters()?has_content>
import ${aggregate().basePackage()}.useCases.crud.common.adapter.converter.*;
</#if>
<#if composition().relationships()?has_content>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
</#if>
<#if composition().relationships()?filter(relationship -> relationship.owned())?size gt 0>
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.standard.SatelliteUpdatePatchItem;
</#if>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
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

<#if types().isGeneric() && api().genericImports()?has_content>
<#list api().genericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Component
final class ${aggregate().baseName()}APIToDomainUpdateAdapter
		implements APIToDomainUpdateAdapter${"<"}${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}DomainModelUpdatePatch${">"}
{
<#list types().apiDomainDifferingParameters() as param>
	private final Function<${api().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}APIToDomainConverter;
</#list>
<#list composition().relationships() as relationship>
	<#if relationship.referenced()>
	private final APIDomainIDAdapter<${relationship.satelliteApiIdType()}, ${relationship.satelliteDomainIdType()}> ${relationship.relationshipVariablePrefix()}IdAdapter;
	<#else>
	private final APIToDomainUpdateAdapter<${relationship.updatePatchType()}, ${relationship.domainUpdatePatchType()}> ${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter;
	private final APIDomainIDAdapter<${relationship.satelliteApiIdType()}, ${relationship.satelliteDomainIdType()}> ${relationship.relationshipVariablePrefix()}IdAdapter;
	</#if>
</#list>

	@Override
	public ${aggregate().baseName()}DomainModelUpdatePatch mapToDomainModelUpdatePatch(final ${aggregate().baseName()}APIModelUpdatePatch apiModel)
	{
		return new ${aggregate().baseName()}DomainModelUpdatePatch(
<#list composition().standaloneProperties() as property>
<#if types().apiDomainDifferingParameters()?seq_contains(property.baseType())>
			apiModel.${property.name()}().map(${property.baseType()?lower_case}APIToDomainConverter)<#if property_has_next || composition().relationships()?has_content>,</#if>
<#else>
			apiModel.${property.name()}()<#if property_has_next || composition().relationships()?has_content>,</#if>
</#if>
</#list>
<#list composition().relationships() as relationship>
			<#if relationship.referenced()>
			<#if relationship.many()>
			apiModel.${relationship.propertyName()}().map(${relationship.propertyName()} -> ${relationship.propertyName()}.stream().map(${relationship.relationshipVariablePrefix()}IdAdapter::mapToDomainID).toList())
			<#else>
			apiModel.${relationship.propertyName()}().map(${relationship.relationshipVariablePrefix()}IdAdapter::mapToDomainID)
			</#if>,
			apiModel.${relationship.removeFieldName()}().stream().map(${relationship.relationshipVariablePrefix()}IdAdapter::mapToDomainID).toList()
			<#else>
			<#if relationship.many()>
			apiModel.${relationship.propertyName()}().map(${relationship.propertyName()} -> ${relationship.propertyName()}.stream().map(item -> new SatelliteUpdatePatchItem<${relationship.satelliteDomainIdType()}, ${relationship.domainUpdatePatchType()}>(
					item.id().map(${relationship.relationshipVariablePrefix()}IdAdapter::mapToDomainID),
					${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter.mapToDomainModelUpdatePatch(item.patch()))).toList())
			<#else>
			apiModel.${relationship.propertyName()}().map(${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter::mapToDomainModelUpdatePatch)
			</#if>,
			apiModel.${relationship.removeFieldName()}().stream().map(${relationship.relationshipVariablePrefix()}IdAdapter::mapToDomainID).toList()
			</#if><#if relationship_has_next>,</#if>
</#list>
		);
	}

	${aggregate().baseName()}APIToDomainUpdateAdapter(<#list types().apiDomainDifferingParameters() as param>
			@Qualifier("${aggregate().beanNamePrefix()}${param}APIToDomainConverter") final Function<${api().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}APIToDomainConverter<#if param_has_next>,</#if>
</#list><#if types().apiDomainDifferingParameters()?has_content && composition().relationships()?has_content>,</#if>
<#list composition().relationships() as relationship>
			<#if relationship.referenced()>
			final APIDomainIDAdapter<${relationship.satelliteApiIdType()}, ${relationship.satelliteDomainIdType()}> ${relationship.relationshipVariablePrefix()}IdAdapter
			<#else>
			@Qualifier("${relationship.satelliteQualifierPrefix()}APIToDomainUpdateAdapter") final APIToDomainUpdateAdapter<${relationship.updatePatchType()}, ${relationship.domainUpdatePatchType()}> ${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter,
			final APIDomainIDAdapter<${relationship.satelliteApiIdType()}, ${relationship.satelliteDomainIdType()}> ${relationship.relationshipVariablePrefix()}IdAdapter
			</#if><#if relationship_has_next>,</#if>
</#list>)
	{
<#list types().apiDomainDifferingParameters() as param>
		this.${param?lower_case}APIToDomainConverter = ${param?lower_case}APIToDomainConverter;
</#list>
<#list composition().relationships() as relationship>
		<#if relationship.referenced()>
		this.${relationship.relationshipVariablePrefix()}IdAdapter = ${relationship.relationshipVariablePrefix()}IdAdapter;
		<#else>
		this.${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter = ${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter;
		this.${relationship.relationshipVariablePrefix()}IdAdapter = ${relationship.relationshipVariablePrefix()}IdAdapter;
		</#if>
</#list>
	}
}
