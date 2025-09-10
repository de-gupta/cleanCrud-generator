<#-- Template for generating UpdateApplicationController class -->
package ${basePackage}.useCases.crud.update.api.application;

import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.update.api.application.AbstractUpdateApplicationController;
import de.gupta.clean.crud.template.useCases.crud.update.api.application.UpdateApplicationController;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}UpdateApplicationController extends
AbstractUpdateApplicationController${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
		implements UpdateApplicationController${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
{
	${modelName}UpdateApplicationController(final UpdateServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}