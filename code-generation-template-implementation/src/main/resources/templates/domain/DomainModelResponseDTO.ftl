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
<#list properties() as property>
    ${domainPropertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
	public static ${modelBaseName()}DomainModelResponse fromDomainModel(final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel)
	{
		return new ${modelBaseName()}DomainModelResponse(
<#list properties() as property>                ${beanNamePrefix()}DomainModel.${property.getter()}()<#if property_has_next>, </#if></#list>);
	}
}
