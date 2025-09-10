<#-- Template for generating DomainPersistenceModelAdapter class -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.model;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.function.Function;

<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric && persistenceGenericImports?has_content>
<#list persistenceGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Component
final class ${modelName}DomainPersistenceModelAdapter
		implements DomainPersistenceModelAdapter${"<"}${modelName}DomainModel, ${modelName}PersistenceModel${">"}
{
	private final ModelBuilderFactory${"<"}${modelName}DomainModel,
	${modelName}DomainModel.${modelName}DomainModelBuilder${">"}
			domainModelBuilderFactory;
	private final ModelBuilderFactory${"<"}${modelName}PersistenceModel,
	${modelName}PersistenceModel.${modelName}PersistenceModelBuilder${">"}
			persistenceModelBuilderFactory;

<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < persistenceConcreteTypes?size>
	private final Function<${domainConcreteTypes[domainIndex]}, ${persistenceConcreteTypes[domainIndex]}> ${param?lower_case}DomainToPersistenceConverter;
	private final Function<${persistenceConcreteTypes[domainIndex]}, ${domainConcreteTypes[domainIndex]}> ${param?lower_case}PersistenceToDomainConverter;
</#if>
</#list>
</#if>

	@Override
	public ${modelName}PersistenceModel toPersistenceModel(
			final ${modelName}DomainModel domainModel)
	{
			return persistenceModelBuilderFactory.builder()
<#list properties as property>
<#if isGeneric && genericTypeParams?seq_contains(property.baseType)>
<#assign index = genericTypeParams?seq_index_of(property.baseType)>
<#if index < domainConcreteTypes?size && index < persistenceConcreteTypes?size>
    <#if property.optional>
    .with${property.capitalizedName}(domainModel.${property.getter}().map(${property.baseType?lower_case}DomainToPersistenceConverter))
    <#else>
    .with${property.capitalizedName}(${property.baseType?lower_case}DomainToPersistenceConverter.apply(domainModel.${property.getter}()))
    </#if>
<#else>
    .with${property.capitalizedName}(domainModel.${property.getter}())
</#if>
<#else>
    .with${property.capitalizedName}(domainModel.${property.getter}())
</#if><#if property_has_next>
</#if></#list>
		.build();
	}

	@Override
	public ${modelName}DomainModel toDomainModel(
			final ${modelName}PersistenceModel persistenceModel)
	{
			return domainModelBuilderFactory.builder()
<#list properties as property>
<#if isGeneric && genericTypeParams?seq_contains(property.baseType)>
<#assign index = genericTypeParams?seq_index_of(property.baseType)>
<#if index < domainConcreteTypes?size && index < persistenceConcreteTypes?size>
    <#if property.optional>
    .with${property.capitalizedName}(persistenceModel.${property.getter}().map(${property.baseType?lower_case}PersistenceToDomainConverter))
    <#else>
    .with${property.capitalizedName}(${property.baseType?lower_case}PersistenceToDomainConverter.apply(persistenceModel.${property.getter}()))
    </#if>
<#else>
    .with${property.capitalizedName}(persistenceModel.${property.getter}())
</#if>
<#else>
    .with${property.capitalizedName}(persistenceModel.${property.getter}())
</#if><#if property_has_next>
</#if></#list>
		.build();
	}

	@Override
	public ${modelName}PersistenceModel updatePersistenceModel(
			final ${modelName}PersistenceModel persistenceModel,
			final ${modelName}DomainModel domainModel)
	{
<#list properties as property>
<#if isGeneric && genericTypeParams?seq_contains(property.baseType)>
<#assign index = genericTypeParams?seq_index_of(property.baseType)>
<#if index < domainConcreteTypes?size && index < persistenceConcreteTypes?size>
    <#if !property.optional>
        persistenceModel.set${property.capitalizedName}(${property.baseType?lower_case}DomainToPersistenceConverter.apply(domainModel.${property.getter}()));
    <#else>
        domainModel.${property.getter}().map(${property.baseType?lower_case}DomainToPersistenceConverter).ifPresent(persistenceModel::set${property.capitalizedName});
    </#if>
<#else>
    <#if !property.optional>
        persistenceModel.set${property.capitalizedName}(domainModel.${property.getter}());
    <#else>
        domainModel.${property.getter}().ifPresent(persistenceModel::set${property.capitalizedName});
    </#if>
</#if>
<#else>
    <#if !property.optional>
        persistenceModel.set${property.capitalizedName}(domainModel.${property.getter}());
    <#else>
        domainModel.${property.getter}().ifPresent(persistenceModel::set${property.capitalizedName});
    </#if>
</#if></#list>

		return persistenceModel;
	}

	${modelName}DomainPersistenceModelAdapter(
			final ModelBuilderFactory${"<"}${modelName}DomainModel,
			${modelName}DomainModel.${modelName}DomainModelBuilder${">"} domainModelBuilderFactory,
			final ModelBuilderFactory${"<"}${modelName}PersistenceModel,
			${modelName}PersistenceModel.${modelName}PersistenceModelBuilder${">"} persistenceModelBuilderFactory<#if isGeneric>,
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < persistenceConcreteTypes?size>
			@Qualifier("${modelName?uncap_first}${param}DomainToPersistenceConverter") final Function<${domainConcreteTypes[domainIndex]}, ${persistenceConcreteTypes[domainIndex]}> ${param?lower_case}DomainToPersistenceConverter,
			@Qualifier("${modelName?uncap_first}${param}PersistenceToDomainConverter") final Function<${persistenceConcreteTypes[domainIndex]}, ${domainConcreteTypes[domainIndex]}> ${param?lower_case}PersistenceToDomainConverter<#if param_has_next>,</#if>
</#if>
</#list>
</#if>)
	{
		this.domainModelBuilderFactory = domainModelBuilderFactory;
		this.persistenceModelBuilderFactory = persistenceModelBuilderFactory;
<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < persistenceConcreteTypes?size>
		this.${param?lower_case}DomainToPersistenceConverter = ${param?lower_case}DomainToPersistenceConverter;
		this.${param?lower_case}PersistenceToDomainConverter = ${param?lower_case}PersistenceToDomainConverter;
</#if>
</#list>
</#if>
	}
}