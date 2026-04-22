<#-- Template for generating FetchServiceFacade class -->
package ${aggregate().basePackage()}.useCases.crud.fetch.facade;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.id.APIDomainIDAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchService;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.AbstractFetchServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}FetchServiceFacade extends
AbstractFetchServiceFacade${"<"}${aggregate().baseName()}APIModelResponse, Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelResponse, Long${">"}
		implements FetchServiceFacade${"<"}${aggregate().baseName()}APIModelResponse,
		Long${">"}
{
${aggregate().baseName()}FetchServiceFacade(final FetchService${"<"}${aggregate().baseName()}DomainModel, Long${">"} service,
		final DomainToAPIResponseAdapter${"<"}${aggregate().baseName()}APIModelResponse, Long, ${aggregate().baseName()}DomainModelResponse${">"} responseMapper,
final DomainResponseBuilder${"<"}${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelResponse${">"} responseBuilder,
final APIDomainIDAdapter${"<"}Long, Long${">"} idAdapter)
{
super(service, responseMapper, responseBuilder, idAdapter);
}
}