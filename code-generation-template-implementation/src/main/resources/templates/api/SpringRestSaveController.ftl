<#-- Template for generating SpringRestSaveController class -->
package ${aggregate().basePackage()}.useCases.crud.save.api.web;

import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelCreate;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.security.${aggregate().baseName()}EndpointSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.save.api.web.AbstractSpringRestSaveController;
import de.gupta.clean.crud.template.useCases.crud.save.api.web.SpringRestSaveController;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${aggregate().baseName()} Save" , description = "Save Operations pertaining to ${aggregate().baseName()}")
@RestController
@RequestMapping("/${aggregate().baseName()?lower_case}/save")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${aggregate().baseName()}EndpointSecurityPolicy.class)
class ${aggregate().baseName()}SpringRestSaveController extends
		AbstractSpringRestSaveController${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelResponse${">"}
		implements SpringRestSaveController${"<"}${aggregate().baseName()}APIModelCreate,
		${aggregate().baseName()}APIModelResponse${">"}
{
	${aggregate().baseName()}SpringRestSaveController(final SaveServiceFacade${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelResponse${">"} service)
	{
		super(service);
	}
}