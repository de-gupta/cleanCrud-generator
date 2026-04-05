<#-- Template for enriching the user-provided base Model interface -->
package ${basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.ModelBuilder;
import de.gupta.clean.crud.template.domain.model.validation.Validatable;

import java.util.Optional;
<#if domainModelImports()?has_content>
<#list domainModelImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public interface ${modelName()}<#if isGeneric()><<#list genericTypeParameters() as param>${param}<#if param_has_next>, </#if></#list>></#if> extends Validatable
{
<#list properties() as property>
    ${property.returnType()} ${property.name()}();

</#list>
	@Override
	default void validate()
	{
	}

	interface ${modelName()}Builder${"<"}<#if isGeneric()><#list genericTypeParameters() as param>${param}<#if param_has_next>, </#if></#list>, </#if>M extends ${modelName()}<#if isGeneric()><<#list genericTypeParameters() as param>${param}<#if param_has_next>, </#if></#list>></#if>, B extends ${modelName()}Builder${"<"}<#if isGeneric()><#list genericTypeParameters() as param>${param}<#if param_has_next>, </#if></#list>, </#if>M, B${">"}${">"} extends ModelBuilder${"<"}M${">"}
	{
    <#list properties() as property>
		B with${property.capitalizedName()}(final ${baseBuilderPropertyType(property)} ${property.name()});
    </#list>
	}
}