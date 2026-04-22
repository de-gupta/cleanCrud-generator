<#-- Template for generating ExistingModelsConstraintService class -->
package ${aggregate().basePackage()}.domain.service.constraints;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.ConstraintResult;
import de.gupta.clean.crud.template.domain.service.constraints.ExistingModelsConstraintService;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}ExistingModelsConstraintService
		implements ExistingModelsConstraintService<${aggregate().baseName()}DomainModel>
{
	@Override
	public ConstraintResult mayThisResourceBeAdded(final ${aggregate().baseName()}DomainModel model)
	{
		// TODO from Template: add additional insertion-time checks against the existing model set if needed.
		return ConstraintResult.satisfied();
	}

	@Override
	public ConstraintResult mayThisResourceBeChangedTo(
			final ${aggregate().baseName()}DomainModel originalModel,
			final ${aggregate().baseName()}DomainModel newModel)
	{
		// TODO from Template: add additional update-time checks against the existing model set if needed.
		return ConstraintResult.satisfied();
	}
}