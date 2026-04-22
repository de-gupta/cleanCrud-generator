<#-- Template for generating DuplicateDefinition implementation -->
package ${aggregate().basePackage()}.domain.service.equality;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.equality.KeyBasedDuplicateDefinition;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}DuplicateDefinition
		implements KeyBasedDuplicateDefinition<${aggregate().baseName()}DomainModel, ${aggregate().duplicateKeyTypeName()}>
{
	@Override
	public ${aggregate().duplicateKeyTypeName()} duplicateKeyOf(final ${aggregate().baseName()}DomainModel model)
	{
		// TODO from Template: replace this default duplicate key with the business key your API should use.
		// TODO from Template: if key-based duplicate detection does not fit this domain, delete this class and implement DuplicateDefinition directly.
		return new ${aggregate().duplicateKeyTypeName()}(
<#if composition().requiredProperties()?size == 0>
				model
<#else>
<#assign firstRequired = true>
<#list composition().requiredProperties() as property>
				<#if !firstRequired>,</#if>model.${property.getter()}()<#assign firstRequired = false>
</#list>
</#if>
		);
	}
}