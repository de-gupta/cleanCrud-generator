<#-- Template for generating APIModelUpdatePatch DTO -->
package ${basePackage}.useCases.crud.common.dto;

import java.util.Optional;
<#if isGeneric && apiGenericImports?has_content>
<#list apiGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelName}APIModelUpdatePatch(
<#list properties as property>
    Optional<<#if genericTypeParams?seq_contains(property.baseType)><#assign index = genericTypeParams?seq_index_of(property.baseType)><#if index < apiConcreteTypes?size>${apiConcreteTypes[index]}<#else>${property.baseType}</#if><#else>${property.baseType}</#if>> ${property.name}<#if property_has_next>,</#if>
</#list>
)
{
}