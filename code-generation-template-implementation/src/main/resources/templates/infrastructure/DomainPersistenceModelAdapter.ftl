<#-- Template for generating DomainPersistenceModelAdapter class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.model;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.function.Function;

<#if isGeneric() && domainGenericImports()?has_content>
<#list domainGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric() && persistenceGenericImports()?has_content>
<#list persistenceGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Component
final class ${modelBaseName()}DomainPersistenceModelAdapter
		implements DomainPersistenceModelAdapter${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}PersistenceModel${">"}
{
	private final ModelBuilderFactory${"<"}${modelBaseName()}DomainModel,
	${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder${">"}
			domainModelBuilderFactory;
	private final ModelBuilderFactory${"<"}${modelBaseName()}PersistenceModel,
	${modelBaseName()}PersistenceModel.${modelBaseName()}PersistenceModelBuilder${">"}
			persistenceModelBuilderFactory;

<#list persistenceDomainDifferingGenericTypeParameters() as param>
	private final Function<${domainConcreteType(param)}, ${persistenceConcreteType(param)}> ${param?lower_case}DomainToPersistenceConverter;
	private final Function<${persistenceConcreteType(param)}, ${domainConcreteType(param)}> ${param?lower_case}PersistenceToDomainConverter;
</#list>

	@Override
	public ${modelBaseName()}PersistenceModel toPersistenceModel(final ${modelBaseName()}DomainModel domainModel)
	{
		return persistenceModelBuilderFactory.builder()
<#list properties() as property>
<#if property.optional()>
		<#if persistenceAndDomainTypesDiffer(property.baseType())>
		.with${property.capitalizedName()}(domainModel.${property.getter()}().map(${property.baseType()?lower_case}DomainToPersistenceConverter))
		<#else>
		.with${property.capitalizedName()}(domainModel.${property.getter()}())
		</#if>
<#else>
		<#if persistenceAndDomainTypesDiffer(property.baseType())>
		.with${property.capitalizedName()}(${property.baseType()?lower_case}DomainToPersistenceConverter.apply(domainModel.${property.getter()}()))
		<#else>
		.with${property.capitalizedName()}(domainModel.${property.getter()}())
		</#if>
</#if>
</#list>
		.build();
	}

	@Override
	public ${modelBaseName()}DomainModel toDomainModel(final ${modelBaseName()}PersistenceModel persistenceModel)
	{
		return domainModelBuilderFactory.builder()
<#list properties() as property>
<#if property.optional()>
		<#if persistenceAndDomainTypesDiffer(property.baseType())>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}().map(${property.baseType()?lower_case}PersistenceToDomainConverter))
		<#else>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}())
		</#if>
<#else>
		<#if persistenceAndDomainTypesDiffer(property.baseType())>
		.with${property.capitalizedName()}(${property.baseType()?lower_case}PersistenceToDomainConverter.apply(persistenceModel.${property.getter()}()))
		<#else>
		.with${property.capitalizedName()}(persistenceModel.${property.getter()}())
		</#if>
</#if>
</#list>
		.build();
	}

	@Override
	public ${modelBaseName()}PersistenceModel updatePersistenceModel(
			final ${modelBaseName()}PersistenceModel persistenceModel,
			final ${modelBaseName()}DomainModel domainModel)
	{
<#list properties() as property>
<#if persistenceAndDomainTypesDiffer(property.baseType())>
	<#if !property.optional()>
		persistenceModel.set${property.capitalizedName()}(${property.baseType()?lower_case}DomainToPersistenceConverter.apply(domainModel.${property.getter()}()));
	<#else>
		domainModel.${property.getter()}().map(${property.baseType()?lower_case}DomainToPersistenceConverter).ifPresent(persistenceModel::set${property.capitalizedName()});
	</#if>
<#else>
	<#if !property.optional()>
		persistenceModel.set${property.capitalizedName()}(domainModel.${property.getter()}());
	<#else>
		domainModel.${property.getter()}().ifPresent(persistenceModel::set${property.capitalizedName()});
	</#if>
</#if>
</#list>
		return persistenceModel;
	}

	${modelBaseName()}DomainPersistenceModelAdapter(
			final ModelBuilderFactory${"<"}${modelBaseName()}DomainModel,
			${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder${">"} domainModelBuilderFactory,
			final ModelBuilderFactory${"<"}${modelBaseName()}PersistenceModel,
			${modelBaseName()}PersistenceModel.${modelBaseName()}PersistenceModelBuilder${">"} persistenceModelBuilderFactory<#if persistenceDomainDifferingGenericTypeParameters()?has_content>,
<#list persistenceDomainDifferingGenericTypeParameters() as param>
			@Qualifier("${beanNamePrefix()}${param}DomainToPersistenceConverter") final Function<${domainConcreteType(param)}, ${persistenceConcreteType(param)}> ${param?lower_case}DomainToPersistenceConverter,
			@Qualifier("${beanNamePrefix()}${param}PersistenceToDomainConverter") final Function<${persistenceConcreteType(param)}, ${domainConcreteType(param)}> ${param?lower_case}PersistenceToDomainConverter<#if param_has_next>,</#if>
</#list>
</#if>)
	{
		this.domainModelBuilderFactory = domainModelBuilderFactory;
		this.persistenceModelBuilderFactory = persistenceModelBuilderFactory;
<#list persistenceDomainDifferingGenericTypeParameters() as param>
		this.${param?lower_case}DomainToPersistenceConverter = ${param?lower_case}DomainToPersistenceConverter;
		this.${param?lower_case}PersistenceToDomainConverter = ${param?lower_case}PersistenceToDomainConverter;
</#list>
	}
}
