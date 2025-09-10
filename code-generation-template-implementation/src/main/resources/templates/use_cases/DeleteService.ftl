<#-- Template for generating DeleteService class -->
package ${basePackage}.useCases.crud.delete.application.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import de.gupta.clean.crud.template.domain.service.crud.policy.DeletionPolicy;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.AbstractDeleteService;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeletePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeleteService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("${modelName?uncap_first}DeleteService")
final class ${modelName}DeleteService extends AbstractDeleteService${"<"}Long, ${modelName}DomainModel${">"} implements DeleteService${"<"}Long${">"}
{
${modelName}DeleteService(
final FetchPersistenceService${"<"}Long, ${modelName}DomainModel${">"} fetchService,
@Qualifier("${modelName?uncap_first}DeletePersistenceService") final DeletePersistenceService${"<"}Long${">"} persistenceService,
final DeletionPolicy${"<"}${modelName}DomainModel${">"} deletionPolicy,
final DomainSecurityPolicy${"<"}${modelName}DomainModel${">"} domainSecurityPolicy)
{
super(fetchService, persistenceService, deletionPolicy, domainSecurityPolicy);
}
}