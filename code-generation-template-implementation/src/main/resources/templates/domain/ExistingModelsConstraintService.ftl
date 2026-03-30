<#-- Template for generating ExistingModelsConstraintService class -->
package ${basePackage()}.domain.service.constraints;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.ConstraintResult;
import de.gupta.clean.crud.template.domain.service.constraints.ExistingModelsConstraintService;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}ExistingModelsConstraintService
		implements ExistingModelsConstraintService<${modelBaseName()}DomainModel>
{
	@Override
	public ConstraintResult mayThisResourceBeAdded(final ${modelBaseName()}DomainModel model)
	{
		// TODO from Template: add additional insertion-time checks against the existing model set if needed.
		return ConstraintResult.satisfied();
	}

	@Override
	public ConstraintResult mayThisResourceBeChangedTo(
			final ${modelBaseName()}DomainModel originalModel,
			final ${modelBaseName()}DomainModel newModel)
	{
		// TODO from Template: add additional update-time checks against the existing model set if needed.
		return ConstraintResult.satisfied();
	}
}