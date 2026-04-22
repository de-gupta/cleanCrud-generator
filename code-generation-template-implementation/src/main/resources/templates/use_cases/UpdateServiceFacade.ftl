<#-- Template for generating UpdateServiceFacade class -->
package ${aggregate().basePackage()}.useCases.crud.update.facade;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelCreate;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdateService;
import de.gupta.clean.crud.template.useCases.crud.update.facade.AbstractUpdateServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}UpdateServiceFacade extends
AbstractUpdateServiceFacade${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}APIModelResponse, Long,
${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse, Long${">"}
		implements UpdateServiceFacade${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}APIModelResponse, Long${">"}
{
${aggregate().baseName()}UpdateServiceFacade(
		final UpdateService${"<"}${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse, Long${">"} service,
		final APIToDomainCreateAdapter${"<"}${aggregate().baseName()}APIModelCreate,
        ${aggregate().baseName()}DomainModelCreate${">"} createAdapter,
		final APIToDomainUpdateAdapter${"<"}${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}DomainModelUpdatePatch${">"} updateAdapter,
		final DomainToAPIResponseAdapter${"<"}${aggregate().baseName()}APIModelResponse, Long,
        ${aggregate().baseName()}DomainModelResponse${">"} responseAdapter,
final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter)
{
super(service, createAdapter, updateAdapter, responseAdapter, idAdapter);
}
}