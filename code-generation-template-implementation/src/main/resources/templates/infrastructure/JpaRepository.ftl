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
	boolean existsBy${property.capitalizedName()}(final ${persistenceResolvedType(property.baseType())} ${property.name()});

	@Query("SELECT t.${property.name()} FROM ${modelBaseName()}PersistenceModelImpl t WHERE t.${property.name()} IN :${property.name()}s")
	List<${persistenceResolvedType(property.baseType())}> find${property.capitalizedName()}sBy${property.capitalizedName()}In(@Param("${property.name()}s") final Collection<${persistenceResolvedType(property.baseType())}> ${property.name()}s);

</#list>
}