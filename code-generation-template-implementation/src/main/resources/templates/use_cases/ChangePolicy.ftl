<#-- Template for generating ChangePolicy class -->
package ${basePackage}.domain.service.crud;

import ${basePackage}.domain.model.${modelName}DomainModel;
import de.gupta.clean.crud.template.domain.model.exceptions.resource.ResourceCannotBePatchedException;
import de.gupta.clean.crud.template.domain.service.crud.policy.ChangePolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}ChangePolicy implements ChangePolicy${"<"}${modelName}DomainModel${">"}
{
@Override
public void validateChangeAttempt(final ${modelName}DomainModel originalModel, final ${modelName}DomainModel updatedModel)
{
	// TODO from template: Add your custom validation logic here
// Example:
// if (originalModel.someProperty().contains("important") && !updatedModel.someProperty().contains("important"))
// {
//     throw ResourceCannotBePatchedException.withMessage("An important item cannot be made unimportant");
// }
}
}