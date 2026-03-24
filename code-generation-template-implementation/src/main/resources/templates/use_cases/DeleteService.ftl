<#-- Template for generating DeleteService class -->
package ${basePackage()}.useCases.crud.delete.application.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.crud.policy.DeletionPolicy;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.AbstractDeleteService;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeletePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeleteService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("${beanNamePrefix()}DeleteService")
final class ${modelBaseName()}DeleteService extends AbstractDeleteService${"<"}Long, ${modelBaseName()}DomainModel${">"} implements DeleteService${"<"}Long${">"}
{
${modelBaseName()}DeleteService(
final FetchPersistenceService${"<"}Long, ${modelBaseName()}DomainModel${">"} fetchService,
@Qualifier("${beanNamePrefix()}DeletePersistenceService") final DeletePersistenceService${"<"}Long${">"} persistenceService,
final DeletionPolicy${"<"}${modelBaseName()}DomainModel${">"} deletionPolicy,
final DomainSecurityPolicy${"<"}${modelBaseName()}DomainModel${">"} domainSecurityPolicy)
{
super(fetchService, persistenceService, deletionPolicy, domainSecurityPolicy);
}
}