<#-- Template for generating DomainModelPatcher class -->
package ${basePackage}.domain.mapping.update;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.domain.mapping.update.DomainModelPatcher;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DomainModelPatcher implements DomainModelPatcher${"<"}${modelName}DomainModel, ${modelName}DomainModelUpdatePatch${">"}
{
private final ModelBuilderFactory${"<"}${modelName}DomainModel, ${modelName}DomainModel.${modelName}DomainModelBuilder${">"} modelBuilderFactory;

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

${modelName}DomainModelPatcher(
final ModelBuilderFactory${"<"}${modelName}DomainModel, ${modelName}DomainModel.${modelName}DomainModelBuilder${">"} modelBuilderFactory)
{
this.modelBuilderFactory = modelBuilderFactory;
}
}