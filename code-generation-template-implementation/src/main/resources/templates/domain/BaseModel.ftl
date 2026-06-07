<#-- Template for enriching the user-provided base Model interface -->
package ${aggregate().basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.ModelBuilder;
import de.gupta.clean.crud.template.domain.model.validation.Validatable;

import java.util.Optional;
<#if domain().imports()?has_content>
<#list domain().imports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

public interface ${aggregate().modelName()}<#if types().isGeneric()><<#list types().parameters() as param>${param}<#if param_has_next>, </#if></#list>></#if> extends Validatable
{
<#list composition().properties() as property>
    ${property.returnType()} ${property.name()}();

</#list>
	@Override
	default void validate()
	{
	}

	interface ${aggregate().modelName()}Builder${"<"}<#if types().isGeneric()><#list types().parameters() as param>${param}<#if param_has_next>, </#if></#list>, </#if>M extends ${aggregate().modelName()}<#if types().isGeneric()><<#list types().parameters() as param>${param}<#if param_has_next>, </#if></#list>></#if>, B extends ${aggregate().modelName()}Builder${"<"}<#if types().isGeneric()><#list types().parameters() as param>${param}<#if param_has_next>, </#if></#list>, </#if>M, B${">"}${">"} extends ModelBuilder${"<"}M${">"}
	{
    <#list composition().properties() as property>
		B with${property.capitalizedName()}(final ${composition().declaredBuilderPropertyType(property)} ${property.name()});
    </#list>
	}
}
