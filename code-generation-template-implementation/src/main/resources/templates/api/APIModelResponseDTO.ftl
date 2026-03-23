<#-- Template for generating APIModelResponse DTO -->
package ${basePackage}.useCases.crud.common.dto;

import java.util.Optional;
<#if isGeneric && apiGenericImports?has_content>
<#list apiGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelName}APIModelResponse(
Long id,
<#list properties as property>
    <#if property.optional()>Optional<<#if genericTypeParams()?seq_contains(property.baseType())><#assign index = genericTypeParams()?seq_index_of(property.baseType())><#if index < apiConcreteTypes?size>${apiConcreteTypes[index]}<#else>${property.baseType()}</#if><#else>${property.baseType()}</#if>><#else><#if genericTypeParams()?seq_contains(property.type())><#assign index = genericTypeParams()?seq_index_of(property.type())><#if index < apiConcreteTypes?size>${apiConcreteTypes[index]}<#else>${property.type()}</#if><#else>${property.type()}</#if></#if> ${property.name()}<#if property_has_next>,</#if>
</#list>
)
{
	public static ${modelName}APIModelResponse of(
			final long id, <#list properties as property>
			final <#if property.optional()>Optional<<#if genericTypeParams()?seq_contains(property.baseType())><#assign index = genericTypeParams()?seq_index_of(property.baseType())><#if index < apiConcreteTypes?size>${apiConcreteTypes[index]}<#else>${property.baseType()}</#if><#else>${property.baseType()}</#if>><#else><#if genericTypeParams()?seq_contains(property.type())><#assign index = genericTypeParams()?seq_index_of(property.type())><#if index < apiConcreteTypes?size>${apiConcreteTypes[index]}<#else>${property.type()}</#if><#else>${property.type()}</#if></#if> ${property.name()}<#if property_has_next>, </#if></#list>)
{
	return new ${modelName}APIModelResponse(
			id, <#list properties as property>${property.name()}<#if property_has_next>, </#if></#list>);
}
}