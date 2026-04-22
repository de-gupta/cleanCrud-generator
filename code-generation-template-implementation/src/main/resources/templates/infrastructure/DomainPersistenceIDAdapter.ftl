<#-- Template for generating DomainPersistenceIDAdapter class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.adapter;

import ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${aggregate().baseName()}DomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.AbstractDomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${aggregate().baseName()}DomainPersistenceIDAdapter extends AbstractDomainPersistenceIDAdapter${"<"}Long, UUID${">"}
implements DomainPersistenceIDAdapter${"<"}Long, UUID${">"}
{
${aggregate().baseName()}DomainPersistenceIDAdapter(
final DomainPersistenceAdapterRepository${"<"}Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel${">"} repository)
{
super(repository);
}
}