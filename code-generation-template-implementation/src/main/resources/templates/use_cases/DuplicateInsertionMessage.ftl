<#-- Template for generating DuplicateInsertionMessage class -->
package ${basePackage}.domain.service.crud;

import ${basePackage}.domain.model.${modelName}DomainModel;
import de.gupta.clean.crud.template.domain.service.equality.DuplicateInsertionMessage;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DuplicateInsertionMessage implements DuplicateInsertionMessage${"<"}${modelName}DomainModel${">"}
{
@Override
public String messageIfModelAlreadyExists(final ${modelName}DomainModel ${modelName?uncap_first}DomainModel)
{
// TODO from Template: write custom logic here
return "The ${modelName?lower_case} with ${properties[0].name} `" + ${modelName?uncap_first}DomainModel.${properties[0].getter}() + "` already exists";
}
}