<#-- Template for generating DomainModelCreate DTO -->
package ${basePackage()}.domain.model.dto;

import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
<#if domainModelImports()?has_content>
<#list domainModelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.Collection" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

public record ${modelBaseName()}DomainModelCreate(
<#list standaloneProperties() as property>
    ${domainPropertyType(property)} ${property.name()}<#if property_has_next || relationships()?has_content>,</#if>
</#list>
<#list relationships() as relationship>
    ${relationship.createFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>
)
{
	public static ${modelBaseName()}DomainModelCreate of(
<#list standaloneProperties() as property>
		final ${domainPropertyType(property)} ${property.name()}<#if property_has_next || relationships()?has_content>,</#if>
</#list>
<#list relationships() as relationship>
		final ${relationship.createFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>
	)
	{
		return new ${modelBaseName()}DomainModelCreate(
				<#list standaloneProperties() as property>${property.name()}<#if property_has_next || relationships()?has_content>, </#if></#list><#list relationships() as relationship>${relationship.propertyName()}<#if relationship_has_next>, </#if></#list>);
	}

	public static ${modelBaseName()}DomainModelCreate fromUpdatePatch(final ${modelBaseName()}DomainModelUpdatePatch updatePatch)
	{
		return new ${modelBaseName()}DomainModelCreate(
				<#list standaloneProperties() as property><#if property.optional()>updatePatch.${property.name()}()<#else>updatePatch.${property.name()}().orElseThrow()</#if><#if property_has_next || relationships()?has_content>, </#if></#list><#list relationships() as relationship><#if relationship.many()>List.of()<#elseif relationship.optional()>Optional.empty()<#else>null</#if><#if relationship_has_next>, </#if></#list>);
	}

	public ${modelBaseName()}DomainModelCreate
	{
<#list relationships() as relationship>
		<#if relationship.many()>
		${relationship.propertyName()} = ${relationship.propertyName()} == null ? List.of() : List.copyOf(${relationship.propertyName()});
		<#elseif relationship.optional()>
		${relationship.propertyName()} = Optional.ofNullable(${relationship.propertyName()}).orElse(Optional.empty());
		</#if>
</#list>
	}
}
