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

<#list persistenceDomainDifferingGenericTypeParameters() as param>
@Component
@Qualifier("${modelName()?uncap_first}${param}DomainToPersistenceConverter")
final class ${modelName()}${param}DomainToPersistenceConverter implements Function<${domainConcreteType(param)}, ${persistenceConcreteType(param)}>
{
	@Override
	public ${persistenceConcreteType(param)} apply(final ${domainConcreteType(param)} domainValue)
	{
		return null;
	}
}

@Component
@Qualifier("${modelName()?uncap_first}${param}PersistenceToDomainConverter")
final class ${modelName()}${param}PersistenceToDomainConverter implements Function<${persistenceConcreteType(param)}, ${domainConcreteType(param)}>
{
	@Override
	public ${domainConcreteType(param)} apply(final ${persistenceConcreteType(param)} persistenceValue)
	{
		return null;
	}
}

</#list>