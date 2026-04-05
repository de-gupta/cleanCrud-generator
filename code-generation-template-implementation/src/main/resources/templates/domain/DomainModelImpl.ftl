<#-- Template for generating DomainModelImpl class -->
package ${basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;

import java.util.Objects;
import java.util.Optional;
<#if domainModelImports()?has_content>
<#list domainModelImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

final class ${modelBaseName()}DomainModelImpl implements ${modelBaseName()}DomainModel
{
<#list properties() as property>
	private ${domainResolvedType(property.baseType())} ${property.name()};
</#list>

	static ${modelBaseName()}DomainModelBuilder builder()
	{
		return new BuilderImpl();
	}

<#list properties() as property>
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
	@Override
	public int hashCode()
	{
		int result = 1;
<#list properties() as property>
	<#if !property.optional()>
		result = 31 * result + (${property.name()} != null ? ${property.name()}.hashCode() : 0);
	</#if>
</#list>
		return result;
	}

	@Override
	public boolean equals(final Object o)
	{
		return o == this ||
				(o instanceof ${modelBaseName()}DomainModel that
<#list properties() as property>
				<#if !property.optional()>
					&& Objects.equals(${property.name()}, that.${property.name()}())
				</#if>
</#list>
				);
	}

	private ${modelBaseName()}DomainModelImpl()
	{
	}

	private static final class BuilderImpl extends AbstractModelBuilder${"<"}${modelBaseName()}DomainModel${">"}
			implements ${modelBaseName()}DomainModelBuilder
	{
		private final ${modelBaseName()}DomainModelImpl model;

<#list properties() as property>
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