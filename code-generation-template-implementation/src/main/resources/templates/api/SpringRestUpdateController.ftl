<#-- Template for generating SpringRestUpdateController class -->
package ${aggregate().basePackage()}.useCases.crud.update.api.web;

import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelCreate;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelUpdatePatch;
import ${aggregate().basePackage()}.useCases.crud.common.security.${aggregate().baseName()}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import de.gupta.clean.crud.template.useCases.crud.update.api.web.AbstractSpringRestUpdateController;
import de.gupta.clean.crud.template.useCases.crud.update.api.web.SpringRestUpdateController;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${aggregate().baseName()} Update" , description = "Update Operations pertaining to ${aggregate().baseName()}")
@RestController
@RequestMapping("/${aggregate().baseName()?lower_case}/update")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${aggregate().baseName()}EndpointSecurityPolicy.class)
class ${aggregate().baseName()}SpringRestUpdateController extends
AbstractSpringRestUpdateController${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}APIModelResponse, Long${">"}
		implements SpringRestUpdateController${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}APIModelResponse, Long${">"}
{
	${aggregate().baseName()}SpringRestUpdateController(final UpdateServiceFacade${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}