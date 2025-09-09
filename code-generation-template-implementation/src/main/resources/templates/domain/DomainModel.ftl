<#-- Template for generating DomainModel interface -->
package ${basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.BaseDomainModel;
<#if isGeneric() && domainGenericImports()?has_content>
<#list domainGenericImports() as import>
import ${import};
</#list>
</#if>

public interface ${modelName()}DomainModel extends
		BaseDomainModel, ${modelName()}Model<#if isGeneric()><<#list genericTypeParameters() as type>${domainConcreteTypes()[type]}<#if type_has_next>, </#if></#list>></#if>
{
	interface ${modelName()}DomainModelBuilder extends ${modelName()}Model.${modelName()}ModelBuilder${"<"}<#if isGeneric()><#list genericTypeParameters() as type>${domainConcreteTypes()[type]}<#if type_has_next>, </#if></#list>, </#if>${modelName()}DomainModel, ${modelName()}DomainModelBuilder${">"}
{
}
}