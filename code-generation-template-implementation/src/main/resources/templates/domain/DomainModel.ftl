package ${basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.BaseDomainModel;

import java.util.Collection;
import java.util.Optional;
<#if domainModelImports()?has_content>
<#list domainModelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.Collection">
import ${import};
</#if>
</#list>
</#if>

public interface ${modelBaseName()}DomainModel extends
		BaseDomainModel, ${modelName()}<#if isGeneric()><<#list genericTypeParameters() as type>${domainConcreteType(type)}<#if type_has_next>, </#if></#list>></#if>
{
<#list relationships() as relationship>
	${relationship.responseFieldType()} ${relationship.propertyName()}();

</#list>
	interface ${modelBaseName()}DomainModelBuilder extends ${modelName()}Builder${"<"}<#if isGeneric()><#list genericTypeParameters() as type>${domainConcreteType(type)}<#if type_has_next>, </#if></#list>, </#if>${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelBuilder${">"}
	{
<#list relationships() as relationship>
		${modelBaseName()}DomainModelBuilder with${relationship.propertyCapitalizedName()}(final ${relationship.responseFieldType()} ${relationship.propertyName()});
</#list>
	}
}
