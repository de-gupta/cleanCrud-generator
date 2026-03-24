package ${basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.BaseDomainModel;

public interface ${modelBaseName()}DomainModel extends
		BaseDomainModel, ${modelName()}<#if isGeneric()><<#list genericTypeParameters() as type>${domainConcreteType(type)}<#if type_has_next>, </#if></#list>></#if>
{
	interface ${modelBaseName()}DomainModelBuilder extends ${modelName()}.${modelName()}Builder${"<"}<#if isGeneric()><#list genericTypeParameters() as type>${domainConcreteType(type)}<#if type_has_next>, </#if></#list>, </#if>${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelBuilder${">"}
	{
	}
}