<#-- Template for generating SpringRestDeleteController class -->
package ${basePackage}.useCases.crud.delete.api.application;

import de.gupta.clean.crud.template.useCases.crud.delete.api.application.AbstractDeleteApplicationController;
import de.gupta.clean.crud.template.useCases.crud.delete.api.application.DeleteApplicationController;
import de.gupta.clean.crud.template.useCases.crud.delete.facade.DeleteServiceFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DeleteApplicationController extends AbstractDeleteApplicationController${"<"}Long${">"}
		implements DeleteApplicationController${"<"}Long${">"}
{
	${modelName}DeleteApplicationController(
			@Qualifier("${modelName?uncap_first}DeleteServiceFacade") final DeleteServiceFacade${"<"}Long${">"} service)
	{
		super(service);
	}
}