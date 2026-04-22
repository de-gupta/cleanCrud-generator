<#-- Template for generating DomainModelBuilder class -->
package ${aggregate().basePackage()}.domain.mapping.save;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import de.gupta.clean.crud.template.domain.mapping.save.DomainModelBuilder;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
final class ${aggregate().baseName()}DomainModelBuilder implements DomainModelBuilder${"<"}${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModel${">"}
{
private final ModelBuilderFactory${"<"}${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder${">"} modelBuilderFactory;

@Override
public ${aggregate().baseName()}DomainModel toModel(final ${aggregate().baseName()}DomainModelCreate domainModelCreate)
{
return modelBuilderFactory.builder()
<#list composition().standaloneProperties() as property>
.with${property.capitalizedName()}(domainModelCreate.${property.getter()}())
</#list>
<#list composition().relationships() as relationship>
	<#if relationship.many()>
.with${relationship.propertyCapitalizedName()}(List.of())
	<#elseif relationship.optional()>
.with${relationship.propertyCapitalizedName()}(Optional.empty())
	<#else>
.with${relationship.propertyCapitalizedName()}(null)
	</#if>
</#list>
.build();
}

${aggregate().baseName()}DomainModelBuilder(
final ModelBuilderFactory${"<"}${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder${">"} modelBuilderFactory)
{
this.modelBuilderFactory = modelBuilderFactory;
}
}
