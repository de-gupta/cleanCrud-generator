<#-- Template for generating CrudService class -->
package ${basePackage}.useCases.crud.all.application.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.domain.model.dto.${modelName}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.domain.mapping.CrudDomainModelMapper;
import de.gupta.clean.crud.template.domain.service.crud.policy.DeletionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import de.gupta.clean.crud.template.useCases.crud.all.application.service.AbstractCrudService;
import de.gupta.clean.crud.template.useCases.crud.all.application.service.CrudPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.all.application.service.CrudService;
import org.springframework.stereotype.Service;

@Service
final class ${modelName}CrudService extends
AbstractCrudService${"<"}${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse, Long, ${modelName}DomainModel${">"}
implements CrudService${"<"}${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse, Long${">"}
{
${modelName}CrudService(final CrudPersistenceService${"<"}Long, ${modelName}DomainModel${">"} persistenceService,
final CrudDomainModelMapper${"<"}${modelName}DomainModel, ${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse${">"} modelMapper,
final InsertionPolicy${"<"}${modelName}DomainModel${">"} insertionPolicy,
final DeletionPolicy${"<"}${modelName}DomainModel${">"} deletionPolicy)
{
super(persistenceService, modelMapper, insertionPolicy, deletionPolicy);
}
}