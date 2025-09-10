<#-- Template for generating SpringRestUpdateController class -->
package ${basePackage}.useCases.crud.update.api.web;

import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelUpdatePatch;
import ${basePackage}.useCases.crud.common.security.${modelName}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import de.gupta.clean.crud.template.useCases.crud.update.api.web.AbstractSpringRestUpdateController;
import de.gupta.clean.crud.template.useCases.crud.update.api.web.SpringRestUpdateController;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${modelName} Update" , description = "Update Operations pertaining to ${modelName}")
@RestController
@RequestMapping("/${modelName?lower_case}/update")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${modelName}EndpointSecurityPolicy.class)
class ${modelName}SpringRestUpdateController extends
AbstractSpringRestUpdateController${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
		implements SpringRestUpdateController${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
{
	${modelName}SpringRestUpdateController(final UpdateServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}