<#-- Template for generating InsertionPolicy class -->
package ${basePackage()}.domain.service.crud;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import de.gupta.clean.crud.template.domain.service.crud.policy.AbstractInsertionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}InsertionPolicy extends AbstractInsertionPolicy<${modelBaseName()}DomainModel>
		implements InsertionPolicy<${modelBaseName()}DomainModel>
{
	${modelBaseName()}InsertionPolicy(
			final DomainConstraintService<${modelBaseName()}DomainModel> domainConstraintService)
	{
		super(domainConstraintService);
	}
}