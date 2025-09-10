<#-- Template for generating ResourceExistenceDetectionService class -->
package ${basePackage}.infrastructure.persistence.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.infrastructure.persistence.repository.${modelName}JpaRepository;
import de.gupta.clean.crud.template.domain.service.constraints.DomainConstraintService;
import de.gupta.clean.crud.template.domain.service.constraints.ConstraintResult;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DomainConstraintService implements DomainConstraintService${"<"}${modelName}DomainModel${">"}
{
private final ${modelName}JpaRepository repository;

@Override
public ConstraintResult mayThisResourceBeAdded(final ${modelName}DomainModel domainModel)
{
// TODO from Template: write custom logic here
return ConstraintResult.satisfied();
}
${modelName}DomainConstraintService(final ${modelName}JpaRepository repository)
{
this.repository = repository;
}
}