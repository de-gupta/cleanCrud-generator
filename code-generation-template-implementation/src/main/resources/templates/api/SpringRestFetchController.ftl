<#-- Template for generating SpringRestFetchController class -->
package ${basePackage}.useCases.crud.fetch.api.web;

import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.security.${modelName}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.web.AbstractSpringRestFetchController;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.web.SpringRestFetchController;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${modelName} Fetch" , description = "Fetch Operations pertaining to ${modelName}")
@RestController
@RequestMapping("/${modelName?lower_case}/fetch")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${modelName}EndpointSecurityPolicy.class)
class ${modelName}SpringRestFetchController extends
AbstractSpringRestFetchController${"<"}${modelName}APIModelResponse, Long${">"}
		implements SpringRestFetchController${"<"}${modelName}APIModelResponse, Long${">"}
{
	${modelName}SpringRestFetchController(final FetchServiceFacade${"<"}${modelName}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}