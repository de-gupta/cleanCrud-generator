<#-- Template for generating DomainToAPIResponseAdapter class -->
package ${basePackage}.useCases.crud.common.adapter;

import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
<#if isGeneric>
import ${basePackage}.useCases.crud.common.adapter.converter.*;
</#if>
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
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
final class ${modelName}DomainToAPIResponseAdapter
		implements DomainToAPIResponseAdapter${"<"}${modelName}APIModelResponse,
		Long, ${modelName}DomainModelResponse${">"}
{
	private final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter;
<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
	private final Function<${domainConcreteTypes[domainIndex]}, ${apiConcreteTypes[domainIndex]}> ${param?lower_case}DomainToAPIConverter;
</#if>
</#list>
</#if>

	@Override
	public ${modelName}APIModelResponse mapToAPIModelResponse(
			final IdentifiedModel${"<"}Long,
			${modelName}DomainModelResponse${">"} domainModel)
	{
		return ${modelName}APIModelResponse.of(
			idAdapter.mapToAPIModelID(domainModel.id()),
<#list properties as property>
<#if isGeneric && genericTypeParams?seq_contains(property.baseType)>
<#assign index = genericTypeParams?seq_index_of(property.baseType)>
<#if index < domainConcreteTypes?size && index < apiConcreteTypes?size>
			<#if property.optional>
			domainModel.model().${property.getter}().map(${property.baseType?lower_case}DomainToAPIConverter)<#if property_has_next>,</#if>
			<#else>
			${property.baseType?lower_case}DomainToAPIConverter.apply(domainModel.model().${property.getter}())<#if property_has_next>,</#if>
			</#if>
<#else>
			domainModel.model().${property.getter}()<#if property_has_next>,</#if>
</#if>
<#else>
			domainModel.model().${property.getter}()<#if property_has_next>,</#if>
</#if>
</#list>
		);
	}

	${modelName}DomainToAPIResponseAdapter(
			final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter<#if isGeneric>,
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
			@Qualifier("${modelName?uncap_first}${param}DomainToAPIConverter") final Function<${domainConcreteTypes[domainIndex]}, ${apiConcreteTypes[domainIndex]}> ${param?lower_case}DomainToAPIConverter<#if param_has_next>,</#if>
</#if>
</#list>
</#if>)
	{
		this.idAdapter = idAdapter;
<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
		this.${param?lower_case}DomainToAPIConverter = ${param?lower_case}DomainToAPIConverter;
</#if>
</#list>
</#if>
	}
}