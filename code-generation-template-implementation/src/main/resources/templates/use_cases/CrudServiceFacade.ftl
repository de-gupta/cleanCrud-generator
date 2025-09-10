<#-- Template for generating CrudServiceFacade class -->
package ${basePackage}.useCases.crud.all.facade;

import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.domain.model.dto.${modelName}DomainModelUpdatePatch;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.all.application.service.CrudService;
import de.gupta.clean.crud.template.useCases.crud.all.facade.AbstractCrudServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.all.facade.CrudServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.all.facade.adapter.model.CrudAPIDomainModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}CrudServiceFacade extends
AbstractCrudServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long,
${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse, Long${">"}
implements CrudServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
{
${modelName}CrudServiceFacade(
final CrudService${"<"}${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse, Long${">"} service,
final CrudAPIDomainModelAdapter${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse,
Long, ${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse${">"} modelAdapter,
final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter)
{
super(service, modelAdapter, idAdapter);
}
}