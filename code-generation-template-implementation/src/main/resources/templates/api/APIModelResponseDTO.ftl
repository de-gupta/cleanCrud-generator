<#-- Template for generating APIModelResponse DTO -->
package ${aggregate().basePackage()}.useCases.crud.common.dto;

import java.util.Optional;
<#if api().imports()?has_content>
<#list api().imports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${aggregate().baseName()}APIModelResponse(
Long id,
<#list composition().properties() as property>
    ${api().propertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
	public static ${aggregate().baseName()}APIModelResponse of(
			final long id, <#list composition().properties() as property>
			final ${api().propertyType(property)} ${property.name()}<#if property_has_next>, </#if></#list>)
	{
		return new ${aggregate().baseName()}APIModelResponse(
				id, <#list composition().properties() as property>${property.name()}<#if property_has_next>, </#if></#list>);
	}
}
