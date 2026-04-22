<#-- Template for generating PatchPolicy class -->
package ${aggregate().basePackage()}.domain.service.crud;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import de.gupta.clean.crud.template.domain.service.crud.policy.AbstractPatchPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.ChangePolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.PatchPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}PatchPolicy extends AbstractPatchPolicy<${aggregate().baseName()}DomainModel>
		implements PatchPolicy<${aggregate().baseName()}DomainModel>
{
	${aggregate().baseName()}PatchPolicy(
			final ChangePolicy<${aggregate().baseName()}DomainModel> changePolicy,
			final DomainConstraintService<${aggregate().baseName()}DomainModel> domainConstraintService)
	{
		super(changePolicy, domainConstraintService);
	}
}