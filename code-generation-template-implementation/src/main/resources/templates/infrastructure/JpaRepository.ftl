<#-- Template for generating JpaRepository interface -->
package ${basePackage()}.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
<#list propertyImports() as importName>
<#if importName != "java.util.Optional" && importName != "java.util.UUID">
import ${importName};
</#if>
</#list>

@Repository
public interface ${modelBaseName()}JpaRepository extends JpaRepository<${modelBaseName()}PersistenceModelImpl, UUID>
{
<#list properties() as property>
<#if !requiresJpaConverter(property)>
	boolean existsBy${property.capitalizedName()}(final ${persistenceResolvedType(property.baseType())} ${property.name()});

	@Query("SELECT t.${property.name()} FROM ${modelBaseName()}PersistenceModelImpl t WHERE t.${property.name()} IN :${property.name()}s")
	List<${boxedPersistenceResolvedType(property.baseType())}> find${property.capitalizedName()}sBy${property.capitalizedName()}In(@Param("${property.name()}s") final Collection<${boxedPersistenceResolvedType(property.baseType())}> ${property.name()}s);

</#if>
</#list>
}
