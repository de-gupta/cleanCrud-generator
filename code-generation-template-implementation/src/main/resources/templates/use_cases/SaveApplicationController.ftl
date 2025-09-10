<#-- Template for generating SaveApplicationController class -->
package ${basePackage}.useCases.crud.save.api.application;

import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import de.gupta.clean.crud.template.useCases.crud.save.api.application.AbstractSaveApplicationController;
import de.gupta.clean.crud.template.useCases.crud.save.api.application.SaveApplicationController;
import de.gupta.clean.crud.template.useCases.crud.save.facade.SaveServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}SaveApplicationController extends
		AbstractSaveApplicationController${"<"}${modelName}APIModelCreate, ${modelName}APIModelResponse${">"}
		implements SaveApplicationController${"<"}${modelName}APIModelCreate,
		${modelName}APIModelResponse${">"}
{
	${modelName}SaveApplicationController(final SaveServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelResponse${">"} service)
	{
		super(service);
	}
}