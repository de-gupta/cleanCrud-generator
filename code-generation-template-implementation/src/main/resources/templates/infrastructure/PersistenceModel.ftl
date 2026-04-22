<#-- Template for generating PersistenceModel interface -->
package ${aggregate().basePackage()}.infrastructure.persistence.model;

import ${aggregate().basePackage()}.domain.model.${aggregate().modelName()};
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilder;
import de.gupta.clean.crud.template.infrastructure.persistence.model.BasePersistenceModel;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
<#if persistence().imports()?has_content>
<#list persistence().imports() as import>
<#if import != "java.util.UUID" && import != "java.util.Optional" && import != "java.util.Collection">
import ${import};
</#if>
</#list>
</#if>

public interface ${aggregate().baseName()}PersistenceModel extends BasePersistenceModel<UUID>, ${aggregate().modelName()}<#if types().isGeneric()><<#list types().parameters() as type>${persistence().concreteType(type)}<#if type_has_next>, </#if></#list>></#if>
{
<#list composition().standaloneProperties() as property>
	${persistence().propertyType(property)} ${property.getter()}();
	void set${property.capitalizedName()}(final ${persistence().resolvedType(property.baseType())} ${property.name()});
</#list>
<#list composition().relationships() as relationship>
	${relationship.persistenceIdPropertyType()} ${relationship.persistenceIdPropertyName()}();
void set${relationship.propertyCapitalizedName()}Id(
		<#if relationship.many()>
		final Collection<${relationship.satelliteApiIdType()}> ${relationship.persistenceIdPropertyName()}
		<#elseif relationship.optional()>
		final ${relationship.satelliteApiIdType()} ${relationship.persistenceIdPropertyName()}
		<#else>
		final ${relationship.satelliteApiIdType()} ${relationship.persistenceIdPropertyName()}
		</#if>);
</#list>

	interface ${aggregate().baseName()}PersistenceModelBuilder extends ${aggregate().modelName()}Builder${"<"}<#if types().isGeneric()><#list types().parameters() as type>${persistence().concreteType(type)}<#if type_has_next>, </#if></#list>, </#if>${aggregate().baseName()}PersistenceModel, ${aggregate().baseName()}PersistenceModelBuilder${">"},
			ModelBuilder<${aggregate().baseName()}PersistenceModel>
	{
<#list composition().relationships() as relationship>
		${aggregate().baseName()}PersistenceModelBuilder with${relationship.propertyCapitalizedName()}Id(final ${relationship.persistenceIdPropertyType()} ${relationship.persistenceIdPropertyName()});
</#list>
	}
}
