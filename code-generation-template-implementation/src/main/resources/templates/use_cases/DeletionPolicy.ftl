<#-- Template for generating DeletionPolicy class -->
package ${aggregate().basePackage()}.domain.service.crud;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.model.exceptions.resource.ResourceCannotBeDeletedException;
import de.gupta.clean.crud.template.domain.service.crud.policy.DeletionPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}DeletionPolicy implements DeletionPolicy${"<"}${aggregate().baseName()}DomainModel${">"}
{
	@Override
	public void validateDeletion(final ${aggregate().baseName()}DomainModel ${aggregate().beanNamePrefix()})
	{
		// TODO from Template: add custom deletion guards here.
		// Example:
		// if (${aggregate().beanNamePrefix()}.someProperty().contains("important"))
		// {
		//     throw ResourceCannotBeDeletedException.withMessage("An important item cannot be deleted");
		// }
	}
}
