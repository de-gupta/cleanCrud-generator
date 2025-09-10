<#-- Template for generating DomainPersistenceIDManagement class -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.adapter;

import ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelName}DomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.AbstractDomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.service.DomainIDGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelName}DomainPersistenceIDManagement
extends AbstractDomainPersistenceIDManagement${"<"}Long, UUID, ${modelName}DomainPersistenceAdapterModel${">"}
implements DomainPersistenceIDManagement${"<"}Long, UUID${">"}
{
${modelName}DomainPersistenceIDManagement(
final DomainPersistenceAdapterRepository${"<"}Long, UUID, ${modelName}DomainPersistenceAdapterModel${">"} repository,
final ModelBuilderFactory${"<"}DomainPersistenceAdapterModel${"<"}Long, UUID${">"},
DomainPersistenceAdapterModel.Builder${"<"}Long, UUID, ${modelName}DomainPersistenceAdapterModel${">"}${">"} modelBuilderFactory,
@Qualifier("${modelName?uncap_first}LongDomainIDGenerator") final DomainIDGenerator${"<"}Long${">"} domainIDGenerator)
{
super(repository, modelBuilderFactory, domainIDGenerator);
}
}