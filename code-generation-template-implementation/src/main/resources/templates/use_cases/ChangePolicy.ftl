package ${aggregate().basePackage()}.domain.service.crud;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.crud.policy.ChangePolicy;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}ChangePolicy implements ChangePolicy<${aggregate().baseName()}DomainModel>
{
	@Override
	public void validateChangeAttempt(final ${aggregate().baseName()}DomainModel originalModel, final ${aggregate().baseName()}DomainModel updatedModel)
	{
		// TODO from Template: validate whether the change from originalModel to updatedModel is allowed.
		// Example: prevent changes to immutable business fields or state transitions.
	}
}
