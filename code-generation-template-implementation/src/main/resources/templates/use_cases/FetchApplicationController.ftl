<#-- Template for generating ApplicationFetchController class -->
package ${basePackage}.useCases.crud.fetch.api.application;

import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.application.AbstractFetchApplicationController;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.application.FetchApplicationController;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}FetchApplicationController extends
AbstractFetchApplicationController${"<"}${modelName}APIModelResponse, Long${">"}
		implements FetchApplicationController${"<"}${modelName}APIModelResponse, Long${">"}
{
	${modelName}FetchApplicationController(final FetchServiceFacade${"<"}${modelName}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}