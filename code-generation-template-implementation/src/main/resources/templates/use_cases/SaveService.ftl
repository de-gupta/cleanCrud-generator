<#-- Template for generating SaveService class -->
package ${basePackage()}.useCases.crud.save.application.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.mapping.save.DomainModelBuilder;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.AbstractSaveService;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SavePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SaveService;
import org.springframework.stereotype.Service;

@Service
final class ${modelBaseName()}SaveService
		extends
		AbstractSaveService${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelResponse, Long${">"}
		implements SaveService${"<"}${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelResponse, Long${">"}
{
    ${modelBaseName()}SaveService(final SavePersistenceService${"<"}Long, ${modelBaseName()}DomainModel${">"} persistenceService,
							final DomainModelBuilder${"<"}${modelBaseName()}DomainModelCreate,
                            ${modelBaseName()}DomainModel${">"} modelBuilder,
							final DomainResponseBuilder${"<"}${modelBaseName()}DomainModel,
                            ${modelBaseName()}DomainModelResponse${">"} responseModelMapper,
							final InsertionPolicy${"<"}${modelBaseName()}DomainModel${">"} insertionPolicy,
							final DomainSecurityPolicy${"<"}${modelBaseName()}DomainModel${">"} domainSecurityPolicy)
	{
		super(persistenceService, modelBuilder, responseModelMapper, insertionPolicy, domainSecurityPolicy);
	}
}