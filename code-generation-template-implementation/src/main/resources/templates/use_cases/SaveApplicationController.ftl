<#-- Template for generating SaveApplicationController class -->
package ${aggregate().basePackage()}.useCases.crud.save.api.application;

import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelCreate;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.save.api.application.AbstractSaveApplicationController;
import de.gupta.clean.crud.template.useCases.crud.save.api.application.SaveApplicationController;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}SaveApplicationController extends
		AbstractSaveApplicationController${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelResponse${">"}
		implements SaveApplicationController${"<"}${aggregate().baseName()}APIModelCreate,
		${aggregate().baseName()}APIModelResponse${">"}
{
	${aggregate().baseName()}SaveApplicationController(final SaveServiceFacade${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelResponse${">"} service)
	{
		super(service);
	}
}