<#-- Template for generating DomainModelUpdatePatch DTO -->
package ${basePackage()}.domain.model.dto;

import java.util.Optional;
<#if domainModelImports()?has_content>
<#list domainModelImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelBaseName()}DomainModelUpdatePatch(
<#list properties() as property>
    Optional<${domainResolvedType(property.baseType())}> ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
	public static ${modelBaseName()}DomainModelUpdatePatch of(
<#list properties() as property>
		final Optional<${domainResolvedType(property.baseType())}> ${property.name()}<#if property_has_next>,</#if>
</#list>
	)
	{
		return new ${modelBaseName()}DomainModelUpdatePatch(<#list properties() as property>${property.name()}<#if property_has_next>, </#if></#list>);
	}
}
