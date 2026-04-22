<#-- Template for generating DomainConstraintService class -->
package ${aggregate().basePackage()}.infrastructure.persistence.service;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.AbstractDomainConstraintService;
import de.gupta.clean.crud.template.domain.service.constraints.ConstraintResult;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import de.gupta.clean.crud.template.domain.service.constraints.ExistingModelsConstraintService;
import de.gupta.clean.crud.template.domain.service.equality.DuplicateDefinition;
import de.gupta.clean.crud.template.domain.service.equality.DuplicateInsertionMessage;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Supplier;

@Component
final class ${aggregate().baseName()}DomainConstraintService
		extends AbstractDomainConstraintService<${aggregate().baseName()}DomainModel>
		implements DomainConstraintService${"<"}${aggregate().baseName()}DomainModel${">"}
{
	${aggregate().baseName()}DomainConstraintService(
			final DuplicateDefinition<${aggregate().baseName()}DomainModel> duplicateDefinition,
			final DuplicateInsertionMessage<${aggregate().baseName()}DomainModel> duplicateInsertionMessage,
			final ExistingModelsConstraintService<${aggregate().baseName()}DomainModel> existingModelsConstraintService,
			final Supplier<Collection<${aggregate().baseName()}DomainModel>> existingModelsSupplier)
	{
		super(duplicateDefinition, duplicateInsertionMessage, existingModelsConstraintService, existingModelsSupplier);
	}
}