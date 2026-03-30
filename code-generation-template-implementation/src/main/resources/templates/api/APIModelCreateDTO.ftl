<#-- Template for generating APIModelCreate DTO -->
package ${basePackage()}.useCases.crud.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
<#if apiModelImports()?has_content>
<#list apiModelImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelBaseName()}APIModelCreate(
<#list properties() as property>
    <#if !property.optional()>
        <#if property.baseType() == "String">
            @NotBlank(message = "${property.capitalizedName()} is required")
        <#else>
            @NotNull(message = "${property.capitalizedName()} is required")
        </#if>
    </#if>
    ${apiPropertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
}