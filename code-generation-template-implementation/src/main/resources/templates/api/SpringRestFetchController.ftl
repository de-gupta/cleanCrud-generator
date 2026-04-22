<#-- Template for generating SpringRestFetchController class -->
package ${aggregate().basePackage()}.useCases.crud.fetch.api.web;

import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.security.${aggregate().baseName()}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.web.AbstractSpringRestFetchController;
import de.gupta.clean.crud.template.useCases.crud.fetch.api.web.SpringRestFetchController;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import de.gupta.clean.crud.template.useCases.crud.fetch.facade.FetchServiceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${aggregate().baseName()} Fetch" , description = "Fetch Operations pertaining to ${aggregate().baseName()}")
@RestController
@RequestMapping("/${aggregate().baseName()?lower_case}/fetch")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${aggregate().baseName()}EndpointSecurityPolicy.class)
class ${aggregate().baseName()}SpringRestFetchController extends
AbstractSpringRestFetchController${"<"}${aggregate().baseName()}APIModelResponse, Long${">"}
		implements SpringRestFetchController${"<"}${aggregate().baseName()}APIModelResponse, Long${">"}
{
	${aggregate().baseName()}SpringRestFetchController(final FetchServiceFacade${"<"}${aggregate().baseName()}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}