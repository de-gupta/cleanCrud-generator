<#-- Template for generating APIModelCreate DTO -->
package ${aggregate().basePackage()}.useCases.crud.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
<#if api().imports()?has_content>
<#list api().imports() as import>
<#if import != "java.util.Optional" && import != "java.util.Collection" && import != "java.util.List">
import ${import};
</#if>
</#list>
</#if>

public record ${aggregate().baseName()}APIModelCreate(
<#assign emitted = false>
<#list composition().standaloneProperties() as property>
    <#if !property.optional()>
        <#if property.baseType() == "String">
            @NotBlank(message = "${property.capitalizedName()} is required")
        <#else>
            @NotNull(message = "${property.capitalizedName()} is required")
        </#if>
    </#if>
    ${api().propertyType(property)} ${property.name()}<#if property_has_next || composition().relationships()?has_content>,</#if>
    <#assign emitted = true>
</#list>
<#list composition().relationships() as relationship>
    ${relationship.createFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>
)
{
	public ${aggregate().baseName()}APIModelCreate
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
