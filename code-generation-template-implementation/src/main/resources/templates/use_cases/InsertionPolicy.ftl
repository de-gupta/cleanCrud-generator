<#-- Template for generating InsertionPolicy class -->
package ${basePackage}.domain.service.crud;

import ${basePackage}.domain.model.${modelName}DomainModel;
import de.gupta.clean.crud.template.domain.service.crud.policy.AbstractInsertionPolicy;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import de.gupta.clean.crud.template.domain.service.equality.DuplicateInsertionMessage;
import de.gupta.clean.crud.template.domain.service.existence.ResourceExistenceDetectionService;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}InsertionPolicy extends AbstractInsertionPolicy${"<"}${modelName}DomainModel${">"} implements InsertionPolicy${"<"}${modelName}DomainModel${">"}
{
${modelName}InsertionPolicy(
		final ResourceExistenceDetectionService${"<"}${modelName}DomainModel${">"} resourceExistenceDetectionService,
		final DuplicateInsertionMessage${"<"}${modelName}DomainModel${">"} duplicateInsertionMessage,
		final DomainConstraintService${"<"}${modelName}DomainModel${">"} domainConstraintService)
{
super(resourceExistenceDetectionService, duplicateInsertionMessage, domainConstraintService);
}
}