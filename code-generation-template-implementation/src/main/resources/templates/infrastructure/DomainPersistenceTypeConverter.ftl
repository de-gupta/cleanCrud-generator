<#-- Template for generating type converters between domain and persistence models -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.model.converter;

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

<#if isGeneric() && persistenceGenericImports()?has_content>
<#list persistenceGenericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#list genericTypeParameters() as param>
@Component
@Qualifier("${beanNamePrefix()}${param}DomainToPersistenceConverter")
final class ${modelBaseName()}${param}DomainToPersistenceConverter implements Function<${domainConcreteType(param)}, ${persistenceConcreteType(param)}>
{
	@Override
	public ${persistenceConcreteType(param)} apply(final ${domainConcreteType(param)} domainValue)
	{
<#if persistenceAndDomainTypesDiffer(param)>
		// TODO from Template: implement conversion from ${domainConcreteType(param)} to ${persistenceConcreteType(param)}.
		throw new UnsupportedOperationException("TODO from Template: implement ${param} domain-to-persistence conversion");
<#else>
		// TODO from Template: replace this identity conversion if ${param} diverges semantically between domain and persistence.
		return domainValue;
</#if>
	}
}

@Component
@Qualifier("${beanNamePrefix()}${param}PersistenceToDomainConverter")
final class ${modelBaseName()}${param}PersistenceToDomainConverter implements Function<${persistenceConcreteType(param)}, ${domainConcreteType(param)}>
{
	@Override
	public ${domainConcreteType(param)} apply(final ${persistenceConcreteType(param)} persistenceValue)
	{
<#if persistenceAndDomainTypesDiffer(param)>
		// TODO from Template: implement conversion from ${persistenceConcreteType(param)} to ${domainConcreteType(param)}.
		throw new UnsupportedOperationException("TODO from Template: implement ${param} persistence-to-domain conversion");
<#else>
		// TODO from Template: replace this identity conversion if ${param} diverges semantically between persistence and domain.
		return persistenceValue;
</#if>
	}
}

</#list>
