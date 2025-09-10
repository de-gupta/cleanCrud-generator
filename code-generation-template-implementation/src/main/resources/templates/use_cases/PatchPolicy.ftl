<#-- Template for generating PatchPolicy class -->
package ${basePackage}.domain.service.crud;

import ${basePackage}.domain.model.${modelName}DomainModel;
import de.gupta.clean.crud.template.domain.service.crud.policy.AbstractPatchPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.ChangePolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.PatchPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}PatchPolicy extends AbstractPatchPolicy${"<"}${modelName}DomainModel${">"} implements PatchPolicy${"<"}${modelName}DomainModel${">"}
{
${modelName}PatchPolicy(final ChangePolicy${"<"}${modelName}DomainModel${">"} changePolicy,
final InsertionPolicy${"<"}${modelName}DomainModel${">"} insertionPolicy)
{
super(changePolicy, insertionPolicy);
}
}