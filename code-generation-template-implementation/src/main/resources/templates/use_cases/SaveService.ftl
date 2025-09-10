<#-- Template for generating SaveService class -->
package ${basePackage}.useCases.crud.save.application.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.mapping.save.DomainModelBuilder;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.AbstractSaveService;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SavePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SaveService;
import org.springframework.stereotype.Service;

@Service
final class ${modelName}SaveService
		extends
		AbstractSaveService${"<"}${modelName}DomainModel, ${modelName}DomainModelCreate, ${modelName}DomainModelResponse, Long${">"}
		implements SaveService${"<"}${modelName}DomainModelCreate, ${modelName}DomainModelResponse, Long${">"}
{
    ${modelName}SaveService(final SavePersistenceService${"<"}Long, ${modelName}DomainModel${">"} persistenceService,
							final DomainModelBuilder${"<"}${modelName}DomainModelCreate,
                            ${modelName}DomainModel${">"} modelBuilder,
							final DomainResponseBuilder${"<"}${modelName}DomainModel,
                            ${modelName}DomainModelResponse${">"} responseModelMapper,
							final InsertionPolicy${"<"}${modelName}DomainModel${">"} insertionPolicy,
							final DomainSecurityPolicy${"<"}${modelName}DomainModel${">"} domainSecurityPolicy)
	{
		super(persistenceService, modelBuilder, responseModelMapper, insertionPolicy, domainSecurityPolicy);
	}
}