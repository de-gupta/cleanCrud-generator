<#-- Template for generating DeletionPolicy class -->
package ${basePackage}.domain.service.crud;

import ${basePackage}.domain.model.${modelName}DomainModel;
import de.gupta.clean.crud.template.domain.model.exceptions.resource.ResourceCannotBeDeletedException;
import de.gupta.clean.crud.template.domain.service.crud.policy.DeletionPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DeletionPolicy implements DeletionPolicy${"<"}${modelName}DomainModel${">"}
{
@Override
public void validateDeletion(final ${modelName}DomainModel ${modelName?uncap_first})
{
// TODO from Template: Add your custom validation logic here
// Example:
// if (${modelName?uncap_first}.someProperty().contains("important"))
// {
//     throw ResourceCannotBeDeletedException.withMessage("An important item cannot be deleted");
// }
}
}