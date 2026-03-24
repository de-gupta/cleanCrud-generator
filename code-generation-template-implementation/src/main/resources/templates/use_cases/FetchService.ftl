<#-- Template for generating FetchService class -->
package ${basePackage()}.useCases.crud.fetch.application.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.AbstractFetchService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchService;
import org.springframework.stereotype.Service;

@Service
final class ${modelBaseName()}FetchService extends
AbstractFetchService${"<"}Long, ${modelBaseName()}DomainModel${">"}
implements FetchService${"<"}${modelBaseName()}DomainModel, Long${">"}
{
${modelBaseName()}FetchService(
final FetchPersistenceService${"<"}Long, ${modelBaseName()}DomainModel${">"} persistenceService,
final DomainSecurityPolicy${"<"}${modelBaseName()}DomainModel${">"} domainSecurityPolicy)
{
super(persistenceService, domainSecurityPolicy);
}
}