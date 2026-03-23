<#-- Template for generating InsertionPolicy class -->
package ${basePackage}.domain.service.crud;

import ${basePackage}.domain.model.${modelName}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import de.gupta.clean.crud.template.domain.service.crud.policy.AbstractInsertionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}InsertionPolicy extends AbstractInsertionPolicy<${modelName}DomainModel>
		implements InsertionPolicy<${modelName}DomainModel>
{
	${modelName}InsertionPolicy(
			final DomainConstraintService<${modelName}DomainModel> domainConstraintService)
	{
		super(domainConstraintService);
	}
}