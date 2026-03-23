package ${basePackage}.domain.model.dto;

import ${basePackage}.domain.model.${modelName}DomainModel;

import java.util.Optional;
<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public record ${modelName}DomainModelResponse(
<#list properties as property>
    <#if property.optional()>
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
    <#else>
        <#if genericTypeParams()?seq_contains(property.type())>
            <#assign index = genericTypeParams()?seq_index_of(property.type())>
            <#if index < domainConcreteTypes?size>
    ${domainConcreteTypes[index]} ${property.name()}<#if property_has_next>,</#if>
            <#else>
    ${property.type()} ${property.name()}<#if property_has_next>,</#if>
            </#if>
        <#else>
    ${property.type()} ${property.name()}<#if property_has_next>,</#if>
        </#if>
    </#if>
</#list>
)
{
	public static ${modelName}DomainModelResponse fromDomainModel(final ${modelName}DomainModel ${modelName?uncap_first}DomainModel)
{
		return new ${modelName}DomainModelResponse(
<#list properties as property>${modelName?uncap_first}DomainModel.${property.getter()}()<#if property_has_next>, </#if></#list>);
}
}