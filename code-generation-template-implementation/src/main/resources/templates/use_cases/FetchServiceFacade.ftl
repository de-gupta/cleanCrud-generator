<#-- Template for generating FetchServiceFacade class -->
package ${basePackage}.useCases.crud.fetch.facade;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchService;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.AbstractFetchServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}FetchServiceFacade extends
AbstractFetchServiceFacade${"<"}${modelName}APIModelResponse, Long, ${modelName}DomainModel, ${modelName}DomainModelResponse, Long${">"}
		implements FetchServiceFacade${"<"}${modelName}APIModelResponse,
		Long${">"}
{
${modelName}FetchServiceFacade(final FetchService${"<"}${modelName}DomainModel, Long${">"} service,
		final DomainToAPIResponseAdapter${"<"}${modelName}APIModelResponse, Long, ${modelName}DomainModelResponse${">"} responseMapper,
final DomainResponseBuilder${"<"}${modelName}DomainModel, ${modelName}DomainModelResponse${">"} responseBuilder,
final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter)
{
super(service, responseMapper, responseBuilder, idAdapter);
}
}