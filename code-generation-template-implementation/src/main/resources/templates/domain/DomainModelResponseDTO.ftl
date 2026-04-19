package ${basePackage()}.domain.model.dto;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;

import java.util.Optional;
<#if domainModelImports()?has_content>
<#list domainModelImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelBaseName()}DomainModelResponse(
<#list standaloneProperties() as property>
    ${domainPropertyType(property)} ${property.name()}<#if property_has_next || relationships()?has_content>,</#if>
</#list>
<#list relationships() as relationship>
    ${relationship.responseFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>
)
{
	public static ${modelBaseName()}DomainModelResponse fromDomainModel(final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel)
	{
		return new ${modelBaseName()}DomainModelResponse(
<#list standaloneProperties() as property>                ${beanNamePrefix()}DomainModel.${property.getter()}()<#if property_has_next || relationships()?has_content>, </#if></#list><#list relationships() as relationship>${beanNamePrefix()}DomainModel.${relationship.propertyName()}()<#if relationship_has_next>, </#if></#list>);
	}
}
