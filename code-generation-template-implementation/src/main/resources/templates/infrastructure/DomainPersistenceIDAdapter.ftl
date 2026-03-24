<#-- Template for generating DomainPersistenceIDAdapter class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.adapter;

import ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelBaseName()}DomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.AbstractDomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelBaseName()}DomainPersistenceIDAdapter extends AbstractDomainPersistenceIDAdapter${"<"}Long, UUID${">"}
implements DomainPersistenceIDAdapter${"<"}Long, UUID${">"}
{
${modelBaseName()}DomainPersistenceIDAdapter(
final DomainPersistenceAdapterRepository${"<"}Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel${">"} repository)
{
super(repository);
}
}