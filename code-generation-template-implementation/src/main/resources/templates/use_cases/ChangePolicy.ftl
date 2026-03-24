package ${basePackage()}.domain.service.crud;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.crud.policy.ChangePolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}ChangePolicy implements ChangePolicy<${modelBaseName()}DomainModel>
{
	@Override
	public void validateChangeAttempt(final ${modelBaseName()}DomainModel originalModel, final ${modelBaseName()}DomainModel updatedModel)
	{
		// TODO from Template: validate whether the change from originalModel to updatedModel is allowed.
		// Example: prevent changes to immutable business fields or state transitions.
	}
}
