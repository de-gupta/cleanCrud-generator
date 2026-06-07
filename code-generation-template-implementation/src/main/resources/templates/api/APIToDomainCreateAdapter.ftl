<#-- Template for generating APIToDomainCreateAdapter class -->
package ${aggregate().basePackage()}.useCases.crud.common.adapter;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelCreate;
<#list composition().relationships() as relationship>
import ${relationship.createImport()};
import ${relationship.domainCreateImport(aggregate().basePackage())};
</#list>
<#if types().apiDomainDifferingParameters()?has_content>
import ${aggregate().basePackage()}.useCases.crud.common.adapter.converter.*;
</#if>
<#list composition().relationships() as relationship>
<#if relationship.referenced()>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
</#if>
</#list>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
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
final class ${aggregate().baseName()}APIToDomainCreateAdapter implements APIToDomainCreateAdapter${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}DomainModelCreate${">"}
{
<#list types().apiDomainDifferingParameters() as param>
	private final Function<${api().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}APIToDomainConverter;
</#list>
<#list composition().relationships() as relationship>
	<#if relationship.referenced()>
	private final APIDomainIDAdapter<${relationship.satelliteApiIdType()}, ${relationship.satelliteDomainIdType()}> ${relationship.relationshipVariablePrefix()}IdAdapter;
	<#else>
	private final APIToDomainCreateAdapter<${relationship.createType()}, ${relationship.domainCreateType()}> ${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter;
	</#if>
</#list>

	@Override
	public ${aggregate().baseName()}DomainModelCreate mapToDomainModelCreate(final ${aggregate().baseName()}APIModelCreate apiModel)
	{
		return new ${aggregate().baseName()}DomainModelCreate(
<#list composition().standaloneProperties() as property>
<#if types().apiDomainDifferingParameters()?seq_contains(property.baseType())>
			<#if property.optional()>
			apiModel.${property.getter()}().map(${property.baseType()?lower_case}APIToDomainConverter)<#if property_has_next || composition().relationships()?has_content>,</#if>
			<#else>
			${property.baseType()?lower_case}APIToDomainConverter.apply(apiModel.${property.getter()}())<#if property_has_next || composition().relationships()?has_content>,</#if>
			</#if>
<#else>
			apiModel.${property.getter()}()<#if property_has_next || composition().relationships()?has_content>,</#if>
</#if>
</#list>
<#list composition().relationships() as relationship>
			<#if relationship.referenced()>
			<#if relationship.many()>
			apiModel.${relationship.propertyName()}().stream().map(${relationship.relationshipVariablePrefix()}IdAdapter::mapToDomainID).toList()
			<#elseif relationship.optional()>
			apiModel.${relationship.propertyName()}().map(${relationship.relationshipVariablePrefix()}IdAdapter::mapToDomainID)
			<#else>
			${relationship.relationshipVariablePrefix()}IdAdapter.mapToDomainID(apiModel.${relationship.propertyName()}())
			</#if>
			<#else>
			<#if relationship.many()>
			apiModel.${relationship.propertyName()}().stream().map(${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter::mapToDomainModelCreate).toList()
			<#elseif relationship.optional()>
			apiModel.${relationship.propertyName()}().map(${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter::mapToDomainModelCreate)
			<#else>
			${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter.mapToDomainModelCreate(apiModel.${relationship.propertyName()}())
			</#if>
			</#if><#if relationship_has_next>,</#if>
</#list>
		);
	}

	${aggregate().baseName()}APIToDomainCreateAdapter(<#list types().apiDomainDifferingParameters() as param>
			@Qualifier("${aggregate().beanNamePrefix()}${param}APIToDomainConverter") final Function<${api().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}APIToDomainConverter<#if param_has_next>,</#if>
</#list><#if types().apiDomainDifferingParameters()?has_content && composition().relationships()?has_content>,</#if>
<#list composition().relationships() as relationship>
			<#if relationship.referenced()>
			final APIDomainIDAdapter<${relationship.satelliteApiIdType()}, ${relationship.satelliteDomainIdType()}> ${relationship.relationshipVariablePrefix()}IdAdapter
			<#else>
			@Qualifier("${relationship.satelliteQualifierPrefix()}APIToDomainCreateAdapter") final APIToDomainCreateAdapter<${relationship.createType()}, ${relationship.domainCreateType()}> ${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter
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
		this.${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter = ${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter;
		</#if>
</#list>
	}
}

