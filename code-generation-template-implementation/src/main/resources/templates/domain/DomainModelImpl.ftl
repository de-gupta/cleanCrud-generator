<#-- Template for generating DomainModelImpl class -->
package ${aggregate().basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
<#if domain().modelImports()?has_content>
<#list domain().modelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

final class ${aggregate().baseName()}DomainModelImpl implements ${aggregate().baseName()}DomainModel
{
<#list composition().standaloneProperties() as property>
	private ${domain().resolvedType(property.baseType())} ${property.name()};
</#list>
<#list composition().relationships() as relationship>
	private ${relationship.responseFieldType()} ${relationship.propertyName()};
</#list>

	static ${aggregate().baseName()}DomainModelBuilder builder()
	{
		return new BuilderImpl();
	}

<#list composition().standaloneProperties() as property>
	@Override
	public ${domain().propertyType(property)} ${property.getter()}()
	{
	<#if property.optional()>
		return Optional.ofNullable(${property.name()});
	<#else>
		return ${property.name()};
	</#if>
	}

</#list>
<#list composition().relationships() as relationship>
	@Override
	public ${relationship.responseFieldType()} ${relationship.propertyName()}()
	{
		return ${relationship.propertyName()};
	}

</#list>
	@Override
	public int hashCode()
	{
		int result = 1;
<#list composition().standaloneProperties() as property>
	<#if !property.optional()>
		result = 31 * result + Objects.hashCode(${property.name()});
	</#if>
</#list>
<#list composition().relationships() as relationship>
		result = 31 * result + Objects.hashCode(${relationship.propertyName()});
</#list>
		return result;
	}

	@Override
	public boolean equals(final Object o)
	{
		return o == this ||
				(o instanceof ${aggregate().baseName()}DomainModel that
<#list composition().standaloneProperties() as property>
				<#if !property.optional()>
					&& Objects.equals(${property.name()}, that.${property.name()}())
				</#if>
</#list>
<#list composition().relationships() as relationship>
					&& Objects.equals(${relationship.propertyName()}, that.${relationship.propertyName()}())
</#list>
				);
	}

	private ${aggregate().baseName()}DomainModelImpl()
	{
	}

	private static final class BuilderImpl extends AbstractModelBuilder<${aggregate().baseName()}DomainModel>
			implements ${aggregate().baseName()}DomainModelBuilder
	{
		private final ${aggregate().baseName()}DomainModelImpl model;

<#list composition().standaloneProperties() as property>
		@Override
		public ${aggregate().baseName()}DomainModelBuilder with${property.capitalizedName()}(final ${domain().builderPropertyType(property)} ${property.name()})
		{
			<#if property.optional()>
			model.${property.name()} = ${property.name()}.orElse(null);
			<#else>
			model.${property.name()} = ${property.name()};
			</#if>
			return this;
		}

</#list>
<#list composition().relationships() as relationship>
		@Override
		public ${aggregate().baseName()}DomainModelBuilder with${relationship.propertyCapitalizedName()}(final ${relationship.responseFieldType()} ${relationship.propertyName()})
		{
			<#if relationship.many()>
			model.${relationship.propertyName()} = ${relationship.propertyName()} == null ? List.of() : List.copyOf(${relationship.propertyName()});
			<#elseif relationship.optional()>
			model.${relationship.propertyName()} = Optional.ofNullable(${relationship.propertyName()}).orElse(Optional.empty());
			<#else>
			model.${relationship.propertyName()} = ${relationship.propertyName()};
			</#if>
			return this;
		}

</#list>
		@Override
		protected ${aggregate().baseName()}DomainModel doBuild()
		{
			return model;
		}

		private BuilderImpl()
		{
			this.model = new ${aggregate().baseName()}DomainModelImpl();
		}
	}
}
