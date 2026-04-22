<#-- Template for generating DuplicateInsertionMessage class -->
package ${aggregate().basePackage()}.domain.service.crud;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.equality.DuplicateInsertionMessage;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}DuplicateInsertionMessage implements DuplicateInsertionMessage${"<"}${aggregate().baseName()}DomainModel${">"}
{
	@Override
	public String messageIfModelAlreadyExists(final ${aggregate().baseName()}DomainModel ${aggregate().beanNamePrefix()}DomainModel)
	{
		// TODO from Template: customize this duplicate message for the business key your API should expose.
		return "The ${aggregate().baseName()?lower_case} with ${composition().properties()[0].name()} `" + ${aggregate().beanNamePrefix()}DomainModel.${composition().properties()[0].getter()}() + "` already exists";
	}
}