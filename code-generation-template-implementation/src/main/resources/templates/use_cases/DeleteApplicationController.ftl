<#-- Template for generating SpringRestDeleteController class -->
package ${aggregate().basePackage()}.useCases.crud.delete.api.application;

import de.gupta.clean.crud.template.useCases.crud.delete.api.application.AbstractDeleteApplicationController;
import de.gupta.clean.crud.template.useCases.crud.delete.api.application.DeleteApplicationController;
import de.gupta.clean.crud.template.useCases.crud.delete.facade.DeleteServiceFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}DeleteApplicationController extends AbstractDeleteApplicationController${"<"}Long${">"}
		implements DeleteApplicationController${"<"}Long${">"}
{
	${aggregate().baseName()}DeleteApplicationController(
			@Qualifier("${aggregate().beanNamePrefix()}DeleteServiceFacade") final DeleteServiceFacade${"<"}Long${">"} service)
	{
		super(service);
	}
}