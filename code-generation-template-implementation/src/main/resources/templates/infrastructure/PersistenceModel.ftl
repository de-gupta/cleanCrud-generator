<#-- Template for generating PersistenceModel interface -->
package ${basePackage()}.infrastructure.persistence.model;

import ${basePackage()}.domain.model.${modelName()};
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilder;
import de.gupta.clean.crud.template.infrastructure.persistence.model.BasePersistenceModel;

import java.util.UUID;
<#if persistenceModelImports()?has_content>
<#list persistenceModelImports() as import>
<#if import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

public interface ${modelBaseName()}PersistenceModel extends
		BasePersistenceModel<UUID>, ${modelName()}<#if isGeneric()><<#list genericTypeParameters() as type>${persistenceConcreteType(type)}<#if type_has_next>, </#if></#list>></#if>
{
<#list properties() as property>
	void set${property.capitalizedName()}(${persistenceResolvedType(property.baseType())} ${property.name()});
</#list>

	interface ${modelBaseName()}PersistenceModelBuilder
			extends ${modelName()}.${modelName()}Builder<<#if isGeneric()><#list genericTypeParameters() as type>${persistenceConcreteType(type)}<#if type_has_next>, </#if></#list>, </#if>${modelBaseName()}PersistenceModel, ${modelBaseName()}PersistenceModelBuilder>,
			ModelBuilder<${modelBaseName()}PersistenceModel>
	{
	}
}