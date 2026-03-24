<#-- Template for generating JpaRepository interface -->
package ${basePackage()}.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
<#if isGeneric() && persistenceGenericImports()?has_content>
<#list persistenceGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Repository
public interface ${modelBaseName()}JpaRepository extends JpaRepository<${modelBaseName()}PersistenceModelImpl, UUID>
{
<#list properties() as property>
	<#assign propertyParamName = property.name()>
	<#assign capitalizedPropertyName = property.capitalizedName()>
	<#assign propertyFieldName = property.name()>
	<#if property.name() == "user">
		<#assign propertyParamName = "userID">
		<#assign capitalizedPropertyName = "UserID">
		<#assign propertyFieldName = "userID">
	</#if>
	boolean existsBy${capitalizedPropertyName}(final ${persistenceResolvedType(property.baseType())} ${propertyParamName});

	@Query("SELECT t.${propertyFieldName} FROM ${modelBaseName()}PersistenceModelImpl t WHERE t.${propertyFieldName} IN :${propertyParamName}s")
	List<${persistenceResolvedType(property.baseType())}> find${capitalizedPropertyName}sBy${capitalizedPropertyName}In(@Param("${propertyParamName}s") final Collection<${persistenceResolvedType(property.baseType())}> ${propertyParamName}s);

</#list>
}