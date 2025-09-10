<#-- Template for generating APIDomainModelAdapter class -->
package ${basePackage}.useCases.crud.all.facade.adapter.model;

import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.domain.model.dto.${modelName}DomainModelUpdatePatch;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelUpdatePatch;
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.useCases.crud.all.facade.adapter.model.CrudAPIDomainModelAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.function.Function;

<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric && apiGenericImports?has_content>
<#list apiGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Component
final class ${modelName}APIDomainModelAdapter implements
CrudAPIDomainModelAdapter${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long, ${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse${">"}
{
<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
	private final Function<${domainConcreteTypes[domainIndex]}, ${apiConcreteTypes[domainIndex]}> ${param?lower_case}DomainToAPIConverter;
	private final Function<${apiConcreteTypes[domainIndex]}, ${domainConcreteTypes[domainIndex]}> ${param?lower_case}APIToDomainConverter;
</#if>
</#list>
</#if>

	@Override
	public ${modelName}APIModelResponse mapToWebModelResponse(
			final IdentifiedModel${"<"}Long, ${modelName}DomainModelResponse${">"} identifiedDomainModel)
	{
		return ${modelName}APIModelResponse.of(
			identifiedDomainModel.id(),
<#list properties as property>
<#if isGeneric && genericTypeParams?seq_contains(property.baseType)>
<#assign index = genericTypeParams?seq_index_of(property.baseType)>
<#if index < domainConcreteTypes?size && index < apiConcreteTypes?size>
			<#if property.optional>
			identifiedDomainModel.model().${property.getter}().map(${property.baseType?lower_case}DomainToAPIConverter)<#if property_has_next>,</#if>
			<#else>
			${property.baseType?lower_case}DomainToAPIConverter.apply(identifiedDomainModel.model().${property.getter}())<#if property_has_next>,</#if>
			</#if>
<#else>
			identifiedDomainModel.model().${property.getter}()<#if property_has_next>,</#if>
</#if>
<#else>
			identifiedDomainModel.model().${property.getter}()<#if property_has_next>,</#if>
</#if>
</#list>
		);
	}

	@Override
	public ${modelName}DomainModelCreate mapToDomainModelCreate(final ${modelName}APIModelCreate apiModelCreate)
	{
		return ${modelName}DomainModelCreate.of(
<#list properties as property>
<#if isGeneric && genericTypeParams?seq_contains(property.baseType)>
<#assign index = genericTypeParams?seq_index_of(property.baseType)>
<#if index < domainConcreteTypes?size && index < apiConcreteTypes?size>
			<#if property.optional>
			apiModelCreate.${property.getter}().map(${property.baseType?lower_case}APIToDomainConverter)<#if property_has_next>,</#if>
			<#else>
			${property.baseType?lower_case}APIToDomainConverter.apply(apiModelCreate.${property.getter}())<#if property_has_next>,</#if>
			</#if>
<#else>
			apiModelCreate.${property.getter}()<#if property_has_next>,</#if>
</#if>
<#else>
			apiModelCreate.${property.getter}()<#if property_has_next>,</#if>
</#if>
</#list>
		);
	}

	@Override
	public ${modelName}DomainModelUpdatePatch mapToDomainModelUpdatePatch(final ${modelName}APIModelUpdatePatch apiModelUpdatePatch)
	{
		return ${modelName}DomainModelUpdatePatch.of(
<#list properties as property>
<#if isGeneric && genericTypeParams?seq_contains(property.baseType)>
<#assign index = genericTypeParams?seq_index_of(property.baseType)>
<#if index < domainConcreteTypes?size && index < apiConcreteTypes?size>
			apiModelUpdatePatch.${property.name}().map(${property.baseType?lower_case}APIToDomainConverter)<#if property_has_next>,</#if>
<#else>
			apiModelUpdatePatch.${property.name}()<#if property_has_next>,</#if>
</#if>
<#else>
			apiModelUpdatePatch.${property.name}()<#if property_has_next>,</#if>
</#if>
</#list>
		);
	}

	${modelName}APIDomainModelAdapter(<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
			@Qualifier("${modelName?uncap_first}${param}DomainToAPIConverter") final Function<${domainConcreteTypes[domainIndex]}, ${apiConcreteTypes[domainIndex]}> ${param?lower_case}DomainToAPIConverter,
			@Qualifier("${modelName?uncap_first}${param}APIToDomainConverter") final Function<${apiConcreteTypes[domainIndex]}, ${domainConcreteTypes[domainIndex]}> ${param?lower_case}APIToDomainConverter<#if param_has_next>,</#if>
</#if>
</#list>
</#if>)
	{
<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
		this.${param?lower_case}DomainToAPIConverter = ${param?lower_case}DomainToAPIConverter;
		this.${param?lower_case}APIToDomainConverter = ${param?lower_case}APIToDomainConverter;
</#if>
</#list>
</#if>
	}
}