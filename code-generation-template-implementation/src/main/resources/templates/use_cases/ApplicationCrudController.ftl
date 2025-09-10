<#-- Template for generating SpringRestController class -->
package ${basePackage}.useCases.crud.all.api.application;

import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.all.api.application.AbstractApplicationCrudController;
import de.gupta.clean.crud.template.useCases.crud.all.api.application.ApplicationCrudController;
import de.gupta.clean.crud.template.useCases.crud.all.facade.CrudServiceFacade;

@Deprecated
class ${modelName}ApplicationCrudController extends AbstractApplicationCrudController${"<"}${modelName}APIModelCreate,
${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
		implements ApplicationCrudController${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
{
${modelName}ApplicationCrudController(
		final CrudServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"} service)
{
super(service);
}
}