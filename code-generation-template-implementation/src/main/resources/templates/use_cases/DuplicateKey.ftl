<#-- Template for generating typed duplicate key record -->
package ${aggregate().basePackage()}.domain.service.equality;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
<#list domain().imports() as importName>
import ${importName};
</#list>

// TODO from Template: adjust this generated duplicate key record if your business duplicate semantics differ.
record ${aggregate().duplicateKeyTypeName()}(
<#if composition().requiredProperties()?size == 0>
		${aggregate().baseName()}DomainModel model
<#else>
<#list composition().requiredProperties() as property>
		${domain().propertyType(property)} ${property.name()}<#if property_has_next>,</#if>
</#list>
</#if>
) {}