<#-- Template for generating ResourceExistenceDetectionService class -->
package ${basePackage}.infrastructure.persistence.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.infrastructure.persistence.repository.${modelName}JpaRepository;
import de.gupta.clean.crud.template.domain.service.existence.ResourceExistenceDetectionService;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}ResourceExistenceDetectionService implements ResourceExistenceDetectionService${"<"}${modelName}DomainModel${">"}
{
	// TODO for template: change it to query repository
private final ${modelName}JpaRepository repository;

@Override
public boolean existsByModel(final ${modelName}DomainModel domainModel)
{
// TODO from Template: write custom logic here
return false;
}

${modelName}ResourceExistenceDetectionService(final ${modelName}JpaRepository repository)
{
this.repository = repository;
}
}