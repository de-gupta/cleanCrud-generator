<#-- Template for generating APIToDomainUpdateAdapter class -->
package ${basePackage}.useCases.crud.common.adapter;

import ${basePackage}.domain.model.dto.${modelName}DomainModelUpdatePatch;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelUpdatePatch;
<#if isGeneric>
import ${basePackage}.useCases.crud.common.adapter.converter.*;
</#if>
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.function.Function;

<#if isGeneric && domainGenericImports?has_content>
<#list domainGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

<#if isGeneric && apiGenericImports?has_content>
<#list apiGenericImports as import>
<#if import != "java.util.Optional">
import ${import};
</#if>
</#list>
</#if>

@Component
final class ${modelName}APIToDomainUpdateAdapter
implements APIToDomainUpdateAdapter${"<"}${modelName}APIModelUpdatePatch, ${modelName}DomainModelUpdatePatch${">"}
{
<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
	private final Function<${apiConcreteTypes[domainIndex]}, ${domainConcreteTypes[domainIndex]}> ${param?lower_case}APIToDomainConverter;
</#if>
</#list>
</#if>

	@Override
	public ${modelName}DomainModelUpdatePatch mapToDomainModelUpdatePatch(final ${modelName}APIModelUpdatePatch apiModel)
	{
		return new ${modelName}DomainModelUpdatePatch(
<#list properties as property>
<#if isGeneric && genericTypeParams?seq_contains(property.baseType)>
<#assign index = genericTypeParams?seq_index_of(property.baseType)>
<#if index < domainConcreteTypes?size && index < apiConcreteTypes?size>
			apiModel.${property.name}().map(${property.baseType?lower_case}APIToDomainConverter)<#if property_has_next>,</#if>
<#else>
			apiModel.${property.name}()<#if property_has_next>,</#if>
</#if>
<#else>
			apiModel.${property.name}()<#if property_has_next>,</#if>
</#if>
</#list>
		);
	}

	${modelName}APIToDomainUpdateAdapter(<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
			@Qualifier("${modelName?uncap_first}${param}APIToDomainConverter") final Function<${apiConcreteTypes[domainIndex]}, ${domainConcreteTypes[domainIndex]}> ${param?lower_case}APIToDomainConverter<#if param_has_next>,</#if>
</#if>
</#list>
</#if>)
	{
<#if isGeneric>
<#list genericTypeParams as param>
<#assign domainIndex = genericTypeParams?seq_index_of(param)>
<#if domainIndex < domainConcreteTypes?size && domainIndex < apiConcreteTypes?size>
		this.${param?lower_case}APIToDomainConverter = ${param?lower_case}APIToDomainConverter;
</#if>
</#list>
</#if>
	}
}