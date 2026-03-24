<#-- Template for generating DeletionPolicy class -->
package ${basePackage()}.domain.service.crud;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.model.exceptions.resource.ResourceCannotBeDeletedException;
import de.gupta.clean.crud.template.domain.service.crud.policy.DeletionPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DeletionPolicy implements DeletionPolicy${"<"}${modelBaseName()}DomainModel${">"}
{
@Override
public void validateDeletion(final ${modelBaseName()}DomainModel ${beanNamePrefix()})
{
// TODO from Template: Add your custom validation logic here
// Example:
// if (${beanNamePrefix()}.someProperty().contains("important"))
// {
//     throw ResourceCannotBeDeletedException.withMessage("An important item cannot be deleted");
// }
}
}