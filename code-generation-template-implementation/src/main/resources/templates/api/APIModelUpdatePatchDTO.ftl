<#-- Template for generating APIModelUpdatePatch DTO -->
package ${basePackage()}.useCases.crud.common.dto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
<#if apiModelImports()?has_content>
<#list apiModelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.Collection" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

public record ${modelBaseName()}APIModelUpdatePatch(
<#list standaloneProperties() as property>
    Optional<${boxedApiResolvedType(property.baseType())}> ${property.name()}<#if property_has_next || relationships()?has_content>,</#if>
</#list>
<#list relationships() as relationship>
    ${relationship.apiUpdateFieldType()} ${relationship.propertyName()},
    ${relationship.removeFieldType()} ${relationship.removeFieldName()}<#if relationship_has_next>,</#if>
</#list>
)
{
	public static ${modelBaseName()}APIModelUpdatePatch of(
<#list standaloneProperties() as property>
		final Optional<${boxedApiResolvedType(property.baseType())}> ${property.name()}<#if property_has_next || relationships()?has_content>,</#if>
</#list>
<#list relationships() as relationship>
		final ${relationship.apiUpdateFieldType()} ${relationship.propertyName()},
		final ${relationship.removeFieldType()} ${relationship.removeFieldName()}<#if relationship_has_next>,</#if>
</#list>
	)
	{
		return new ${modelBaseName()}APIModelUpdatePatch(
				<#list standaloneProperties() as property>${property.name()}<#if property_has_next || relationships()?has_content>, </#if></#list><#list relationships() as relationship>${relationship.propertyName()}, ${relationship.removeFieldName()}<#if relationship_has_next>, </#if></#list>);
	}

	public ${modelBaseName()}APIModelUpdatePatch
	{
<#list standaloneProperties() as property>
		${property.name()} = Optional.ofNullable(${property.name()}).orElse(Optional.empty());
</#list>
<#list relationships() as relationship>
		<#if relationship.many()>
		${relationship.propertyName()} = Optional.ofNullable(${relationship.propertyName()}).orElse(Optional.empty()).map(List::copyOf);
		${relationship.removeFieldName()} = Optional.ofNullable(${relationship.removeFieldName()}).map(List::copyOf).orElse(List.of());
		<#else>
		${relationship.propertyName()} = Optional.ofNullable(${relationship.propertyName()}).orElse(Optional.empty());
		</#if>
</#list>
	}

<#list relationships() as relationship>

	public record ${relationship.apiUpdatePatchItemSimpleType()}(
			Optional<Long> id,
			${relationship.updatePatchType()} patch
	)
	{
		public ${relationship.apiUpdatePatchItemSimpleType()}
		{
			id = Optional.ofNullable(id).orElse(Optional.empty());
		}
	}
</#list>
}


