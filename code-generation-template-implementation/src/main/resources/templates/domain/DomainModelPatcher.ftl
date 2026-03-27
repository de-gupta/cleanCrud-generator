<#-- Template for generating DomainModelPatcher class -->
package ${basePackage()}.domain.mapping.update;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.domain.mapping.update.DomainModelPatcher;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DomainModelPatcher implements DomainModelPatcher${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelUpdatePatch${">"}
{
private final ModelBuilderFactory${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder${">"} modelBuilderFactory;

@Override
public ${modelBaseName()}DomainModel patchModel(final ${modelBaseName()}DomainModel originalModel,
final ${modelBaseName()}DomainModelUpdatePatch updatePatch)
{
final var builder = modelBuilderFactory.builder();
<#list properties() as property>
	<#if property.optional()>
if (updatePatch.${property.name()}().isPresent())
{
	builder.with${property.capitalizedName()}(updatePatch.${property.name()}().get());
}
else
{
	originalModel.${property.getter()}().ifPresent(builder::with${property.capitalizedName()});
}
	<#else>
builder.with${property.capitalizedName()}(updatePatch.${property.name()}().orElse(originalModel.${property.getter()}()));
	</#if>

</#list>
return builder.build();
}

${modelBaseName()}DomainModelPatcher(
final ModelBuilderFactory${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder${">"} modelBuilderFactory)
{
this.modelBuilderFactory = modelBuilderFactory;
}
}
