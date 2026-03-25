<#-- Template for generating DuplicateDefinition implementation -->
package ${basePackage()}.domain.service.equality;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.equality.AbstractKeyBasedDuplicateDefinition;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DuplicateDefinition
		extends AbstractKeyBasedDuplicateDefinition<${modelBaseName()}DomainModel, ${duplicateKeyTypeName()}>
{
	@Override
	public ${duplicateKeyTypeName()} duplicateKeyOf(final ${modelBaseName()}DomainModel model)
	{
		// TODO from Template: replace this default duplicate key with the business key your API should use.
		// TODO from Template: if key-based duplicate detection does not fit this domain, delete this class and implement DuplicateDefinition directly.
		return new ${duplicateKeyTypeName()}(
<#if requiredProperties()?size == 0>
				model
<#else>
<#assign firstRequired = true>
<#list requiredProperties() as property>
				<#if !firstRequired>,</#if>model.${property.getter()}()<#assign firstRequired = false>
</#list>
</#if>
		);
	}
}
