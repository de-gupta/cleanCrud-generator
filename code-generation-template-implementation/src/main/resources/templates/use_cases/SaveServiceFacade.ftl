<#-- Template for generating SaveServiceFacade class -->
package ${aggregate().basePackage()}.useCases.crud.save.facade;

import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelCreate;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SaveService;
import de.gupta.clean.crud.template.useCases.crud.save.facade.AbstractSaveServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}SaveServiceFacade extends
AbstractSaveServiceFacade${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelResponse,
${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelResponse, Long${">"}
implements SaveServiceFacade${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelResponse${">"}
{
${aggregate().baseName()}SaveServiceFacade(
final SaveService${"<"}${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelResponse, Long${">"} service,
final APIToDomainCreateAdapter${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}DomainModelCreate${">"} createMapper,
final DomainToAPIResponseAdapter${"<"}${aggregate().baseName()}APIModelResponse, Long, ${aggregate().baseName()}DomainModelResponse${">"} responseMapper)
{
super(service, createMapper, responseMapper);
}
}