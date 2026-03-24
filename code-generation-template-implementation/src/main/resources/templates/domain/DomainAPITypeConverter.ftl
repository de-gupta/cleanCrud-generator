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
final class ${modelName()}${param}DomainToAPIConverter implements Function<${domainConcreteType(param)}, ${apiConcreteType(param)}>
{
	@Override
	public ${apiConcreteType(param)} apply(final ${domainConcreteType(param)} domainValue)
	{
		return null;
	}
}

@Component
@Qualifier("${beanNamePrefix()}${param}APIToDomainConverter")
final class ${modelName()}${param}APIToDomainConverter implements Function<${apiConcreteType(param)}, ${domainConcreteType(param)}>
{
	@Override
	public ${domainConcreteType(param)} apply(final ${apiConcreteType(param)} apiValue)
	{
		return null;
	}
}

</#list>