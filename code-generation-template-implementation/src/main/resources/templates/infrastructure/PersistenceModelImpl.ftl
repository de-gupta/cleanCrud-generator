<#-- Template for generating PersistenceModelImpl class -->
package ${basePackage()}.infrastructure.persistence.repository;

<#if jpaConverterProperties()?has_content>
import ${basePackage()}.infrastructure.persistence.converter.${persistenceJpaConvertersTypeName()};
</#if>
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
<#if persistenceModelImports()?has_content>
<#list persistenceModelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

@Entity
@Table(name = "${persistenceModelTableName()}")
public class ${modelBaseName()}PersistenceModelImpl implements ${modelBaseName()}PersistenceModel
{
	@Id
	@GeneratedValue
	private UUID id;

<#list properties() as property>
	<#if !property.optional()>
	@NotNull
	<#if property.isEnum()>
	@Enumerated(EnumType.STRING)
	</#if>
	<#if requiresJpaConverter(property)>
	@Convert(converter = ${persistenceJpaConvertersTypeName()}.${jpaConverterNestedClassName(property)}.class)
	@Column(name = "${sqlColumnName(property)}", nullable = false, columnDefinition = "TEXT")
	<#else>
	@Column(name = "${sqlColumnName(property)}", nullable = false)
	</#if>
	<#else>
	<#if property.isEnum()>
	@Enumerated(EnumType.STRING)
	</#if>
	<#if requiresJpaConverter(property)>
	@Convert(converter = ${persistenceJpaConvertersTypeName()}.${jpaConverterNestedClassName(property)}.class)
	@Column(name = "${sqlColumnName(property)}", columnDefinition = "TEXT")
	<#else>
	@Column(name = "${sqlColumnName(property)}"<#if property.type() == "String">, columnDefinition = "TEXT"</#if>)
	</#if>
	</#if>
	private ${persistenceResolvedType(property.baseType())} ${property.name()};
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
		return Optional.ofNullable(${property.name()});
	<#else>
		return ${property.name()};
	</#if>
	}

	@Override
	public void set${property.capitalizedName()}(final ${persistenceResolvedType(property.baseType())} ${property.name()})
	{
		this.${property.name()} = ${property.name()};
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
		public ${modelBaseName()}PersistenceModelBuilder with${property.capitalizedName()}(final ${persistenceBuilderPropertyType(property)} ${property.name()})
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