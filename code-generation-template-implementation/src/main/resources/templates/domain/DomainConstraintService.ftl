<#-- Template for generating DomainConstraintService class -->
package ${basePackage()}.infrastructure.persistence.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.ConstraintResult;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DomainConstraintService implements DomainConstraintService${"<"}${modelBaseName()}DomainModel${">"}
{
	@Override
	public ConstraintResult validateForInsertion(final ${modelBaseName()}DomainModel model)
	{
		return ConstraintResult.satisfied();
	}

	@Override
	public ConstraintResult validateForUpdate(
			final ${modelBaseName()}DomainModel originalModel,
			final ${modelBaseName()}DomainModel updatedModel)
	{
		return ConstraintResult.satisfied();
	}
}