<#-- Template for generating DomainModelCreate DTO -->
package ${aggregate().basePackage()}.domain.model.dto;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
<#if domain().imports()?has_content>
<#list domain().imports() as import>
<#if import != "java.util.Optional" && import != "java.util.Collection" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

public record ${aggregate().baseName()}DomainModelCreate(
<#list composition().standaloneProperties() as property>
    ${domain().propertyType(property)} ${property.name()}<#if property_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#list composition().relationships() as relationship>
    ${relationship.createFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>
)
{
	public static ${aggregate().baseName()}DomainModelCreate of(
<#list composition().standaloneProperties() as property>
		final ${domain().propertyType(property)} ${property.name()}<#if property_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#list composition().relationships() as relationship>
		final ${relationship.createFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>
	)
	{
		return new ${aggregate().baseName()}DomainModelCreate(
				<#list composition().standaloneProperties() as property>${property.name()}<#if property_has_next || composition().relationships()?has_content>, </#if></#list><#list composition().relationships() as relationship>${relationship.propertyName()}<#if relationship_has_next>, </#if></#list>);
	}

	public static ${aggregate().baseName()}DomainModelCreate fromUpdatePatch(final ${aggregate().baseName()}DomainModelUpdatePatch updatePatch)
	{
		return new ${aggregate().baseName()}DomainModelCreate(
				<#list composition().standaloneProperties() as property><#if property.optional()>updatePatch.${property.name()}()<#else>updatePatch.${property.name()}().orElseThrow()</#if><#if property_has_next || composition().relationships()?has_content>, </#if></#list><#list composition().relationships() as relationship><#if relationship.many()>List.of()<#elseif relationship.optional()>Optional.empty()<#else>null</#if><#if relationship_has_next>, </#if></#list>);
	}

	public ${aggregate().baseName()}DomainModelCreate
	{
<#list composition().relationships() as relationship>
		<#if relationship.many()>
		${relationship.propertyName()} = ${relationship.propertyName()} == null ? List.of() : List.copyOf(${relationship.propertyName()});
		<#elseif relationship.optional()>
		${relationship.propertyName()} = Optional.ofNullable(${relationship.propertyName()}).orElse(Optional.empty());
		</#if>
</#list>
	}
}
