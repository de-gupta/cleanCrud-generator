<#-- Template for generating DomainModelImpl class -->
package ${basePackage}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;

import java.util.Objects;
import java.util.Optional;
<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

final class ${modelName}DomainModelImpl implements ${modelName}DomainModel
{
<#list properties as property>
    <#if genericTypeParams?seq_contains(property.type)>
        <#assign index = genericTypeParams?seq_index_of(property.type)>
        <#if index < domainConcreteTypes?size>
    private ${domainConcreteTypes[index]} ${property.name};
        <#else>
    private ${property.type} ${property.name};
        </#if>
    <#else>
    private ${property.type} ${property.name};
    </#if>
</#list>

	static ${modelName}DomainModelBuilder builder()
{
	return new BuilderImpl();
}

<#list properties as property>
    @Override
    public <#if property.optional>Optional<<#if genericTypeParams?seq_contains(property.baseType)><#assign index = genericTypeParams?seq_index_of(property.baseType)><#if index < domainConcreteTypes?size>${domainConcreteTypes[index]}<#else>${property.baseType}</#if><#else>${property.baseType}</#if>><#else><#if genericTypeParams?seq_contains(property.type)><#assign index = genericTypeParams?seq_index_of(property.type)><#if index < domainConcreteTypes?size>${domainConcreteTypes[index]}<#else>${property.type}</#if><#else>${property.type}</#if></#if> ${property.getter}()
    {
    <#if property.optional>
        return Optional.ofNullable(${property.name});
    <#else>
        return ${property.name};
    </#if>
    }

</#list>
@Override
public int hashCode()
{
	// TODO from template: check and adapt if you change equals method
	int result = 1;
<#list properties as property>
	<#if !property.optional>
	result = 31 * result + (${property.name} != null ? ${property.name}.hashCode() : 0);
	</#if>
</#list>
	return result;
}

@Override
public boolean equals(Object o)
{
	// TODO from template: check and adapt as needed
	return o == this ||
		(o instanceof ${modelName}DomainModel that
		<#list properties as property>
			<#if !property.optional>
			&& Objects.equals(${property.name}, that.${property.name}())
			</#if>
		</#list>
		);
}

private ${modelName}DomainModelImpl()
{
}

	private static final class BuilderImpl extends AbstractModelBuilder${"<"}${modelName}DomainModel${">"}
implements
    ${modelName}DomainModelBuilder
{
	private final ${modelName}DomainModelImpl model;

<#list properties as property>
    @Override
	public ${modelName}DomainModelBuilder with${property.capitalizedName}(
			final <#if property.optional>Optional<<#if genericTypeParams?seq_contains(property.baseType)><#assign index = genericTypeParams?seq_index_of(property.baseType)><#if index < domainConcreteTypes?size>${domainConcreteTypes[index]}<#else>${property.baseType}</#if><#else>${property.baseType}</#if>><#else><#if genericTypeParams?seq_contains(property.type)><#assign index = genericTypeParams?seq_index_of(property.type)><#if index < domainConcreteTypes?size>${domainConcreteTypes[index]}<#else>${property.type}</#if><#else>${property.type}</#if></#if> ${property.name})
    {
    <#if property.optional>
        ${property.name}.ifPresent(d -> model.${property.name} = d);
    <#else>
        model.${property.name} = ${property.name};
    </#if>
    return this;
    }

</#list>
@Override
protected ${modelName}DomainModel doBuild()
{
return model;
}

private BuilderImpl()
{
	this.model = new ${modelName}DomainModelImpl();
}
}
}