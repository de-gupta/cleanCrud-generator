<#-- Template for generating UpdateServiceFacade class -->
package ${basePackage()}.useCases.crud.update.facade;

import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelCreate;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdateService;
import de.gupta.clean.crud.template.useCases.crud.update.facade.AbstractUpdateServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}UpdateServiceFacade extends
AbstractUpdateServiceFacade${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}APIModelResponse, Long,
${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse, Long${">"}
		implements UpdateServiceFacade${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}APIModelResponse, Long${">"}
{
${modelBaseName()}UpdateServiceFacade(
		final UpdateService${"<"}${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse, Long${">"} service,
		final APIToDomainCreateAdapter${"<"}${modelBaseName()}APIModelCreate,
        ${modelBaseName()}DomainModelCreate${">"} createAdapter,
		final APIToDomainUpdateAdapter${"<"}${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}DomainModelUpdatePatch${">"} updateAdapter,
		final DomainToAPIResponseAdapter${"<"}${modelBaseName()}APIModelResponse, Long,
        ${modelBaseName()}DomainModelResponse${">"} responseAdapter,
final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter)
{
super(service, createAdapter, updateAdapter, responseAdapter, idAdapter);
}
}