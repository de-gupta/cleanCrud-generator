package ${aggregate().basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.BaseDomainModel;

<#if domain().modelImports()?has_content>
<#list domain().modelImports() as import>
import ${import};
</#list>
</#if>

public interface ${aggregate().baseName()}DomainModel extends
		BaseDomainModel, ${aggregate().modelName()}<#if types().isGeneric()><<#list types().parameters() as type>${domain().concreteType(type)}<#if type_has_next>, </#if></#list>></#if>
{
	interface ${aggregate().baseName()}DomainModelBuilder extends ${aggregate().modelName()}Builder${"<"}<#if types().isGeneric()><#list types().parameters() as type>${domain().concreteType(type)}<#if type_has_next>, </#if></#list>, </#if>${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelBuilder${">"}
	{
	}
}
