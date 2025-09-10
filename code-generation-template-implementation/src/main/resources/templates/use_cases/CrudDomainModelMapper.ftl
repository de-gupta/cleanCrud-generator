<#-- Template for generating CrudDomainModelMapperImpl class -->
package ${basePackage}.domain.mapping;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.domain.model.dto.${modelName}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.domain.mapping.CrudDomainModelMapper;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}CrudDomainModelMapper implements
CrudDomainModelMapper${"<"}${modelName}DomainModel, ${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse${">"}
{
private final ModelBuilderFactory${"<"}${modelName}DomainModel, ${modelName}DomainModel.${modelName}DomainModelBuilder${">"}
modelBuilderFactory;

@Override
public ${modelName}DomainModelResponse toResponse(final ${modelName}DomainModel ${modelName?uncap_first}DomainModel)
{
return ${modelName}DomainModelResponse.fromDomainModel(${modelName?uncap_first}DomainModel);
}

@Override
public ${modelName}DomainModel toModel(final ${modelName}DomainModelCreate ${modelName?uncap_first}DomainModelCreate)
{
return modelBuilderFactory.builder()
<#list properties as property>
    .with${property.capitalizedName}(${modelName?uncap_first}DomainModelCreate.${property.getter}())<#if property_has_next>
</#if></#list>
.build();
}

@Override
public ${modelName}DomainModel patchModel(final ${modelName}DomainModel originalModel,
final ${modelName}DomainModelUpdatePatch updatePatch)
{
return modelBuilderFactory.builder()
<#list properties as property>
    <#if property.optional>
        .with${property.capitalizedName}(updatePatch.${property.name}().isPresent() ?
        updatePatch.${property.name}() :
        originalModel.${property.getter}())
    <#else>
        .with${property.capitalizedName}(updatePatch.${property.name}().orElse(originalModel.${property.getter}()))
    </#if><#if property_has_next>

</#if>
</#list>
.build();
}

${modelName}CrudDomainModelMapper(
final ModelBuilderFactory${"<"}${modelName}DomainModel, ${modelName}DomainModel.${modelName}DomainModelBuilder${">"} modelBuilderFactory)
{
this.modelBuilderFactory = modelBuilderFactory;
}
}