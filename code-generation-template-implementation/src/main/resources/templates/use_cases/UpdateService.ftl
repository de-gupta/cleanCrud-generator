<#-- Template for generating UpdateService class -->
package ${basePackage()}.useCases.crud.update.application.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
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
final class ${modelBaseName()}UpdateService
		extends
AbstractUpdateService${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse, Long${">"}
		implements UpdateService${"<"}${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse, Long${">"}
{
${modelBaseName()}UpdateService(
		final FetchPersistenceService${"<"}Long, ${modelBaseName()}DomainModel${">"} fetchService,
		final UpdatePersistenceService${"<"}Long, ${modelBaseName()}DomainModel${">"} persistenceService,
		final DomainModelBuilder${"<"}${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModel${">"} createModelBuilder,
		final DomainModelPatcher${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelUpdatePatch${">"} modelPatcher,
		final DomainResponseBuilder${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelResponse${">"} responseModelMapper,
		final InsertionPolicy${"<"}${modelBaseName()}DomainModel${">"} insertionPolicy,
		final PatchPolicy${"<"}${modelBaseName()}DomainModel${">"} patchPolicy,
		final DomainSecurityPolicy${"<"}${modelBaseName()}DomainModel${">"} domainSecurityPolicy)
{
super(fetchService, persistenceService, createModelBuilder, modelPatcher, responseModelMapper, insertionPolicy,
patchPolicy, domainSecurityPolicy);
}
}