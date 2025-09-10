<#-- Template for generating FetchService class -->
package ${basePackage}.useCases.crud.fetch.application.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.AbstractFetchService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchService;
import org.springframework.stereotype.Service;

@Service
final class ${modelName}FetchService extends
AbstractFetchService${"<"}Long, ${modelName}DomainModel${">"}
implements FetchService${"<"}${modelName}DomainModel, Long${">"}
{
${modelName}FetchService(
final FetchPersistenceService${"<"}Long, ${modelName}DomainModel${">"} persistenceService,
final DomainSecurityPolicy${"<"}${modelName}DomainModel${">"} domainSecurityPolicy)
{
super(persistenceService, domainSecurityPolicy);
}
}