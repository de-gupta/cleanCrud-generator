<#-- Template for generating DuplicateInsertionMessage class -->
package ${basePackage()}.domain.service.crud;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.equality.DuplicateInsertionMessage;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DuplicateInsertionMessage implements DuplicateInsertionMessage${"<"}${modelBaseName()}DomainModel${">"}
{
@Override
public String messageIfModelAlreadyExists(final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel)
{
// TODO from Template: write custom logic here
return "The ${modelName()?lower_case} with ${properties()[0].name()} `" + ${beanNamePrefix()}DomainModel.${properties()[0].getter()}() + "` already exists";
}
}