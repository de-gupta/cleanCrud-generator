<#-- Template for generating PersistenceModel interface -->
package ${basePackage}.infrastructure.persistence.model;

import ${basePackage}.domain.model.${modelName}Model;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilder;
import de.gupta.clean.crud.template.infrastructure.persistence.model.BasePersistenceModel;

import java.util.UUID;
<#if isGeneric && persistenceGenericImports?has_content>
<#list persistenceGenericImports as import>
<#if import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

public interface ${modelName}PersistenceModel extends
		BasePersistenceModel${"<"}UUID${">"},  ${modelName}Model<#if isGeneric><<#list persistenceConcreteTypes as type>${type}<#if type_has_next>, </#if></#list>></#if>
{
<#list properties as property>
    void set${property.capitalizedName}(<#if genericTypeParams?seq_contains(property.baseType)><#assign index = genericTypeParams?seq_index_of(property.baseType)><#if index < persistenceConcreteTypes?size>${persistenceConcreteTypes[index]}<#else>${property.baseType}</#if><#else>${property.baseType}</#if> ${property.name});
</#list>

	interface ${modelName}PersistenceModelBuilder
			extends ${modelName}Model.${modelName}ModelBuilder${"<"}<#if isGeneric><#list persistenceConcreteTypes as type>${type}<#if type_has_next>, </#if></#list>, </#if>${modelName}PersistenceModel, ${modelName}PersistenceModelBuilder${">"},
			ModelBuilder${"<"}${modelName}PersistenceModel${">"}
{
}
}