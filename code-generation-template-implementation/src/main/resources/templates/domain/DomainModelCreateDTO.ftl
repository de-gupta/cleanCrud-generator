<#-- Template for generating DomainModelCreate DTO -->
package ${basePackage}.domain.model.dto;

import java.util.Optional;
<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelName}DomainModelCreate(
<#list properties as property>
    <#if property.optional>
        <#if genericTypeParams?seq_contains(property.baseType)>
            <#assign index = genericTypeParams?seq_index_of(property.baseType)>
            <#if index < domainConcreteTypes?size>
    Optional<${domainConcreteTypes[index]}> ${property.name}<#if property_has_next>,</#if>
            <#else>
    Optional<${property.baseType}> ${property.name}<#if property_has_next>,</#if>
            </#if>
        <#else>
    Optional<${property.baseType}> ${property.name}<#if property_has_next>,</#if>
        </#if>
    <#else>
        <#if genericTypeParams?seq_contains(property.type)>
            <#assign index = genericTypeParams?seq_index_of(property.type)>
            <#if index < domainConcreteTypes?size>
    ${domainConcreteTypes[index]} ${property.name}<#if property_has_next>,</#if>
            <#else>
    ${property.type} ${property.name}<#if property_has_next>,</#if>
            </#if>
        <#else>
    ${property.type} ${property.name}<#if property_has_next>,</#if>
        </#if>
    </#if>
</#list>
)
{
	public static ${modelName}DomainModelCreate of(
<#list properties as property>
    <#if property.optional>
        <#if genericTypeParams?seq_contains(property.baseType)>
            <#assign index = genericTypeParams?seq_index_of(property.baseType)>
            <#if index < domainConcreteTypes?size>
    Optional<${domainConcreteTypes[index]}> ${property.name}<#if property_has_next>,</#if>
            <#else>
    Optional<${property.baseType}> ${property.name}<#if property_has_next>,</#if>
            </#if>
        <#else>
    Optional<${property.baseType}> ${property.name}<#if property_has_next>,</#if>
        </#if>
    <#else>
        <#if genericTypeParams?seq_contains(property.type)>
            <#assign index = genericTypeParams?seq_index_of(property.type)>
            <#if index < domainConcreteTypes?size>
    ${domainConcreteTypes[index]} ${property.name}<#if property_has_next>,</#if>
            <#else>
    ${property.type} ${property.name}<#if property_has_next>,</#if>
            </#if>
        <#else>
    ${property.type} ${property.name}<#if property_has_next>,</#if>
        </#if>
    </#if>
</#list>
			)
{
	return new ${modelName}DomainModelCreate(<#list properties as property>${property.name}<#if property_has_next>, </#if></#list>);
}
}