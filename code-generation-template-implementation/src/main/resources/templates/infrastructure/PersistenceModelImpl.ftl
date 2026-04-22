<#-- Template for generating PersistenceModelImpl class -->
package ${aggregate().basePackage()}.infrastructure.persistence.repository;

<#if persistence().converterProperties()?has_content>
import ${aggregate().basePackage()}.infrastructure.persistence.converter.${persistence().jpaConvertersTypeName()};
</#if>
import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
<#if persistence().imports()?has_content>
<#list persistence().imports() as import>
<#if import != "java.util.Optional" && import != "java.util.UUID" && import != "java.util.Collection" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

@Entity
@Table(name = "${persistence().modelTableName()}")
public class ${aggregate().baseName()}PersistenceModelImpl implements ${aggregate().baseName()}PersistenceModel
{
	@Id
	@GeneratedValue
	private UUID id;

<#list composition().standaloneProperties() as property>
	<#if !property.optional()>
	@NotNull
	<#if property.isEnum()>
	@Enumerated(EnumType.STRING)
	</#if>
	<#if persistence().requiresJpaConverter(property)>
	@Convert(converter = ${persistence().jpaConvertersTypeName()}.${persistence().converterNestedClassName(property)}.class)
	@Column(name = "${persistence().sqlColumnName(property)}", nullable = false, columnDefinition = "TEXT")
	<#else>
	@Column(name = "${persistence().sqlColumnName(property)}", nullable = false)
	</#if>
	<#else>
	<#if property.isEnum()>
	@Enumerated(EnumType.STRING)
	</#if>
	<#if persistence().requiresJpaConverter(property)>
	@Convert(converter = ${persistence().jpaConvertersTypeName()}.${persistence().converterNestedClassName(property)}.class)
	@Column(name = "${persistence().sqlColumnName(property)}", columnDefinition = "TEXT")
	<#else>
	@Column(name = "${persistence().sqlColumnName(property)}"<#if property.type() == "String">, columnDefinition = "TEXT"</#if>)
	</#if>
	</#if>
	private ${persistence().resolvedType(property.baseType())} ${property.name()};
</#list>
<#list composition().relationships() as relationship>
	<#if relationship.many()>
	@ElementCollection
	@CollectionTable(name = "${persistence().modelTableName()}_${relationship.persistenceIdPropertyName()?lower_case}", joinColumns = @JoinColumn(name = "${aggregate().baseName()?lower_case}_id"))
	@Column(name = "${persistence().sqlIdentifier(relationship.persistenceIdPropertyName()?lower_case)}")
	private Collection<${relationship.satelliteApiIdType()}> ${relationship.persistenceIdPropertyName()} = new java.util.ArrayList<>();
	<#else>
	@Column(name = "${persistence().sqlIdentifier(relationship.persistenceIdPropertyName()?lower_case)}")
	private ${relationship.satelliteApiIdType()} ${relationship.persistenceIdPropertyName()};
	</#if>
</#list>

	static ${aggregate().baseName()}PersistenceModelBuilder builder()
	{
		return new ${aggregate().baseName()}PersistenceModelBuilderImpl();
	}

<#list composition().standaloneProperties() as property>
	@Override
	public ${persistence().propertyType(property)} ${property.getter()}()
	{
	<#if property.optional()>
		return Optional.ofNullable(${property.name()});
	<#else>
		return ${property.name()};
	</#if>
	}

	@Override
	public void set${property.capitalizedName()}(final ${persistence().resolvedType(property.baseType())} ${property.name()})
	{
		this.${property.name()} = ${property.name()};
		this.validate();
	}

</#list>
<#list composition().relationships() as relationship>
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
		if (!(o instanceof final ${aggregate().baseName()}PersistenceModelImpl that)) return false;
		return this == that || Objects.equals(id, that.id);
	}

	protected ${aggregate().baseName()}PersistenceModelImpl()
	{
	}

	private static final class ${aggregate().baseName()}PersistenceModelBuilderImpl extends AbstractModelBuilder<${aggregate().baseName()}PersistenceModel>
			implements ${aggregate().baseName()}PersistenceModelBuilder
	{
		private final ${aggregate().baseName()}PersistenceModelImpl model;

<#list composition().standaloneProperties() as property>
		@Override
		public ${aggregate().baseName()}PersistenceModelBuilder with${property.capitalizedName()}(final ${persistence().builderPropertyType(property)} ${property.name()})
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
		public ${aggregate().baseName()}PersistenceModelBuilder with${relationship.propertyCapitalizedName()}Id(final ${relationship.persistenceIdPropertyType()} ${relationship.persistenceIdPropertyName()})
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
		protected ${aggregate().baseName()}PersistenceModel doBuild()
		{
			return model;
		}

		private ${aggregate().baseName()}PersistenceModelBuilderImpl()
		{
			this.model = new ${aggregate().baseName()}PersistenceModelImpl();
		}
	}
}

