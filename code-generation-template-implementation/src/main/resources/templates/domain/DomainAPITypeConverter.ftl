<#-- Template for generating type converters between domain and API models -->
package ${basePackage()}.useCases.crud.common.adapter.converter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
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

<#list apiDomainDifferingGenericTypeParameters() as param>
@Component
@Qualifier("${beanNamePrefix()}${param}DomainToAPIConverter")
final class ${modelBaseName()}${param}DomainToAPIConverter implements Function<${domainConcreteType(param)}, ${apiConcreteType(param)}>
{
	@Override
	public ${apiConcreteType(param)} apply(final ${domainConcreteType(param)} domainValue)
	{
		// TODO from Template: implement conversion from ${domainConcreteType(param)} to ${apiConcreteType(param)}.
		throw new UnsupportedOperationException("TODO from Template: implement ${param} domain-to-API conversion");
	}
}

@Component
@Qualifier("${beanNamePrefix()}${param}APIToDomainConverter")
final class ${modelBaseName()}${param}APIToDomainConverter implements Function<${apiConcreteType(param)}, ${domainConcreteType(param)}>
{
	@Override
	public ${domainConcreteType(param)} apply(final ${apiConcreteType(param)} apiValue)
	{
		// TODO from Template: implement conversion from ${apiConcreteType(param)} to ${domainConcreteType(param)}.
		throw new UnsupportedOperationException("TODO from Template: implement ${param} API-to-domain conversion");
	}
}

</#list>
