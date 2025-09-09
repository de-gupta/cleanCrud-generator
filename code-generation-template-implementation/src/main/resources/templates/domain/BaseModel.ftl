<#-- Template for generating base Model interface with boilerplate code -->
package ${basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.ModelBuilder;
import de.gupta.clean.crud.template.domain.model.validation.Validatable;

import java.util.Optional;
<#if isGeneric() && domainGenericImports()?has_content>
<#list domainGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public interface ${modelName()}Model<#if isGeneric()><<#list genericTypeParameters() as param>${param}<#if param_has_next>, </#if></#list>></#if> extends Validatable
{
<#list properties() as property>
    ${property.returnType()} ${property.name()}();

</#list>
	@Override
	default void validate()
	{
	}

	interface ${modelName()}ModelBuilder${"<"}<#if isGeneric()><#list genericTypeParameters() as param>${param}<#if param_has_next>, </#if></#list>, </#if>M extends ${modelName()}Model<#if isGeneric()><<#list genericTypeParameters() as param>${param}<#if param_has_next>, </#if></#list>></#if>,B extends ${modelName()}ModelBuilder${"<"}<#if isGeneric()><#list genericTypeParameters() as param>${param}<#if param_has_next>, </#if></#list>, </#if>M,B${">"}${">"} extends ModelBuilder${"<"}M${">"}
	{
	<#list properties() as property>
	    B with${property.capitalizedName()}(final ${property.returnType()} ${property.name()});
    </#list>
	}
}