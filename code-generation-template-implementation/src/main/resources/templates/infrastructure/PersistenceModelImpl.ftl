<#-- Template for generating PersistenceModelImpl class -->
package ${basePackage()}.infrastructure.persistence.repository;

<#if jpaConverterProperties()?has_content>
import ${basePackage()}.infrastructure.persistence.converter.${persistenceJpaConvertersTypeName()};
</#if>
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
<#if persistenceModelImports()?has_content>
<#list persistenceModelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.UUID" && import != "java.util.Collection" && import != "java.util.List">
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

<#list standaloneProperties() as property>
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
<#list relationships() as relationship>
	<#if relationship.many()>
	@ElementCollection
	@CollectionTable(name = "${persistenceModelTableName()}_${relationship.persistenceIdPropertyName()?lower_case}", joinColumns = @JoinColumn(name = "${modelBaseName()?lower_case}_id"))
	@Column(name = "${sqlIdentifier(relationship.persistenceIdPropertyName()?lower_case)}")
	private Collection<${relationship.satelliteApiIdType()}> ${relationship.persistenceIdPropertyName()} = new java.util.ArrayList<>();
	<#else>
	@Column(name = "${sqlIdentifier(relationship.persistenceIdPropertyName()?lower_case)}")
	private ${relationship.satelliteApiIdType()} ${relationship.persistenceIdPropertyName()};
	</#if>
</#list>

	static ${modelBaseName()}PersistenceModelBuilder builder()
	{
		return new ${modelBaseName()}PersistenceModelBuilderImpl();
	}

<#list standaloneProperties() as property>
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
<#list relationships() as relationship>
	@Override
	public ${relationship.persistenceIdPropertyType()} ${relationship.persistenceIdPropertyName()}()
	{
		<#if relationship.many()>
		return List.copyOf(${relationship.persistenceIdPropertyName()});
		<#elseif relationship.optional()>
		return Optional.ofNullable(${relationship.persistenceIdPropertyName()});
		<#else>
		return ${relationship.persistenceIdPropertyName()};
		</#if>
	}

	@Override
	public void set${relationship.propertyCapitalizedName()}Id(
			final <#if relationship.many()>Collection<${relationship.satelliteApiIdType()}><#else>${relationship.satelliteApiIdType()}</#if> ${relationship.persistenceIdPropertyName()})
	{
		<#if relationship.many()>
		this.${relationship.persistenceIdPropertyName()} = new java.util.ArrayList<>(${relationship.persistenceIdPropertyName()});
		<#else>
		this.${relationship.persistenceIdPropertyName()} = ${relationship.persistenceIdPropertyName()};
		</#if>
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

	private static final class ${modelBaseName()}PersistenceModelBuilderImpl extends AbstractModelBuilder<${modelBaseName()}PersistenceModel>
			implements ${modelBaseName()}PersistenceModelBuilder
	{
		private final ${modelBaseName()}PersistenceModelImpl model;

<#list standaloneProperties() as property>
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
<#list relationships() as relationship>
		@Override
		public ${modelBaseName()}PersistenceModelBuilder with${relationship.propertyCapitalizedName()}Id(final ${relationship.persistenceIdPropertyType()} ${relationship.persistenceIdPropertyName()})
		{
			<#if relationship.many()>
			model.${relationship.persistenceIdPropertyName()} = new java.util.ArrayList<>(${relationship.persistenceIdPropertyName()});
			<#elseif relationship.optional()>
			model.${relationship.persistenceIdPropertyName()} = ${relationship.persistenceIdPropertyName()}.orElse(null);
			<#else>
			model.${relationship.persistenceIdPropertyName()} = ${relationship.persistenceIdPropertyName()};
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

