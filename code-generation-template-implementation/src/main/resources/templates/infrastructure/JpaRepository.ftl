<#-- Template for generating JpaRepository interface -->
package ${aggregate().basePackage()}.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
<#list persistence().imports() as importName>
<#if importName != "java.util.Optional" && importName != "java.util.UUID">
import ${importName};
</#if>
</#list>

@Repository
public interface ${aggregate().baseName()}JpaRepository extends JpaRepository<${aggregate().baseName()}PersistenceModelImpl, UUID>
{
<#list composition().standaloneProperties() as property>
<#if !persistence().requiresJpaConverter(property)>
	boolean existsBy${property.capitalizedName()}(final ${persistence().resolvedType(property.baseType())} ${property.name()});

	@Query("SELECT t.${property.name()} FROM ${aggregate().baseName()}PersistenceModelImpl t WHERE t.${property.name()} IN :${property.name()}s")
	List<${persistence().boxedResolvedType(property.baseType())}> find${property.capitalizedName()}sBy${property.capitalizedName()}In(@Param("${property.name()}s") final Collection<${persistence().boxedResolvedType(property.baseType())}> ${property.name()}s);

</#if>
</#list>
<#list composition().relationships() as relationship>
<#if !relationship.many()>
	boolean existsBy${relationship.persistenceIdPropertyName()?cap_first}(final ${relationship.satellitePersistenceIdType()} ${relationship.persistenceIdPropertyName()});

	@Query("SELECT t.${relationship.persistenceIdPropertyName()} FROM ${aggregate().baseName()}PersistenceModelImpl t WHERE t.${relationship.persistenceIdPropertyName()} IN :${relationship.persistenceIdPropertyName()}s")
	List<${relationship.satellitePersistenceIdType()}> find${relationship.persistenceIdPropertyName()?cap_first}sBy${relationship.persistenceIdPropertyName()?cap_first}In(@Param("${relationship.persistenceIdPropertyName()}s") final Collection<${relationship.satellitePersistenceIdType()}> ${relationship.persistenceIdPropertyName()}s);

</#if>
</#list>
}
