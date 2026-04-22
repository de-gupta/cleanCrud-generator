<#-- Template for generating DomainModelPatcher class -->
package ${aggregate().basePackage()}.domain.mapping.update;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.domain.mapping.update.DomainModelPatcher;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}DomainModelPatcher implements DomainModelPatcher${"<"}${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelUpdatePatch${">"}
{
private final ModelBuilderFactory${"<"}${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder${">"} modelBuilderFactory;

@Override
public ${aggregate().baseName()}DomainModel patchModel(final ${aggregate().baseName()}DomainModel originalModel,
final ${aggregate().baseName()}DomainModelUpdatePatch updatePatch)
{
return modelBuilderFactory.builder()
<#list composition().standaloneProperties() as property>
	<#if property.optional()>
.with${property.capitalizedName()}(updatePatch.${property.name()}().isPresent() ? updatePatch.${property.name()}() : originalModel.${property.getter()}())
	<#else>
.with${property.capitalizedName()}(updatePatch.${property.name()}().orElse(originalModel.${property.getter()}()))
	</#if>

</#list>
<#list composition().relationships() as relationship>
.with${relationship.propertyCapitalizedName()}(originalModel.${relationship.propertyName()}())
</#list>
.build();
}

${aggregate().baseName()}DomainModelPatcher(
final ModelBuilderFactory${"<"}${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder${">"} modelBuilderFactory)
{
this.modelBuilderFactory = modelBuilderFactory;
}
}
