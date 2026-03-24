<#-- Template for generating ApplicationFetchController class -->
package ${basePackage()}.useCases.crud.fetch.api.application;

import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.application.AbstractFetchApplicationController;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.application.FetchApplicationController;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}FetchApplicationController extends
AbstractFetchApplicationController${"<"}${modelBaseName()}APIModelResponse, Long${">"}
		implements FetchApplicationController${"<"}${modelBaseName()}APIModelResponse, Long${">"}
{
	${modelBaseName()}FetchApplicationController(final FetchServiceFacade${"<"}${modelBaseName()}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}