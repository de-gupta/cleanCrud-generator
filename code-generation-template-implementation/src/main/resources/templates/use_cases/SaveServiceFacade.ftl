<#-- Template for generating SaveServiceFacade class -->
package ${basePackage()}.useCases.crud.save.facade;

import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelCreate;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SaveService;
import de.gupta.clean.crud.template.useCases.crud.save.facade.AbstractSaveServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}SaveServiceFacade extends
AbstractSaveServiceFacade${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelResponse,
${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelResponse, Long${">"}
implements SaveServiceFacade${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelResponse${">"}
{
${modelBaseName()}SaveServiceFacade(
final SaveService${"<"}${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelResponse, Long${">"} service,
final APIToDomainCreateAdapter${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}DomainModelCreate${">"} createMapper,
final DomainToAPIResponseAdapter${"<"}${modelBaseName()}APIModelResponse, Long, ${modelBaseName()}DomainModelResponse${">"} responseMapper)
{
super(service, createMapper, responseMapper);
}
}