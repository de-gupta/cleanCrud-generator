<#-- Template for generating per-module audit actor supplier -->
package ${aggregate().basePackage()}.infrastructure.persistence.audit;

import de.gupta.clean.crud.template.infrastructure.persistence.history.audit.AuditActor;
import de.gupta.clean.crud.template.infrastructure.persistence.history.service.AuditActorSupplier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("${aggregate().beanNamePrefix()}AuditActorSupplier")
public final class ${aggregate().baseName()}AuditActorSupplier implements AuditActorSupplier
{
	@Override
	public AuditActor get()
	{
		// TODO return the current audit actor for this module, for example from a security or request context.
		return null;
	}
}
