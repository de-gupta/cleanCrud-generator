package ${aggregate().basePackage()}.domain.model.dto;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;

import java.util.Optional;
<#if domain().imports()?has_content>
<#list domain().imports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${aggregate().baseName()}DomainModelResponse(
<#list composition().standaloneProperties() as property>
    ${domain().propertyType(property)} ${property.name()}<#if property_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#list composition().relationships() as relationship>
    ${relationship.responseFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>
)
{
	public static ${aggregate().baseName()}DomainModelResponse fromDomainModel(final ${aggregate().baseName()}DomainModel ${aggregate().beanNamePrefix()}DomainModel)
	{
		return new ${aggregate().baseName()}DomainModelResponse(
<#list composition().standaloneProperties() as property>                ${aggregate().beanNamePrefix()}DomainModel.${property.getter()}()<#if property_has_next || composition().relationships()?has_content>, </#if></#list><#list composition().relationships() as relationship>${aggregate().beanNamePrefix()}DomainModel.${relationship.propertyName()}()<#if relationship_has_next>, </#if></#list>);
	}
}
