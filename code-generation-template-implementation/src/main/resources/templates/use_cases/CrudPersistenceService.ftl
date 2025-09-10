<#-- Template for generating CrudPersistenceService class -->
package ${basePackage}.useCases.crud.all.infrastructure.persistence.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.all.application.service.CrudPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.all.infrastructure.persistence.service.AbstractCrudPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.all.infrastructure.persistence.service.PersistenceModelCrudRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelName}CrudPersistenceService
extends AbstractCrudPersistenceService${"<"}Long, ${modelName}DomainModel, UUID, ${modelName}PersistenceModel${">"}
implements CrudPersistenceService${"<"}Long, ${modelName}DomainModel${">"}
{
${modelName}CrudPersistenceService(final PersistenceModelCrudRepository${"<"}${modelName}PersistenceModel, UUID${">"} repository,
final DomainPersistenceModelAdapter${"<"}${modelName}DomainModel, ${modelName}PersistenceModel${">"} modelAdapter,
@Qualifier("${modelName?uncap_first}DomainPersistenceIDAdapter") final DomainPersistenceIDAdapter${"<"}Long, UUID${">"} idAdapter,
@Qualifier("${modelName?uncap_first}DomainPersistenceIDManagement") final DomainPersistenceIDManagement${"<"}Long, UUID${">"} idManagement)
{
super(repository, modelAdapter, idAdapter, idManagement);
}
}