<#-- Template for generating type converters between domain and persistence models -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.model.converter;

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

<#if isGeneric && persistenceGenericImports?has_content>
<#list persistenceGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric>
<#list genericTypeParams() as param>
<#assign domainIndex = genericTypeParams()?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < persistenceConcreteTypes?size>
@Component
@Qualifier("${modelName?uncap_first}${param}DomainToPersistenceConverter")
final class ${modelName}${param}DomainToPersistenceConverter implements Function<${domainConcreteTypes[domainIndex]}, ${persistenceConcreteTypes[domainIndex]}> {

    @Override
    public ${persistenceConcreteTypes[domainIndex]} apply(${domainConcreteTypes[domainIndex]} domainValue) {
        // TODO: Implement conversion logic from domain to persistence type
        <#if domainConcreteTypes[domainIndex] == persistenceConcreteTypes[domainIndex]>
        return domainValue; // Identity function for same types, but you can customize if needed
        <#else>
        return null; // Replace with actual conversion logic
        </#if>
    }
}

@Component
@Qualifier("${modelName?uncap_first}${param}PersistenceToDomainConverter")
final class ${modelName}${param}PersistenceToDomainConverter implements Function<${persistenceConcreteTypes[domainIndex]}, ${domainConcreteTypes[domainIndex]}> {

    @Override
    public ${domainConcreteTypes[domainIndex]} apply(${persistenceConcreteTypes[domainIndex]} persistenceValue) {
        // TODO: Implement conversion logic from persistence to domain type
        <#if domainConcreteTypes[domainIndex] == persistenceConcreteTypes[domainIndex]>
        return persistenceValue; // Identity function for same types, but you can customize if needed
        <#else>
        return null; // Replace with actual conversion logic
        </#if>
    }
}

</#if>
</#list>
</#if>