<#-- Template for generating DomainConstraintService class -->
package ${basePackage()}.infrastructure.persistence.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
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
final class ${modelBaseName()}DomainConstraintService
		extends AbstractDomainConstraintService<${modelBaseName()}DomainModel>
		implements DomainConstraintService${"<"}${modelBaseName()}DomainModel${">"}
{
	${modelBaseName()}DomainConstraintService(
			final DuplicateDefinition<${modelBaseName()}DomainModel> duplicateDefinition,
			final DuplicateInsertionMessage<${modelBaseName()}DomainModel> duplicateInsertionMessage,
			final ExistingModelsConstraintService<${modelBaseName()}DomainModel> existingModelsConstraintService,
			final Supplier<Collection<${modelBaseName()}DomainModel>> existingModelsSupplier)
	{
		super(duplicateDefinition, duplicateInsertionMessage, existingModelsConstraintService, existingModelsSupplier);
	}
}