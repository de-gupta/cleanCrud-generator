<#-- Template for generating FetchService class -->
package ${aggregate().basePackage()}.domain.service.security;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}DomainSecurityPolicy implements DomainSecurityPolicy${"<"}${aggregate().baseName()}DomainModel${">"}
{
	@Override
	public boolean isAccessAllowed(final ${aggregate().baseName()}DomainModel domainModel)
	{
		// TODO from Template: replace this permissive default with real domain visibility rules.
		// Example: return currentUserCanSee(domainModel);
		return true;
	}
}