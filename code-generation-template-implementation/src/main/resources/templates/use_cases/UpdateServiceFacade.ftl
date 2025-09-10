<#-- Template for generating UpdateServiceFacade class -->
package ${basePackage}.useCases.crud.update.facade;

import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.domain.model.dto.${modelName}DomainModelUpdatePatch;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdateService;
import de.gupta.clean.crud.template.useCases.crud.update.facade.AbstractUpdateServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}UpdateServiceFacade extends
AbstractUpdateServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long,
${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse, Long${">"}
		implements UpdateServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
{
${modelName}UpdateServiceFacade(
		final UpdateService${"<"}${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse, Long${">"} service,
		final APIToDomainCreateAdapter${"<"}${modelName}APIModelCreate,
        ${modelName}DomainModelCreate${">"} createAdapter,
		final APIToDomainUpdateAdapter${"<"}${modelName}APIModelUpdatePatch, ${modelName}DomainModelUpdatePatch${">"} updateAdapter,
		final DomainToAPIResponseAdapter${"<"}${modelName}APIModelResponse, Long,
        ${modelName}DomainModelResponse${">"} responseAdapter,
final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter)
{
super(service, createAdapter, updateAdapter, responseAdapter, idAdapter);
}
}