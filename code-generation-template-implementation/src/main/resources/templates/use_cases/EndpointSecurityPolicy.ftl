<#-- Template for generating EndpointSecurityPolicy class -->
package ${basePackage()}.useCases.crud.common.security;

import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityPolicy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public final class ${modelBaseName()}EndpointSecurityPolicy implements EndpointSecurityPolicy
{
	@Override
	public boolean isAccessAllowed(final Method method, final Object[] args, final HttpServletRequest request)
	{
		return true;
	}
}