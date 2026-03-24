<#-- Template for generating APIModelResponse DTO -->
package ${basePackage()}.useCases.crud.common.dto;

import java.util.Optional;
<#if apiModelImports()?has_content>
<#list apiModelImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelBaseName()}APIModelResponse(
Long id,
<#list properties() as property>
    ${apiPropertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
	public static ${modelBaseName()}APIModelResponse of(
			final long id, <#list properties() as property>
			final ${apiPropertyType(property)} ${property.name()}<#if property_has_next>, </#if></#list>)
	{
		return new ${modelBaseName()}APIModelResponse(
				id, <#list properties() as property>${property.name()}<#if property_has_next>, </#if></#list>);
	}
}
