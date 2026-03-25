<#-- Template for generating SpringRestSaveController class -->
package ${basePackage()}.useCases.crud.save.api.web;

import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelCreate;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import ${basePackage()}.useCases.crud.common.security.${modelBaseName()}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.save.api.web.AbstractSpringRestSaveController;
import de.gupta.clean.crud.template.useCases.crud.save.api.web.SpringRestSaveController;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${modelBaseName()} Save" , description = "Save Operations pertaining to ${modelBaseName()}")
@RestController
@RequestMapping("/${modelBaseName()?lower_case}/save")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${modelBaseName()}EndpointSecurityPolicy.class)
class ${modelBaseName()}SpringRestSaveController extends
		AbstractSpringRestSaveController${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelResponse${">"}
		implements SpringRestSaveController${"<"}${modelBaseName()}APIModelCreate,
		${modelBaseName()}APIModelResponse${">"}
{
	${modelBaseName()}SpringRestSaveController(final SaveServiceFacade${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelResponse${">"} service)
	{
		super(service);
	}
}
