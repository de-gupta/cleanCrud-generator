<#-- Template for generating SpringRestSaveController class -->
package ${basePackage}.useCases.crud.save.api.web;

import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.security.${modelName}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.save.api.web.AbstractSpringRestSaveController;
import de.gupta.clean.crud.template.useCases.crud.save.api.web.SpringRestSaveController;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${modelName} Save" , description = "Save Operations pertaining to ${modelName}")
@RestController
@RequestMapping("/${modelName?lower_case}/save")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${modelName}EndpointSecurityPolicy.class)
class ${modelName}SpringRestSaveController extends
		AbstractSpringRestSaveController${"<"}${modelName}APIModelCreate, ${modelName}APIModelResponse${">"}
		implements SpringRestSaveController${"<"}${modelName}APIModelCreate,
		${modelName}APIModelResponse${">"}
{
	${modelName}SpringRestSaveController(final SaveServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelResponse${">"} service)
	{
		super(service);
	}
}