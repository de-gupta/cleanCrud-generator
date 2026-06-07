<#-- Template for generating DomainToAPIResponseAdapter class -->
package ${aggregate().basePackage()}.useCases.crud.common.adapter;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
<#list composition().relationships() as relationship>
import ${relationship.responseImport()};
import ${relationship.domainResponseImport(aggregate().basePackage())};
import ${relationship.domainModelImport()};
</#list>
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
		${aggregate().rootApiIdType()}, ${aggregate().baseName()}DomainModelResponse${">"}
{
	private final APIDomainIDAdapter${"<"}${aggregate().rootApiIdType()}, ${aggregate().rootDomainIdType()}${">"} idAdapter;
<#list types().apiDomainDifferingParameters() as param>
	private final Function<${domain().concreteType(param)}, ${api().concreteType(param)}> ${param?lower_case}DomainToAPIConverter;
</#list>
<#list composition().relationships() as relationship>
	private final DomainToAPIResponseAdapter<${relationship.responseType()}, ${relationship.satelliteApiIdType()}, ${relationship.domainResponseType()}> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter;
	private final de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder<${relationship.domainModelType()}, ${relationship.domainResponseType()}> ${relationship.relationshipVariablePrefix()}DomainResponseBuilder;
</#list>

	@Override
	public ${aggregate().baseName()}APIModelResponse mapToAPIModelResponse(
			final IdentifiedModel${"<"}${aggregate().rootApiIdType()},
			${aggregate().baseName()}DomainModelResponse${">"} domainModel)
	{
		return ${aggregate().baseName()}APIModelResponse.of(
				idAdapter.mapToAPIModelID(domainModel.id()),
<#list composition().properties() as property>
<#if composition().relationshipPropertyNames()?seq_contains(property.name())>
	<#assign relationship = composition().relationship(property)>
				<#if relationship.many()>
				domainModel.model().${relationship.propertyName()}().stream()
						.map(this::${relationship.relationshipVariablePrefix()}ToApi)
						.toList()
				<#elseif relationship.optional()>
				domainModel.model().${relationship.propertyName()}().map(this::${relationship.relationshipVariablePrefix()}ToApi)
				<#else>
				${relationship.relationshipVariablePrefix()}ToApi(domainModel.model().${relationship.propertyName()}())
				</#if><#if property_has_next>,</#if>
<#else>
<#if types().apiDomainDifferingParameters()?seq_contains(property.baseType())>
				<#if property.optional()>
				domainModel.model().${property.getter()}().map(${property.baseType()?lower_case}DomainToAPIConverter)<#if property_has_next>,</#if>
				<#else>
				${property.baseType()?lower_case}DomainToAPIConverter.apply(domainModel.model().${property.getter()}())<#if property_has_next>,</#if>
				</#if>
<#else>
				domainModel.model().${property.getter()}()<#if property_has_next>,</#if>
</#if>
</#if>
</#list>
		);
	}

<#list composition().relationships() as relationship>
	private ${relationship.responseType()} ${relationship.relationshipVariablePrefix()}ToApi(
			final de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel<${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}> satellite)
	{
		return ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter.mapToAPIModelResponse(
				de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel.of(
						satellite.id(),
						${relationship.relationshipVariablePrefix()}DomainResponseBuilder.toResponse(satellite.model())));
	}

</#list>

	${aggregate().baseName()}DomainToAPIResponseAdapter(
			final APIDomainIDAdapter${"<"}${aggregate().rootApiIdType()}, ${aggregate().rootDomainIdType()}${">"} idAdapter<#if types().apiDomainDifferingParameters()?has_content || composition().relationships()?has_content>,
<#list types().apiDomainDifferingParameters() as param>
			@Qualifier("${aggregate().beanNamePrefix()}${param}DomainToAPIConverter") final Function<${domain().concreteType(param)}, ${api().concreteType(param)}> ${param?lower_case}DomainToAPIConverter<#if param_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#list composition().relationships() as relationship>
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainToAPIResponseAdapter") final DomainToAPIResponseAdapter<${relationship.responseType()}, ${relationship.satelliteApiIdType()}, ${relationship.domainResponseType()}> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainResponseBuilder") final de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder<${relationship.domainModelType()}, ${relationship.domainResponseType()}> ${relationship.relationshipVariablePrefix()}DomainResponseBuilder<#if relationship_has_next>,</#if>
</#list>
</#if>)
	{
		this.idAdapter = idAdapter;
<#list types().apiDomainDifferingParameters() as param>
		this.${param?lower_case}DomainToAPIConverter = ${param?lower_case}DomainToAPIConverter;
</#list>
<#list composition().relationships() as relationship>
		this.${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter = ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter;
		this.${relationship.relationshipVariablePrefix()}DomainResponseBuilder = ${relationship.relationshipVariablePrefix()}DomainResponseBuilder;
</#list>
	}
}
