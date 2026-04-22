<#-- Template for generating InsertionPolicy class -->
package ${aggregate().basePackage()}.domain.service.crud;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import de.gupta.clean.crud.template.domain.service.crud.policy.AbstractInsertionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}InsertionPolicy extends AbstractInsertionPolicy<${aggregate().baseName()}DomainModel>
		implements InsertionPolicy<${aggregate().baseName()}DomainModel>
{
	${aggregate().baseName()}InsertionPolicy(
			final DomainConstraintService<${aggregate().baseName()}DomainModel> domainConstraintService)
	{
		super(domainConstraintService);
	}
}