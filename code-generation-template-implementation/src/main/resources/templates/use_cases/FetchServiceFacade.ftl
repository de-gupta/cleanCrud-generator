<#-- Template for generating FetchServiceFacade class -->
package ${basePackage()}.useCases.crud.fetch.facade;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchService;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.AbstractFetchServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}FetchServiceFacade extends
AbstractFetchServiceFacade${"<"}${modelBaseName()}APIModelResponse, Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelResponse, Long${">"}
		implements FetchServiceFacade${"<"}${modelBaseName()}APIModelResponse,
		Long${">"}
{
${modelBaseName()}FetchServiceFacade(final FetchService${"<"}${modelBaseName()}DomainModel, Long${">"} service,
		final DomainToAPIResponseAdapter${"<"}${modelBaseName()}APIModelResponse, Long, ${modelBaseName()}DomainModelResponse${">"} responseMapper,
final DomainResponseBuilder${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelResponse${">"} responseBuilder,
final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter)
{
super(service, responseMapper, responseBuilder, idAdapter);
}
}