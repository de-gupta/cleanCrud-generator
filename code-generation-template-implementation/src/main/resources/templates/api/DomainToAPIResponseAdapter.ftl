<#-- Template for generating DomainToAPIResponseAdapter class -->
package ${aggregate().basePackage()}.useCases.crud.common.adapter;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
<#if types().apiDomainDifferingParameters()?has_content>
import ${aggregate().basePackage()}.useCases.crud.common.adapter.converter.*;
</#if>
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.function.Function;

<#if types().isGeneric() && domain().genericImports()?has_content>
<#list domain().genericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if types().isGeneric() && api().genericImports()?has_content>
<#list api().genericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Component
final class ${aggregate().baseName()}DomainToAPIResponseAdapter
		implements DomainToAPIResponseAdapter${"<"}${aggregate().baseName()}APIModelResponse,
		Long, ${aggregate().baseName()}DomainModelResponse${">"}
{
	private final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter;
<#list types().apiDomainDifferingParameters() as param>
	private final Function<${domain().concreteType(param)}, ${api().concreteType(param)}> ${param?lower_case}DomainToAPIConverter;
</#list>

	@Override
	public ${aggregate().baseName()}APIModelResponse mapToAPIModelResponse(
			final IdentifiedModel${"<"}Long,
			${aggregate().baseName()}DomainModelResponse${">"} domainModel)
	{
		return ${aggregate().baseName()}APIModelResponse.of(
				idAdapter.mapToAPIModelID(domainModel.id()),
<#list composition().properties() as property>
<#if types().apiDomainDifferingParameters()?seq_contains(property.baseType())>
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

	${aggregate().baseName()}DomainToAPIResponseAdapter(
			final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter<#if types().apiDomainDifferingParameters()?has_content>,
<#list types().apiDomainDifferingParameters() as param>
			@Qualifier("${aggregate().beanNamePrefix()}${param}DomainToAPIConverter") final Function<${domain().concreteType(param)}, ${api().concreteType(param)}> ${param?lower_case}DomainToAPIConverter<#if param_has_next>,</#if>
</#list>
</#if>)
	{
		this.idAdapter = idAdapter;
<#list types().apiDomainDifferingParameters() as param>
		this.${param?lower_case}DomainToAPIConverter = ${param?lower_case}DomainToAPIConverter;
</#list>
	}
}