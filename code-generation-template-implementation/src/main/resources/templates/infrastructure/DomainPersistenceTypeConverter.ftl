<#-- Template for generating type converters between domain and persistence models -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.model.converter;

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

<#if types().isGeneric() && persistence().genericImports()?has_content>
<#list persistence().genericImports() as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#list types().parameters() as param>
@Component
@Qualifier("${aggregate().beanNamePrefix()}${param}DomainToPersistenceConverter")
final class ${aggregate().baseName()}${param}DomainToPersistenceConverter implements Function<${domain().concreteType(param)}, ${persistence().concreteType(param)}>
{
	@Override
	public ${persistence().concreteType(param)} apply(final ${domain().concreteType(param)} domainValue)
	{
<#if types().persistenceDomainDifferingParameters()?seq_contains(param)>
		// TODO from Template: implement conversion from ${domain().concreteType(param)} to ${persistence().concreteType(param)}.
		throw new UnsupportedOperationException("TODO from Template: implement ${param} domain-to-persistence conversion");
<#else>
		// TODO from Template: replace this identity conversion if ${param} diverges semantically between domain and persistence.
		return domainValue;
</#if>
	}
}

@Component
@Qualifier("${aggregate().beanNamePrefix()}${param}PersistenceToDomainConverter")
final class ${aggregate().baseName()}${param}PersistenceToDomainConverter implements Function<${persistence().concreteType(param)}, ${domain().concreteType(param)}>
{
	@Override
	public ${domain().concreteType(param)} apply(final ${persistence().concreteType(param)} persistenceValue)
	{
<#if types().persistenceDomainDifferingParameters()?seq_contains(param)>
		// TODO from Template: implement conversion from ${persistence().concreteType(param)} to ${domain().concreteType(param)}.
		throw new UnsupportedOperationException("TODO from Template: implement ${param} persistence-to-domain conversion");
<#else>
		// TODO from Template: replace this identity conversion if ${param} diverges semantically between persistence and domain.
		return persistenceValue;
</#if>
	}
}

</#list>
