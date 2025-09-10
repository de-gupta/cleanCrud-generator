<#-- Template for generating APIModelCreate DTO -->
package ${basePackage}.useCases.crud.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
<#if isGeneric && apiGenericImports?has_content>
<#list apiGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelName}APIModelCreate(
<#list properties as property>
    <#if !property.optional>
        <#if property.baseType == "String">
            @NotBlank(message = "${property.capitalizedName} is required")
        <#else>
        @NotNull(message = "${property.capitalizedName} is required")
        </#if>
    </#if>
    <#if property.optional>Optional<<#if genericTypeParams?seq_contains(property.baseType)><#assign index = genericTypeParams?seq_index_of(property.baseType)><#if index < apiConcreteTypes?size>${apiConcreteTypes[index]}<#else>${property.baseType}</#if><#else>${property.baseType}</#if>><#else><#if genericTypeParams?seq_contains(property.type)><#assign index = genericTypeParams?seq_index_of(property.type)><#if index < apiConcreteTypes?size>${apiConcreteTypes[index]}<#else>${property.type}</#if><#else>${property.type}</#if></#if> ${property.name}<#if property_has_next>,</#if>
</#list>
)
{
}