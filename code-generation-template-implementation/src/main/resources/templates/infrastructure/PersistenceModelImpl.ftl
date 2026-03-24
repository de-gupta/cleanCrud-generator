<#-- Template for generating PersistenceModelImpl class -->
package ${basePackage()}.infrastructure.persistence.repository;

import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
<#if isGeneric() && persistenceGenericImports()?has_content>
<#list persistenceGenericImports() as import>
<#if import != "java.util.Optional" && import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

@Entity
@Table(name = "${modelName()?lower_case}_persistence_model")
public class ${modelBaseName()}PersistenceModelImpl implements ${modelBaseName()}PersistenceModel
{
	@Id
	@GeneratedValue
	private UUID id;

<#list properties() as property>
	<#if !property.optional()>
	@NotNull
	@Column(nullable = false)
	<#else>
	@Column<#if property.type() == "String">(columnDefinition = "TEXT")</#if>
	</#if>
	private ${persistenceResolvedType(property.baseType())} <#if property.name() == "user">userID<#else>${property.name()}</#if>;
</#list>

	static ${modelBaseName()}PersistenceModelBuilder builder()
	{
		return new ${modelBaseName()}PersistenceModelBuilderImpl();
	}

<#list properties() as property>
	@Override
	public ${persistencePropertyType(property)} ${property.getter()}()
	{
	<#if property.optional()>
		return Optional.ofNullable(<#if property.name() == "user">userID<#else>${property.name()}</#if>);
	<#else>
		return <#if property.name() == "user">userID<#else>${property.name()}</#if>;
	</#if>
	}

	@Override
	public void set${property.capitalizedName()}(final ${persistenceResolvedType(property.baseType())} ${property.name()})
	{
		this.<#if property.name() == "user">userID<#else>${property.name()}</#if> = ${property.name()};
		this.validate();
	}

</#list>
	@Override
	public UUID id()
	{
		return id;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object o)
	{
		if (!(o instanceof final ${modelBaseName()}PersistenceModelImpl that)) return false;
		return this == that || Objects.equals(id, that.id);
	}

	protected ${modelBaseName()}PersistenceModelImpl()
	{
	}

	private static final class ${modelBaseName()}PersistenceModelBuilderImpl extends AbstractModelBuilder${"<"}${modelBaseName()}PersistenceModel${">"}
			implements ${modelBaseName()}PersistenceModelBuilder
	{
		private final ${modelBaseName()}PersistenceModelImpl model;

<#list properties() as property>
		@Override
		public ${modelBaseName()}PersistenceModelBuilder with${property.capitalizedName()}(final ${persistencePropertyType(property)} ${property.name()})
		{
			<#if property.optional()>
			${property.name()}.ifPresent(value -> model.<#if property.name() == "user">userID<#else>${property.name()}</#if> = value);
			<#else>
			model.<#if property.name() == "user">userID<#else>${property.name()}</#if> = ${property.name()};
			</#if>
			return this;
		}

</#list>
		@Override
		protected ${modelBaseName()}PersistenceModel doBuild()
		{
			return model;
		}

		private ${modelBaseName()}PersistenceModelBuilderImpl()
		{
			this.model = new ${modelBaseName()}PersistenceModelImpl();
		}
	}
}