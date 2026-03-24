<#-- Template for generating APIModelUpdatePatch DTO -->
package ${basePackage()}.useCases.crud.common.dto;

import java.util.Optional;
<#if isGeneric() && apiGenericImports()?has_content>
<#list apiGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelBaseName()}APIModelUpdatePatch(
<#list properties() as property>
    Optional<${apiResolvedType(property.baseType())}> ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
}