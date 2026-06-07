<#-- Template for generating PersistenceModel interface -->
package ${aggregate().basePackage()}.infrastructure.persistence.model;

import ${aggregate().basePackage()}.domain.model.${aggregate().modelName()};
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilder;
import de.gupta.clean.crud.template.infrastructure.persistence.model.BasePersistenceModel;

import java.util.Collection;
<#if persistence().interfaceImports()?has_content>
<#list persistence().interfaceImports() as import>
<#if import != "java.util.Collection">
import ${import};
</#if>
</#list>
</#if>

public interface ${aggregate().baseName()}PersistenceModel extends BasePersistenceModel<${aggregate().rootPersistenceIdType()}>, ${aggregate().modelName()}<#if types().isGeneric()><<#list types().parameters() as type>${persistence().concreteType(type)}<#if type_has_next>, </#if></#list>></#if>
{
<#list composition().standaloneProperties() as property>
	void set${property.capitalizedName()}(final ${persistence().resolvedType(property.baseType())} ${property.name()});
</#list>
<#list composition().relationships() as relationship>
	void set${relationship.propertyCapitalizedName()}(final <#if relationship.many()>${relationship.persistenceIdPropertyType()}<#else>${relationship.satellitePersistenceIdSimpleType()}</#if> ${relationship.persistenceIdPropertyName()});
</#list>

	interface ${aggregate().baseName()}PersistenceModelBuilder
			extends ${aggregate().modelName()}Builder${"<"}<#if types().isGeneric()><#list types().parameters() as type>${persistence().concreteType(type)}<#if type_has_next>, </#if></#list>, </#if>${aggregate().baseName()}PersistenceModel, ${aggregate().baseName()}PersistenceModelBuilder${">"},
			ModelBuilder<${aggregate().baseName()}PersistenceModel>
	{
	}
}
