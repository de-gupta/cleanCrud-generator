package ${aggregate().basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.BaseDomainModel;

import java.util.Collection;
import java.util.Optional;
<#if domain().imports()?has_content>
<#list domain().imports() as import>
<#if import != "java.util.Optional" && import != "java.util.Collection">
import ${import};
</#if>
</#list>
</#if>

public interface ${aggregate().baseName()}DomainModel extends
		BaseDomainModel, ${aggregate().modelName()}<#if types().isGeneric()><<#list types().parameters() as type>${domain().concreteType(type)}<#if type_has_next>, </#if></#list>></#if>
{
<#list composition().relationships() as relationship>
	${relationship.responseFieldType()} ${relationship.propertyName()}();

</#list>
	interface ${aggregate().baseName()}DomainModelBuilder extends ${aggregate().modelName()}Builder${"<"}<#if types().isGeneric()><#list types().parameters() as type>${domain().concreteType(type)}<#if type_has_next>, </#if></#list>, </#if>${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelBuilder${">"}
	{
<#list composition().relationships() as relationship>
		${aggregate().baseName()}DomainModelBuilder with${relationship.propertyCapitalizedName()}(final ${relationship.responseFieldType()} ${relationship.propertyName()});
</#list>
	}
}
