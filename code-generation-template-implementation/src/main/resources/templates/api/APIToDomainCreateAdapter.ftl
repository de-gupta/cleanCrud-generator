<#-- Template for generating APIToDomainCreateAdapter class -->
package ${aggregate().basePackage()}.useCases.crud.common.adapter;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelCreate;
<#if types().apiDomainDifferingParameters()?has_content>
import ${aggregate().basePackage()}.useCases.crud.common.adapter.converter.*;
</#if>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
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
final class ${aggregate().baseName()}APIToDomainCreateAdapter implements APIToDomainCreateAdapter${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}DomainModelCreate${">"}
{
<#list types().apiDomainDifferingParameters() as param>
	private final Function<${api().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}APIToDomainConverter;
</#list>

	@Override
	public ${aggregate().baseName()}DomainModelCreate mapToDomainModelCreate(final ${aggregate().baseName()}APIModelCreate apiModel)
	{
		return new ${aggregate().baseName()}DomainModelCreate(
<#list composition().standaloneProperties() as property>
<#if types().apiDomainDifferingParameters()?seq_contains(property.baseType())>
			<#if property.optional()>
			apiModel.${property.getter()}().map(${property.baseType()?lower_case}APIToDomainConverter)<#if property_has_next || composition().relationships()?has_content>,</#if>
			<#else>
			${property.baseType()?lower_case}APIToDomainConverter.apply(apiModel.${property.getter()}())<#if property_has_next || composition().relationships()?has_content>,</#if>
			</#if>
<#else>
			apiModel.${property.getter()}()<#if property_has_next || composition().relationships()?has_content>,</#if>
</#if>
</#list>
<#list composition().relationships() as relationship>
			apiModel.${relationship.propertyName()}()<#if relationship_has_next>,</#if>
</#list>
		);
	}

	${aggregate().baseName()}APIToDomainCreateAdapter(<#list types().apiDomainDifferingParameters() as param>
			@Qualifier("${aggregate().beanNamePrefix()}${param}APIToDomainConverter") final Function<${api().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}APIToDomainConverter<#if param_has_next>,</#if>
</#list>)
	{
<#list types().apiDomainDifferingParameters() as param>
		this.${param?lower_case}APIToDomainConverter = ${param?lower_case}APIToDomainConverter;
</#list>
	}
}
