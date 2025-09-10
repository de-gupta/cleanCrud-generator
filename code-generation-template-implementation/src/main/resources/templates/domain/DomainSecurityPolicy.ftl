<#-- Template for generating FetchService class -->
package ${basePackage}.domain.service.security;

import ${basePackage}.domain.model.${modelName}DomainModel;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DomainSecurityPolicy implements DomainSecurityPolicy${"<"}${modelName}DomainModel${">"}
{
@Override
public boolean isAccessAllowed(final ${modelName}DomainModel domainModel)
{
return true;
}
}