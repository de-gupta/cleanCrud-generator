<#-- Template for generating PersistenceModelImpl class -->
package ${basePackage}.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.AbstractModelBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
<#if isGeneric && persistenceGenericImports?has_content>
<#list persistenceGenericImports as import>
<#if import != "java.util.Optional" && import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

@Entity
@Table(name = "${modelName?lower_case}_persistence_model")
class ${modelName}PersistenceModelImpl implements ${modelName}PersistenceModel
{
	@Id
	@GeneratedValue
	private UUID id;

<#list properties as property>
    <#if !property.optional()>
        @NotNull
        @Column(nullable = false)
    <#else>
        @Column<#if property.type() == "String">(columnDefinition = "TEXT")</#if>
    </#if>
    <#if genericTypeParams()?seq_contains(property.type())>
        <#assign index = genericTypeParams()?seq_index_of(property.type())>
        <#if index < persistenceConcreteTypes?size>
    private ${persistenceConcreteTypes[index]} <#if property.name() == "user">userID<#else> ${property.name()}</#if>;
        <#else>
    private ${property.type()} <#if property.name() == "user">userID<#else>${property.name()}</#if>;
        </#if>
    <#else>
    private ${property.type()} ${property.name()};
    </#if>
</#list>

	static ${modelName}PersistenceModelBuilder builder()
	{
		return new ${modelName}PersistenceModelBuilderImpl();
	}

<#list properties as property>
    @Override
	public <#if property.optional()>Optional<<#if genericTypeParams()?seq_contains(property.baseType())><#assign index = genericTypeParams()?seq_index_of(property.baseType())><#if index < persistenceConcreteTypes?size>${persistenceConcreteTypes[index]}<#else>${property.baseType()}</#if><#else>${property.baseType()}</#if>><#else><#if genericTypeParams()?seq_contains(property.type())><#assign index = genericTypeParams()?seq_index_of(property.type())><#if index < persistenceConcreteTypes?size>${persistenceConcreteTypes[index]}<#else>${property.type()}</#if><#else>${property.type()}</#if></#if> ${property.getter()}()
    {
    <#if property.optional()>
        return Optional.ofNullable(<#if property.name() == "user">userID<#else>${property.name()}</#if>);
    <#else>
    return <#if property.name() == "user">userID<#else>${property.name()}</#if>;
    </#if>
	}

    @Override
	public void set${property.capitalizedName()}(final <#if genericTypeParams()?seq_contains(property.baseType())><#assign index = genericTypeParams()?seq_index_of(property.baseType())><#if index < persistenceConcreteTypes?size>${persistenceConcreteTypes[index]}<#else>${property.baseType()}</#if><#else>${property.baseType()}</#if> ${property.name()})
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
		if (!(o instanceof final ${modelName}PersistenceModelImpl that)) return false;
		return this == that || Objects.equals(id, that.id);
	}

	protected ${modelName}PersistenceModelImpl()
	{
	}

	private static final class ${modelName}PersistenceModelBuilderImpl extends AbstractModelBuilder${"<"}${modelName}PersistenceModel${">"}
			implements ${modelName}PersistenceModelBuilder
	{
		private final ${modelName}PersistenceModelImpl model;

<#list properties as property>
    @Override
	public ${modelName}PersistenceModelBuilder with${property.capitalizedName()}(
			final <#if property.optional()>Optional<<#if genericTypeParams()?seq_contains(property.baseType())><#assign index = genericTypeParams()?seq_index_of(property.baseType())><#if index < persistenceConcreteTypes?size>${persistenceConcreteTypes[index]}<#else>${property.baseType()}</#if><#else>${property.baseType()}</#if>><#else><#if genericTypeParams()?seq_contains(property.type())><#assign index = genericTypeParams()?seq_index_of(property.type())><#if index < persistenceConcreteTypes?size>${persistenceConcreteTypes[index]}<#else>${property.type()}</#if><#else>${property.type()}</#if></#if> ${property.name()})
	{
		<#if property.optional()>
                ${property.name()}.ifPresent(d -> model.<#if property.name() == "user">userID<#else>${property.name()}</#if> = d);
            <#else>
                model.<#if property.name() == "user">userID<#else>${property.name()}</#if> = ${property.name()};
            </#if>
		return this;
	}

</#list>
		@Override
		protected ${modelName}PersistenceModel doBuild()
		{
			return model;
		}

		private ${modelName}PersistenceModelBuilderImpl()
		{
			this.model = new ${modelName}PersistenceModelImpl();
		}
	}
}