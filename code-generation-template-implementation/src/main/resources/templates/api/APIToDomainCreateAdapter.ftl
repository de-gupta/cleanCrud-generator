<#-- Template for generating APIToDomainCreateAdapter class -->
package ${basePackage()}.useCases.crud.common.adapter;

import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelCreate;
<#if apiDomainDifferingGenericTypeParameters()?has_content>
import ${basePackage()}.useCases.crud.common.adapter.converter.*;
</#if>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
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
final class ${modelBaseName()}APIToDomainCreateAdapter implements APIToDomainCreateAdapter${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}DomainModelCreate${">"}
{
<#list apiDomainDifferingGenericTypeParameters() as param>
	private final Function<${apiConcreteType(param)}, ${domainConcreteType(param)}> ${param?lower_case}APIToDomainConverter;
</#list>

	@Override
	public ${modelBaseName()}DomainModelCreate mapToDomainModelCreate(final ${modelBaseName()}APIModelCreate apiModel)
	{
		return new ${modelBaseName()}DomainModelCreate(
<#list properties() as property>
<#if apiAndDomainTypesDiffer(property.baseType())>
			<#if property.optional()>
			apiModel.${property.getter()}().map(${property.baseType()?lower_case}APIToDomainConverter)<#if property_has_next>,</#if>
			<#else>
			${property.baseType()?lower_case}APIToDomainConverter.apply(apiModel.${property.getter()}())<#if property_has_next>,</#if>
			</#if>
<#else>
			apiModel.${property.getter()}()<#if property_has_next>,</#if>
</#if>
</#list>
		);
	}

	${modelBaseName()}APIToDomainCreateAdapter(<#list apiDomainDifferingGenericTypeParameters() as param>
			@Qualifier("${modelName()?uncap_first}${param}APIToDomainConverter") final Function<${apiConcreteType(param)}, ${domainConcreteType(param)}> ${param?lower_case}APIToDomainConverter<#if param_has_next>,</#if>
</#list>)
	{
<#list apiDomainDifferingGenericTypeParameters() as param>
		this.${param?lower_case}APIToDomainConverter = ${param?lower_case}APIToDomainConverter;
</#list>
	}
}