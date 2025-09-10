<#-- Template for generating UpdateService class -->
package ${basePackage}.useCases.crud.update.application.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelCreate;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import ${basePackage}.domain.model.dto.${modelName}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.mapping.save.DomainModelBuilder;
import de.gupta.clean.crud.template.domain.mapping.update.DomainModelPatcher;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.PatchPolicy;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.AbstractUpdateService;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdatePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdateService;
import org.springframework.stereotype.Service;

@Service
final class ${modelName}UpdateService
		extends
AbstractUpdateService${"<"}${modelName}DomainModel, ${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse, Long${">"}
		implements UpdateService${"<"}${modelName}DomainModelCreate, ${modelName}DomainModelUpdatePatch, ${modelName}DomainModelResponse, Long${">"}
{
${modelName}UpdateService(
		final FetchPersistenceService${"<"}Long, ${modelName}DomainModel${">"} fetchService,
		final UpdatePersistenceService${"<"}Long, ${modelName}DomainModel${">"} persistenceService,
		final DomainModelBuilder${"<"}${modelName}DomainModelCreate, ${modelName}DomainModel${">"} createModelBuilder,
		final DomainModelPatcher${"<"}${modelName}DomainModel, ${modelName}DomainModelUpdatePatch${">"} modelPatcher,
		final DomainResponseBuilder${"<"}${modelName}DomainModel, ${modelName}DomainModelResponse${">"} responseModelMapper,
		final InsertionPolicy${"<"}${modelName}DomainModel${">"} insertionPolicy,
		final PatchPolicy${"<"}${modelName}DomainModel${">"} patchPolicy,
		final DomainSecurityPolicy${"<"}${modelName}DomainModel${">"} domainSecurityPolicy)
{
super(fetchService, persistenceService, createModelBuilder, modelPatcher, responseModelMapper, insertionPolicy,
patchPolicy, domainSecurityPolicy);
}
}