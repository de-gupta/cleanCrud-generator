<#-- Template for generating APIToDomainUpdateAdapter class -->
package ${aggregate().basePackage()}.useCases.crud.common.adapter;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelUpdatePatch;
<#if types().apiDomainDifferingParameters()?has_content>
import ${aggregate().basePackage()}.useCases.crud.common.adapter.converter.*;
</#if>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
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
final class ${aggregate().baseName()}APIToDomainUpdateAdapter
		implements APIToDomainUpdateAdapter${"<"}${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}DomainModelUpdatePatch${">"}
{
<#list types().apiDomainDifferingParameters() as param>
	private final Function<${api().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}APIToDomainConverter;
</#list>

	@Override
	public ${aggregate().baseName()}DomainModelUpdatePatch mapToDomainModelUpdatePatch(final ${aggregate().baseName()}APIModelUpdatePatch apiModel)
	{
		return new ${aggregate().baseName()}DomainModelUpdatePatch(
<#list composition().standaloneProperties() as property>
<#if types().apiDomainDifferingParameters()?seq_contains(property.baseType())>
			apiModel.${property.name()}().map(${property.baseType()?lower_case}APIToDomainConverter)<#if property_has_next || composition().relationships()?has_content>,</#if>
<#else>
			apiModel.${property.name()}()<#if property_has_next || composition().relationships()?has_content>,</#if>
</#if>
</#list>
<#list composition().relationships() as relationship>
			apiModel.${relationship.propertyName()}(),
			apiModel.${relationship.removeFieldName()}()<#if relationship_has_next>,</#if>
</#list>
		);
	}

	${aggregate().baseName()}APIToDomainUpdateAdapter(<#list types().apiDomainDifferingParameters() as param>
			@Qualifier("${aggregate().beanNamePrefix()}${param}APIToDomainConverter") final Function<${api().concreteType(param)}, ${domain().concreteType(param)}> ${param?lower_case}APIToDomainConverter<#if param_has_next>,</#if>
</#list>)
	{
<#list types().apiDomainDifferingParameters() as param>
		this.${param?lower_case}APIToDomainConverter = ${param?lower_case}APIToDomainConverter;
</#list>
	}
}