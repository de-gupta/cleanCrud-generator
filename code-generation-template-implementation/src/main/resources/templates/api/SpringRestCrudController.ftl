<#-- Template for generating SpringRestController class -->
package ${basePackage}.useCases.crud.all.api.web;

import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelCreate;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelResponse;
import ${basePackage}.useCases.crud.common.dto.${modelName}APIModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.all.api.web.AbstractSpringRestCrudController;
import de.gupta.clean.crud.template.useCases.crud.all.api.web.SpringRestCrudController;
import de.gupta.clean.crud.template.useCases.crud.all.facade.CrudServiceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${modelName} Crud API", description = "Crud Operations pertaining to ${modelName}")
@RestController
@RequestMapping("/${modelName?lower_case}")
@Deprecated
class ${modelName}SpringRestCrudController extends AbstractSpringRestCrudController${"<"}${modelName}APIModelCreate,
${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
		implements SpringRestCrudController${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"}
{
${modelName}SpringRestCrudController(
		final CrudServiceFacade${"<"}${modelName}APIModelCreate, ${modelName}APIModelUpdatePatch, ${modelName}APIModelResponse, Long${">"} service)
{
super(service);
}
}