<#-- Template for generating UpdateApplicationController class -->
package ${basePackage()}.useCases.crud.update.api.application;

import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelCreate;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelResponse;
import ${basePackage()}.useCases.crud.common.dto.${modelBaseName()}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.update.api.application.AbstractUpdateApplicationController;
import de.gupta.clean.crud.template.useCases.crud.update.api.application.UpdateApplicationController;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}UpdateApplicationController extends
AbstractUpdateApplicationController${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}APIModelResponse, Long${">"}
		implements UpdateApplicationController${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}APIModelResponse, Long${">"}
{
	${modelBaseName()}UpdateApplicationController(final UpdateServiceFacade${"<"}${modelBaseName()}APIModelCreate, ${modelBaseName()}APIModelUpdatePatch, ${modelBaseName()}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}