<#-- Template for generating APIModelUpdatePatch DTO -->
package ${aggregate().basePackage()}.useCases.crud.common.dto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
<#assign hasOwnedRelationships = composition().relationships()?filter(relationship -> relationship.owned())?size gt 0>
<#if hasOwnedRelationships>
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.standard.SatelliteUpdatePatchItem;
</#if>
<#if api().imports()?has_content>
<#list api().imports() as import>
<#if import != "java.util.Optional" && import != "java.util.Collection" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

public record ${aggregate().baseName()}APIModelUpdatePatch(
<#list composition().standaloneProperties() as property>
    Optional<${api().boxedResolvedType(property.baseType())}> ${property.name()}<#if property_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#list composition().relationships() as relationship>
    ${relationship.apiUpdateFieldType()} ${relationship.propertyName()},
    ${relationship.removeFieldType()} ${relationship.removeFieldName()}<#if relationship_has_next>,</#if>
</#list>
)
{
	public static ${aggregate().baseName()}APIModelUpdatePatch of(
<#list composition().standaloneProperties() as property>
		final Optional<${api().boxedResolvedType(property.baseType())}> ${property.name()}<#if property_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#list composition().relationships() as relationship>
		final ${relationship.apiUpdateFieldType()} ${relationship.propertyName()},
		final ${relationship.removeFieldType()} ${relationship.removeFieldName()}<#if relationship_has_next>,</#if>
</#list>
	)
	{
		return new ${aggregate().baseName()}APIModelUpdatePatch(
				<#list composition().standaloneProperties() as property>${property.name()}<#if property_has_next || composition().relationships()?has_content>, </#if></#list><#list composition().relationships() as relationship>${relationship.propertyName()}, ${relationship.removeFieldName()}<#if relationship_has_next>, </#if></#list>);
	}

	public ${aggregate().baseName()}APIModelUpdatePatch
	{
<#list composition().standaloneProperties() as property>
		${property.name()} = Optional.ofNullable(${property.name()}).orElse(Optional.empty());
</#list>
<#list composition().relationships() as relationship>
		<#if relationship.referenced()>
		${relationship.propertyName()} = Optional.ofNullable(${relationship.propertyName()}).orElse(Optional.empty())<#if relationship.many()>.map(List::copyOf)</#if>;
		<#else>
		${relationship.propertyName()} = Optional.ofNullable(${relationship.propertyName()}).orElse(Optional.empty()).map(List::copyOf);
		</#if>
		${relationship.removeFieldName()} = Optional.ofNullable(${relationship.removeFieldName()}).map(List::copyOf).orElse(List.of());
</#list>
	}
}
