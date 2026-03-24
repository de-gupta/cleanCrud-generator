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
	}
}