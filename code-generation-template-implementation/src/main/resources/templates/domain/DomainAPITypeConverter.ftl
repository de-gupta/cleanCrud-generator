<#-- Template for generating type converters between domain and API models -->
package ${aggregate().basePackage()}.useCases.crud.common.adapter.converter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
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

<#list types().apiDomainDifferingParameters() as param>
@Component
@Qualifier("${aggregate().beanNamePrefix()}${param}DomainToAPIConverter")
final class ${aggregate().baseName()}${param}DomainToAPIConverter implements Function<${domain().concreteType(param)}, ${api().concreteType(param)}>
{
	@Override
	public ${api().concreteType(param)} apply(final ${domain().concreteType(param)} domainValue)
	{
<#if types().apiDomainDifferingParameters()?seq_contains(param)>
		// TODO from Template: implement conversion from ${domain().concreteType(param)} to ${api().concreteType(param)}.
		throw new UnsupportedOperationException("TODO from Template: implement ${param} domain-to-API conversion");
<#else>
		// TODO from Template: replace this identity conversion if ${param} diverges semantically between domain and API.
		return domainValue;
</#if>
	}
}

@Component
@Qualifier("${aggregate().beanNamePrefix()}${param}APIToDomainConverter")
final class ${aggregate().baseName()}${param}APIToDomainConverter implements Function<${api().concreteType(param)}, ${domain().concreteType(param)}>
{
	@Override
	public ${domain().concreteType(param)} apply(final ${api().concreteType(param)} apiValue)
	{
<#if types().apiDomainDifferingParameters()?seq_contains(param)>
		// TODO from Template: implement conversion from ${api().concreteType(param)} to ${domain().concreteType(param)}.
		throw new UnsupportedOperationException("TODO from Template: implement ${param} API-to-domain conversion");
<#else>
		// TODO from Template: replace this identity conversion if ${param} diverges semantically between API and domain.
		return apiValue;
</#if>
	}
}

</#list>

