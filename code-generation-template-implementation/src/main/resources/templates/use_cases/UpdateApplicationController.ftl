<#-- Template for generating UpdateApplicationController class -->
package ${aggregate().basePackage()}.useCases.crud.update.api.application;

import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelCreate;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelResponse;
import ${aggregate().basePackage()}.useCases.crud.common.dto.${aggregate().baseName()}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.update.api.application.AbstractUpdateApplicationController;
import de.gupta.clean.crud.template.useCases.crud.update.api.application.UpdateApplicationController;
import de.gupta.clean.crud.template.useCases.crud.update.facade.UpdateServiceFacade;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}UpdateApplicationController extends
AbstractUpdateApplicationController${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}APIModelResponse, Long${">"}
		implements UpdateApplicationController${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}APIModelResponse, Long${">"}
{
	${aggregate().baseName()}UpdateApplicationController(final UpdateServiceFacade${"<"}${aggregate().baseName()}APIModelCreate, ${aggregate().baseName()}APIModelUpdatePatch, ${aggregate().baseName()}APIModelResponse, Long${">"} service)
	{
		super(service);
	}
}