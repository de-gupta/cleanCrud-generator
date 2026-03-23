<#-- Template for generating JpaRepository interface -->
package ${basePackage}.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Repository
public interface ${modelName}JpaRepository extends JpaRepository<${modelName}PersistenceModelImpl, UUID>
{
<#list properties as property>
    <#assign propertyParamName = property.name()>
    <#assign capitalizedPropertyName = property.capitalizedName()>
    <#assign propertyFieldName = property.name()>
    <#if property.name() == "user">
        <#assign propertyParamName = "userID">
        <#assign capitalizedPropertyName = "UserID">
        <#assign propertyFieldName = "userID">
    </#if>
    <#if genericTypeParams()?seq_contains(property.baseType())>
        <#assign index = genericTypeParams()?seq_index_of(property.baseType())>
        <#if index < persistenceConcreteTypes?size>
        boolean existsBy${capitalizedPropertyName}(final ${persistenceConcreteTypes[index]} ${propertyParamName});

        @Query("SELECT t.${propertyFieldName} FROM ${modelName}PersistenceModelImpl t WHERE t.${propertyFieldName} IN :${propertyParamName}s")
        List<${persistenceConcreteTypes[index]}> find${capitalizedPropertyName}sBy${capitalizedPropertyName}In(@Param("${propertyParamName}s") final Collection<${persistenceConcreteTypes[index]}> ${propertyParamName}s);
        <#else>

        boolean existsBy${capitalizedPropertyName}(final ${property.baseType()} ${propertyParamName});

        @Query("SELECT t.${propertyFieldName} FROM ${modelName}PersistenceModelImpl t WHERE t.${propertyFieldName} IN :${propertyParamName}s")
        List<${property.baseType()}> find${capitalizedPropertyName}sBy${capitalizedPropertyName}In(@Param("${propertyParamName}s") final Collection<${property.type()}> ${propertyParamName}s);

        </#if>
    <#else>
        boolean existsBy${capitalizedPropertyName}(final ${property.baseType()} ${propertyParamName});

        @Query("SELECT t.${propertyFieldName} FROM ${modelName}PersistenceModelImpl t WHERE t.${propertyFieldName} IN :${propertyParamName}s")
        List<${property.baseType()}> find${capitalizedPropertyName}sBy${capitalizedPropertyName}In(@Param("${propertyParamName}s") final Collection<${property.type()}> ${propertyParamName}s);
    </#if>
</#list>
}