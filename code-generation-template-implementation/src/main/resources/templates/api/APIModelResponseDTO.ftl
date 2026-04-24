<#-- Template for generating APIModelResponse DTO -->
package ${aggregate().basePackage()}.useCases.crud.common.dto;

import java.util.Optional;
<#if api().responseImports()?has_content>
<#list api().responseImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${aggregate().baseName()}APIModelResponse(
${aggregate().rootApiIdType()} id,
<#list composition().properties() as property>
    ${api().propertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
	public static ${aggregate().baseName()}APIModelResponse of(
			final ${aggregate().rootApiIdType()} id, <#list composition().properties() as property>
			final ${api().propertyType(property)} ${property.name()}<#if property_has_next>, </#if></#list>)
	{
		return new ${aggregate().baseName()}APIModelResponse(
				id, <#list composition().properties() as property>${property.name()}<#if property_has_next>, </#if></#list>);
	}
}
