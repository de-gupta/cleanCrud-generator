<#-- Template for generating SaveApplicationController class -->
package ${basePackage()}.useCases.crud.save.api.application;

import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelCreate;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.save.api.application.AbstractSaveApplicationController;
import de.gupta.clean.crud.template.useCases.crud.save.api.application.SaveApplicationController;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}SaveApplicationController extends
		AbstractSaveApplicationController${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelResponse${">"}
		implements SaveApplicationController${"<"}${modelBaseName()}APIModelCreate,
		${modelBaseName()}APIModelResponse${">"}
{
	${modelBaseName()}SaveApplicationController(final SaveServiceFacade${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelResponse${">"} service)
	{
		super(service);
	}
}