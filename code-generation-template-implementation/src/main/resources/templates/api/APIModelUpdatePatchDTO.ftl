<#-- Template for generating APIModelUpdatePatch DTO -->
package ${basePackage()}.useCases.crud.common.dto;

import java.util.Optional;
<#if apiModelImports()?has_content>
<#list apiModelImports() as import>
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
