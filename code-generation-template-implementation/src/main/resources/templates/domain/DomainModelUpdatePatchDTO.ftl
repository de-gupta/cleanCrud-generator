<#-- Template for generating DomainModelUpdatePatch DTO -->
package ${aggregate().basePackage()}.domain.model.dto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
<#assign hasOwnedRelationships = composition().relationships()?filter(relationship -> relationship.owned())?size gt 0>
<#if hasOwnedRelationships>
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.standard.SatelliteUpdatePatchItem;
</#if>
<#if domain().imports()?has_content>
<#list domain().imports() as import>
<#if import != "java.util.Optional" && import != "java.util.Collection" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

public record ${aggregate().baseName()}DomainModelUpdatePatch(
<#list composition().standaloneProperties() as property>
    Optional<${domain().boxedResolvedType(property.baseType())}> ${property.name()}<#if property_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#list composition().relationships() as relationship>
    ${relationship.domainUpdateFieldType()} ${relationship.propertyName()},
    ${relationship.removeFieldType()} ${relationship.removeFieldName()}<#if relationship_has_next>,</#if>
</#list>
)
{
	public static ${aggregate().baseName()}DomainModelUpdatePatch of(
<#list composition().standaloneProperties() as property>
		final Optional<${domain().boxedResolvedType(property.baseType())}> ${property.name()}<#if property_has_next || composition().relationships()?has_content>,</#if>
</#list>
<#list composition().relationships() as relationship>
		final ${relationship.domainUpdateFieldType()} ${relationship.propertyName()},
		final ${relationship.removeFieldType()} ${relationship.removeFieldName()}<#if relationship_has_next>,</#if>
</#list>
	)
	{
		return new ${aggregate().baseName()}DomainModelUpdatePatch(
				<#list composition().standaloneProperties() as property>${property.name()}<#if property_has_next || composition().relationships()?has_content>, </#if></#list><#list composition().relationships() as relationship>${relationship.propertyName()}, ${relationship.removeFieldName()}<#if relationship_has_next>, </#if></#list>);
	}

	public ${aggregate().baseName()}DomainModelUpdatePatch
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
