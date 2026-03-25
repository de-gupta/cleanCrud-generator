<#-- Template for generating SpringRestUpdateController class -->
package ${basePackage()}.useCases.crud.update.api.web;

import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelCreate;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelUpdatePatch;
import ${basePackage()}.useCases.crud.common.security.${modelBaseName()}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import de.gupta.clean.crud.template.useCases.crud.update.api.web.AbstractSpringRestUpdateController;
import de.gupta.clean.crud.template.useCases.crud.update.api.web.SpringRestUpdateController;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${modelBaseName()} Update" , description = "Update Operations pertaining to ${modelBaseName()}")
@RestController
@RequestMapping("/${modelBaseName()?lower_case}/update")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${modelBaseName()}EndpointSecurityPolicy.class)
class ${modelBaseName()}SpringRestUpdateController extends
AbstractSpringRestUpdateController${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}APIModelResponse, Long${">"}
		implements SpringRestUpdateController${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}APIModelResponse, Long${">"}
{
	${modelBaseName()}SpringRestUpdateController(final UpdateServiceFacade${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}
