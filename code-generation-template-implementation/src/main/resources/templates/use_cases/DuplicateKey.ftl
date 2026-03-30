<#-- Template for generating typed duplicate key record -->
package ${basePackage()}.domain.service.equality;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
<#list domainModelImports() as importName>
import ${importName};
</#list>

// TODO from Template: adjust this generated duplicate key record if your business duplicate semantics differ.
record ${duplicateKeyTypeName()}(
<#if requiredProperties()?size == 0>
		${modelBaseName()}DomainModel model
<#else>
<#list requiredProperties() as property>
		${domainPropertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
</#if>
) {}