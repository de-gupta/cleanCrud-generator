<#assign parentPackage = aggregate().basePackage()?keep_before_last(".")>
package ${parentPackage}.common.adapter.id;

import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.AbstractIdentityAPIDomainIDAdapter;
import org.springframework.stereotype.Component;

@Component
final class IdentityAPIDomainIDAdapter<ID> extends AbstractIdentityAPIDomainIDAdapter<ID>
		implements APIDomainIDAdapter<ID, ID>
{
}