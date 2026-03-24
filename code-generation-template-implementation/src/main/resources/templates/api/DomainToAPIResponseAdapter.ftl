<#-- Template for generating DomainToAPIResponseAdapter class -->
package ${basePackage()}.useCases.crud.common.adapter;

import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
<#if apiDomainDifferingGenericTypeParameters()?has_content>
import ${basePackage()}.useCases.crud.common.adapter.converter.*;
</#if>
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.function.Function;

<#if isGeneric() && domainGenericImports()?has_content>
<#list domainGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric() && apiGenericImports()?has_content>
<#list apiGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Component
final class ${modelBaseName()}DomainToAPIResponseAdapter
		implements DomainToAPIResponseAdapter${"<"}${modelBaseName()}APIModelResponse,
		Long, ${modelBaseName()}DomainModelResponse${">"}
{
	private final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter;
<#list apiDomainDifferingGenericTypeParameters() as param>
	private final Function<${domainConcreteType(param)}, ${apiConcreteType(param)}> ${param?lower_case}DomainToAPIConverter;
</#list>

	@Override
	public ${modelBaseName()}APIModelResponse mapToAPIModelResponse(
			final IdentifiedModel${"<"}Long,
			${modelBaseName()}DomainModelResponse${">"} domainModel)
	{
		return ${modelBaseName()}APIModelResponse.of(
				idAdapter.mapToAPIModelID(domainModel.id()),
<#list properties() as property>
<#if apiAndDomainTypesDiffer(property.baseType())>
				<#if property.optional()>
				domainModel.model().${property.getter()}().map(${property.baseType()?lower_case}DomainToAPIConverter)<#if property_has_next>,</#if>
				<#else>
				${property.baseType()?lower_case}DomainToAPIConverter.apply(domainModel.model().${property.getter()}())<#if property_has_next>,</#if>
				</#if>
<#else>
				domainModel.model().${property.getter()}()<#if property_has_next>,</#if>
</#if>
</#list>
		);
	}

	${modelBaseName()}DomainToAPIResponseAdapter(
			final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter<#if apiDomainDifferingGenericTypeParameters()?has_content>,
<#list apiDomainDifferingGenericTypeParameters() as param>
			@Qualifier("${modelName()?uncap_first}${param}DomainToAPIConverter") final Function<${domainConcreteType(param)}, ${apiConcreteType(param)}> ${param?lower_case}DomainToAPIConverter<#if param_has_next>,</#if>
</#list>
</#if>)
	{
		this.idAdapter = idAdapter;
<#list apiDomainDifferingGenericTypeParameters() as param>
		this.${param?lower_case}DomainToAPIConverter = ${param?lower_case}DomainToAPIConverter;
</#list>
	}
}