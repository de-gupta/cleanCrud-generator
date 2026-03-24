<#-- Template for generating PatchPolicy class -->
package ${basePackage()}.domain.service.crud;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import de.gupta.clean.crud.template.domain.service.crud.policy.AbstractPatchPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.ChangePolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.PatchPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}PatchPolicy extends AbstractPatchPolicy<${modelBaseName()}DomainModel>
		implements PatchPolicy<${modelBaseName()}DomainModel>
{
	${modelBaseName()}PatchPolicy(
			final ChangePolicy<${modelBaseName()}DomainModel> changePolicy,
			final DomainConstraintService<${modelBaseName()}DomainModel> domainConstraintService)
	{
		super(changePolicy, domainConstraintService);
	}
}