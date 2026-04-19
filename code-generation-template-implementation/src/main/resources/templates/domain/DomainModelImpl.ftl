<#-- Template for generating DomainModelImpl class -->
package ${basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
<#if domainModelImports()?has_content>
<#list domainModelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

final class ${modelBaseName()}DomainModelImpl implements ${modelBaseName()}DomainModel
{
<#list standaloneProperties() as property>
	private ${domainResolvedType(property.baseType())} ${property.name()};
</#list>
<#list relationships() as relationship>
	private ${relationship.responseFieldType()} ${relationship.propertyName()};
</#list>

	static ${modelBaseName()}DomainModelBuilder builder()
	{
		return new BuilderImpl();
	}

<#list standaloneProperties() as property>
	@Override
	public ${domainPropertyType(property)} ${property.getter()}()
	{
	<#if property.optional()>
		return Optional.ofNullable(${property.name()});
	<#else>
		return ${property.name()};
	</#if>
	}

</#list>
<#list relationships() as relationship>
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
<#list standaloneProperties() as property>
	<#if !property.optional()>
		result = 31 * result + Objects.hashCode(${property.name()});
	</#if>
</#list>
<#list relationships() as relationship>
		result = 31 * result + Objects.hashCode(${relationship.propertyName()});
</#list>
		return result;
	}

	@Override
	public boolean equals(final Object o)
	{
		return o == this ||
				(o instanceof ${modelBaseName()}DomainModel that
<#list standaloneProperties() as property>
				<#if !property.optional()>
					&& Objects.equals(${property.name()}, that.${property.name()}())
				</#if>
</#list>
<#list relationships() as relationship>
					&& Objects.equals(${relationship.propertyName()}, that.${relationship.propertyName()}())
</#list>
				);
	}

	private ${modelBaseName()}DomainModelImpl()
	{
	}

	private static final class BuilderImpl extends AbstractModelBuilder<${modelBaseName()}DomainModel>
			implements ${modelBaseName()}DomainModelBuilder
	{
		private final ${modelBaseName()}DomainModelImpl model;

<#list standaloneProperties() as property>
		@Override
		public ${modelBaseName()}DomainModelBuilder with${property.capitalizedName()}(final ${domainBuilderPropertyType(property)} ${property.name()})
		{
			<#if property.optional()>
			model.${property.name()} = ${property.name()}.orElse(null);
			<#else>
			model.${property.name()} = ${property.name()};
			</#if>
			return this;
		}

</#list>
<#list relationships() as relationship>
		@Override
		public ${modelBaseName()}DomainModelBuilder with${relationship.propertyCapitalizedName()}(final ${relationship.responseFieldType()} ${relationship.propertyName()})
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
		protected ${modelBaseName()}DomainModel doBuild()
		{
			return model;
		}

		private BuilderImpl()
		{
			this.model = new ${modelBaseName()}DomainModelImpl();
		}
	}
}

