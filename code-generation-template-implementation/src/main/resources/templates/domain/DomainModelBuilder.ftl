<#-- Template for generating DomainModelBuilder class -->
package ${basePackage}.domain.mapping.save;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import de.gupta.clean.crud.template.domain.mapping.save.DomainModelBuilder;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DomainModelBuilder implements DomainModelBuilder${"<"}${modelName}DomainModelCreate, ${modelName}DomainModel${">"}
{
private final ModelBuilderFactory${"<"}${modelName}DomainModel, ${modelName}DomainModel.${modelName}DomainModelBuilder${">"} modelBuilderFactory;

@Override
public ${modelName}DomainModel toModel(final ${modelName}DomainModelCreate domainModelCreate)
{
return modelBuilderFactory.builder()
<#list properties as property>
    .with${property.capitalizedName()}(domainModelCreate.${property.getter()}())<#if property_has_next>
</#if></#list>
.build();
}

${modelName}DomainModelBuilder(
final ModelBuilderFactory${"<"}${modelName}DomainModel, ${modelName}DomainModel.${modelName}DomainModelBuilder${">"} modelBuilderFactory)
{
this.modelBuilderFactory = modelBuilderFactory;
}
}