<#-- Template for generating SaveServiceFacade class -->
package ${basePackage}.useCases.crud.save.facade;

import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SaveService;
import de.gupta.clean.crud.template.useCases.crud.save.facade.AbstractSaveServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}SaveServiceFacade extends
AbstractSaveServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelResponse,
${modelName}DomainModelCreate, ${modelName}DomainModelResponse, Long${">"}
implements SaveServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelResponse${">"}
{
${modelName}SaveServiceFacade(
final SaveService${"<"}${modelName}DomainModelCreate, ${modelName}DomainModelResponse, Long${">"} service,
final APIToDomainCreateAdapter${"<"}${modelName}APIModelCreate, ${modelName}DomainModelCreate${">"} createMapper,
final DomainToAPIResponseAdapter${"<"}${modelName}APIModelResponse, Long, ${modelName}DomainModelResponse${">"} responseMapper)
{
super(service, createMapper, responseMapper);
}
}