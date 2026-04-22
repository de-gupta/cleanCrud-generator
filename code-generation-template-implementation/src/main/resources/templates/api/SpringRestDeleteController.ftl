<#-- Template for generating SpringRestDeleteController class -->
package ${aggregate().basePackage()}.useCases.crud.delete.api.web;

import de.gupta.clean.crud.template.useCases.crud.delete.api.web.AbstractSpringRestDeleteController;
import de.gupta.clean.crud.template.useCases.crud.delete.api.web.SpringRestDeleteController;
import de.gupta.clean.crud.template.useCases.crud.delete.facade.DeleteServiceFacade;
import de.gupta.clean.crud.template.useCases.crud.common.security.EndpointSecurityConfiguration;
import ${aggregate().basePackage()}.useCases.crud.common.security.${aggregate().baseName()}EndpointSecurityPolicy;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Qualifier;

@Tag(name = "${aggregate().baseName()} Delete" , description = "Delete Operations pertaining to ${aggregate().baseName()}")
@RestController
@RequestMapping("/${aggregate().baseName()?lower_case}/delete")
@EndpointSecurityConfiguration(enabled = true, endpointPolicy = ${aggregate().baseName()}EndpointSecurityPolicy.class)
class ${aggregate().baseName()}SpringRestDeleteController extends AbstractSpringRestDeleteController${"<"}Long${">"}
		implements SpringRestDeleteController${"<"}Long${">"}
{
	${aggregate().baseName()}SpringRestDeleteController(
			@Qualifier("${aggregate().beanNamePrefix()}DeleteServiceFacade") final DeleteServiceFacade${"<"}Long${">"} service)
	{
		super(service);
	}
}