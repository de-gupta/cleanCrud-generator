<#-- Template for generating SpringRestDeleteController class -->
package ${basePackage}.useCases.crud.delete.api.web;

import de.gupta.clean.crud.template.useCases.crud.delete.api.web.AbstractSpringRestDeleteController;
import de.gupta.clean.crud.template.useCases.crud.delete.api.web.SpringRestDeleteController;
import de.gupta.clean.crud.template.useCases.crud.delete.facade.DeleteServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import ${basePackage}.useCases.crud.common.security.${modelName}EndpointSecurityPolicy;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Qualifier;

@Tag(name = "${modelName} Delete" , description = "Delete Operations pertaining to ${modelName}")
@RestController
@RequestMapping("/${modelName?lower_case}/delete")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${modelName}EndpointSecurityPolicy.class)
class ${modelName}SpringRestDeleteController extends AbstractSpringRestDeleteController${"<"}Long${">"}
		implements SpringRestDeleteController${"<"}Long${">"}
{
	${modelName}SpringRestDeleteController(
			@Qualifier("${modelName?uncap_first}DeleteServiceFacade") final DeleteServiceFacade${"<"}Long${">"} service)
	{
		super(service);
	}
}