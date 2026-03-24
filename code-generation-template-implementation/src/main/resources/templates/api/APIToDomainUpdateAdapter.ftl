<#-- Template for generating APIToDomainUpdateAdapter class -->
package ${basePackage()}.useCases.crud.common.adapter;

import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelUpdatePatch;
<#if apiDomainDifferingGenericTypeParameters()?has_content>
import ${basePackage()}.useCases.crud.common.adapter.converter.*;
</#if>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
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
final class ${modelBaseName()}APIToDomainUpdateAdapter
		implements APIToDomainUpdateAdapter${"<"}${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}DomainModelUpdatePatch${">"}
{
<#list apiDomainDifferingGenericTypeParameters() as param>
	private final Function<${apiConcreteType(param)}, ${domainConcreteType(param)}> ${param?lower_case}APIToDomainConverter;
</#list>

	@Override
	public ${modelBaseName()}DomainModelUpdatePatch mapToDomainModelUpdatePatch(final ${modelBaseName()}APIModelUpdatePatch apiModel)
	{
		return new ${modelBaseName()}DomainModelUpdatePatch(
<#list properties() as property>
<#if apiAndDomainTypesDiffer(property.baseType())>
			apiModel.${property.name()}().map(${property.baseType()?lower_case}APIToDomainConverter)<#if property_has_next>,</#if>
<#else>
			apiModel.${property.name()}()<#if property_has_next>,</#if>
</#if>
</#list>
		);
	}

	${modelBaseName()}APIToDomainUpdateAdapter(<#list apiDomainDifferingGenericTypeParameters() as param>
			@Qualifier("${beanNamePrefix()}${param}APIToDomainConverter") final Function<${apiConcreteType(param)}, ${domainConcreteType(param)}> ${param?lower_case}APIToDomainConverter<#if param_has_next>,</#if>
</#list>)
	{
<#list apiDomainDifferingGenericTypeParameters() as param>
		this.${param?lower_case}APIToDomainConverter = ${param?lower_case}APIToDomainConverter;
</#list>
	}
}