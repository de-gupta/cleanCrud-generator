<#-- Template for generating FetchService class -->
package ${basePackage()}.domain.service.security;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DomainSecurityPolicy implements DomainSecurityPolicy${"<"}${modelBaseName()}DomainModel${">"}
{
@Override
public boolean isAccessAllowed(final ${modelBaseName()}DomainModel domainModel)
{
return true;
}
}