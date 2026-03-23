<#-- Template for generating DomainModelUpdatePatch DTO -->
package ${basePackage}.domain.model.dto;

import java.util.Optional;
<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelName}DomainModelUpdatePatch(
<#list properties as property>
    <#if genericTypeParams()?seq_contains(property.baseType())>
        <#assign index = genericTypeParams()?seq_index_of(property.baseType())>
        <#if index < domainConcreteTypes?size>
    Optional<${domainConcreteTypes[index]}> ${property.name()}<#if property_has_next>,</#if>
        <#else>
    Optional<${property.baseType()}> ${property.name()}<#if property_has_next>,</#if>
        </#if>
    <#else>
    Optional<${property.baseType()}> ${property.name()}<#if property_has_next>,</#if>
    </#if>
</#list>
)
{
	public static ${modelName}DomainModelUpdatePatch of(
<#list properties as property>
    <#if genericTypeParams()?seq_contains(property.baseType())>
        <#assign index = genericTypeParams()?seq_index_of(property.baseType())>
        <#if index < domainConcreteTypes?size>
		final Optional<${domainConcreteTypes[index]}> ${property.name()}<#if property_has_next>,</#if>
        <#else>
		final Optional<${property.baseType()}> ${property.name()}<#if property_has_next>,</#if>
        </#if>
    <#else>
		final Optional<${property.baseType()}> ${property.name()}<#if property_has_next>,</#if>
    </#if>
</#list>
	)
	{
		return new ${modelName}DomainModelUpdatePatch(<#list properties as property>${property.name()}<#if property_has_next>, </#if></#list>);
	}
}