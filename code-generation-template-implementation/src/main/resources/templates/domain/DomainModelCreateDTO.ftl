<#-- Template for generating DomainModelCreate DTO -->
package ${basePackage()}.domain.model.dto;

import java.util.Optional;
<#if domainModelImports()?has_content>
<#list domainModelImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelBaseName()}DomainModelCreate(
<#list properties() as property>
    ${domainPropertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
	public static ${modelBaseName()}DomainModelCreate of(
<#list properties() as property>
		final ${domainPropertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
	)
	{
		return new ${modelBaseName()}DomainModelCreate(<#list properties() as property>${property.name()}<#if property_has_next>, </#if></#list>);
	}
}
