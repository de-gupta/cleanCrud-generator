<#-- Template for generating LongDomainIDGenerator class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.service;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.service.AbstractLongDomainIDGenerator;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.service.DomainIDGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("${aggregate().beanNamePrefix()}LongDomainIDGenerator")
final class ${aggregate().baseName()}LongDomainIDGenerator extends AbstractLongDomainIDGenerator implements DomainIDGenerator${"<"}Long${">"}
{
}