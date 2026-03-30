<#-- Template for generating SpringRestFetchController class -->
package ${basePackage()}.useCases.crud.fetch.api.web;

import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import ${basePackage()}.useCases.crud.common.security.${modelBaseName()}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.web.AbstractSpringRestFetchController;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.web.SpringRestFetchController;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${modelBaseName()} Fetch" , description = "Fetch Operations pertaining to ${modelBaseName()}")
@RestController
@RequestMapping("/${modelBaseName()?lower_case}/fetch")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${modelBaseName()}EndpointSecurityPolicy.class)
class ${modelBaseName()}SpringRestFetchController extends
AbstractSpringRestFetchController${"<"}${modelBaseName()}APIModelResponse, Long${">"}
		implements SpringRestFetchController${"<"}${modelBaseName()}APIModelResponse, Long${">"}
{
	${modelBaseName()}SpringRestFetchController(final FetchServiceFacade${"<"}${modelBaseName()}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}