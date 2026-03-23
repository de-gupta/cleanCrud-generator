<#-- Template for generating type converters between domain and API models -->
package ${basePackage}.useCases.crud.common.adapter.converter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.function.Function;

<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric && apiGenericImports?has_content>
<#list apiGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric>
<#list genericTypeParams() as param>
<#assign domainIndex = genericTypeParams()?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
@Component
@Qualifier("${modelName?uncap_first}${param}DomainToAPIConverter")
final class ${modelName}${param}DomainToAPIConverter implements Function<${domainConcreteTypes[domainIndex]}, ${apiConcreteTypes[domainIndex]}> {

    @Override
    public ${apiConcreteTypes[domainIndex]} apply(${domainConcreteTypes[domainIndex]} domainValue) {
        // TODO: Implement conversion logic from domain to API type
        <#if domainConcreteTypes[domainIndex] == apiConcreteTypes[domainIndex]>
        return domainValue; // Identity function for same types, but you can customize if needed
        <#else>
        return null; // Replace with actual conversion logic
        </#if>
    }
}

@Component
@Qualifier("${modelName?uncap_first}${param}APIToDomainConverter")
final class ${modelName}${param}APIToDomainConverter implements Function<${apiConcreteTypes[domainIndex]}, ${domainConcreteTypes[domainIndex]}> {

    @Override
    public ${domainConcreteTypes[domainIndex]} apply(${apiConcreteTypes[domainIndex]} apiValue) {
        // TODO: Implement conversion logic from API to domain type
        <#if domainConcreteTypes[domainIndex] == apiConcreteTypes[domainIndex]>
        return apiValue; // Identity function for same types, but you can customize if needed
        <#else>
        return null; // Replace with actual conversion logic
        </#if>
    }
}

</#if>
</#list>
</#if>