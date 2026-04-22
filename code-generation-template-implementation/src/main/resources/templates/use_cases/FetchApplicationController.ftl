<#-- Template for generating ApplicationFetchController class -->
package ${aggregate().basePackage()}.useCases.crud.fetch.api.application;

import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.application.AbstractFetchApplicationController;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.application.FetchApplicationController;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}FetchApplicationController extends
AbstractFetchApplicationController${"<"}${aggregate().baseName()}APIModelResponse, Long${">"}
		implements FetchApplicationController${"<"}${aggregate().baseName()}APIModelResponse, Long${">"}
{
	${aggregate().baseName()}FetchApplicationController(final FetchServiceFacade${"<"}${aggregate().baseName()}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}