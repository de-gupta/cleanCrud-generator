<#-- Template for generating DomainModelBuilder class -->
package ${basePackage()}.domain.mapping.save;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import de.gupta.clean.crud.template.domain.mapping.save.DomainModelBuilder;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DomainModelBuilder implements DomainModelBuilder${"<"}${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModel${">"}
{
private final ModelBuilderFactory${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder${">"} modelBuilderFactory;

@Override
public ${modelBaseName()}DomainModel toModel(final ${modelBaseName()}DomainModelCreate domainModelCreate)
{
final var builder = modelBuilderFactory.builder();
<#list properties() as property>
	<#if property.optional()>
domainModelCreate.${property.getter()}().ifPresent(builder::with${property.capitalizedName()});
	<#else>
builder.with${property.capitalizedName()}(domainModelCreate.${property.getter()}());
	</#if>
</#list>
return builder.build();
}

${modelBaseName()}DomainModelBuilder(
final ModelBuilderFactory${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder${">"} modelBuilderFactory)
{
this.modelBuilderFactory = modelBuilderFactory;
}
}
