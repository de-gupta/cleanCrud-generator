<#-- Template for generating PersistenceModel interface -->
package ${basePackage()}.infrastructure.persistence.model;

import ${basePackage()}.domain.model.${modelName()};
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilder;
import de.gupta.clean.crud.template.infrastructure.persistence.model.BasePersistenceModel;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
<#if persistenceModelImports()?has_content>
<#list persistenceModelImports() as import>
<#if import != "java.util.UUID" && import != "java.util.Optional" && import != "java.util.Collection">
import ${import};
</#if>
</#list>
</#if>

public interface ${modelBaseName()}PersistenceModel extends BasePersistenceModel<UUID>, ${modelName()}<#if isGeneric()><<#list genericTypeParameters() as type>${persistenceConcreteType(type)}<#if type_has_next>, </#if></#list>></#if>
{
<#list standaloneProperties() as property>
	${persistencePropertyType(property)} ${property.getter()}();
	void set${property.capitalizedName()}(final ${persistenceResolvedType(property.baseType())} ${property.name()});
</#list>
<#list relationships() as relationship>
	${relationship.persistenceIdPropertyType()} ${relationship.persistenceIdPropertyName()}();
void set${relationship.propertyCapitalizedName()}Id(
		<#if relationship.many()>
		final Collection<${relationship.satelliteDomainIdType()}> ${relationship.persistenceIdPropertyName()}
		<#elseif relationship.optional()>
		final ${relationship.satelliteDomainIdType()} ${relationship.persistenceIdPropertyName()}
		<#else>
		final ${relationship.satelliteDomainIdType()} ${relationship.persistenceIdPropertyName()}
		</#if>);
</#list>

	interface ${modelBaseName()}PersistenceModelBuilder extends ${modelName()}Builder${"<"}<#if isGeneric()><#list genericTypeParameters() as type>${persistenceConcreteType(type)}<#if type_has_next>, </#if></#list>, </#if>${modelBaseName()}PersistenceModel, ${modelBaseName()}PersistenceModelBuilder${">"},
			ModelBuilder<${modelBaseName()}PersistenceModel>
	{
<#list relationships() as relationship>
		${modelBaseName()}PersistenceModelBuilder with${relationship.propertyCapitalizedName()}Id(final ${relationship.persistenceIdPropertyType()} ${relationship.persistenceIdPropertyName()});
</#list>
	}
}