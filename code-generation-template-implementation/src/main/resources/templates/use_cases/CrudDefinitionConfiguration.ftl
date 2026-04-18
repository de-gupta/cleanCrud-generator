package ${basePackage()}.useCases.crud.configuration;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.mapping.save.DomainModelBuilder;
import de.gupta.clean.crud.template.domain.mapping.update.DomainModelPatcher;
import de.gupta.clean.crud.template.domain.service.crud.policy.DeletionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.PatchPolicy;
import de.gupta.clean.crud.template.domain.service.equality.DuplicateDefinition;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.aggregate.builder.AggregateCrudDefinitions;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.AggregateCrudDefinition;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPort;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateMutationPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ${modelBaseName()}CrudDefinitionConfiguration
{
	@Bean
	@Qualifier("${beanNamePrefix()}AggregateCrudDefinition")
	AggregateCrudDefinition<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate,
			${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse> ${beanNamePrefix()}AggregateCrudDefinition(
			@Qualifier("${beanNamePrefix()}AggregateMutationPort") final AggregateMutationPort<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch> mutationPort,
			@Qualifier("${beanNamePrefix()}AggregateFetchPort") final AggregateFetchPort<Long, ${modelBaseName()}DomainModel> fetchPort,
			@Qualifier("${beanNamePrefix()}DomainModelBuilder") final DomainModelBuilder<${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModel> createBuilder,
			@Qualifier("${beanNamePrefix()}DomainModelPatcher") final DomainModelPatcher<${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelUpdatePatch> patcher,
			@Qualifier("${beanNamePrefix()}DomainResponseBuilder") final DomainResponseBuilder<${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelResponse> responseBuilder,
			@Qualifier("${beanNamePrefix()}InsertionPolicy") final InsertionPolicy<${modelBaseName()}DomainModel> insertionPolicy,
			@Qualifier("${beanNamePrefix()}PatchPolicy") final PatchPolicy<${modelBaseName()}DomainModel> patchPolicy,
			@Qualifier("${beanNamePrefix()}DeletionPolicy") final DeletionPolicy<${modelBaseName()}DomainModel> deletionPolicy,
			@Qualifier("${beanNamePrefix()}DomainSecurityPolicy") final DomainSecurityPolicy<${modelBaseName()}DomainModel> securityPolicy,
			@Qualifier("${beanNamePrefix()}DuplicateDefinition") final DuplicateDefinition<${modelBaseName()}DomainModel> duplicateDefinition)
	{
		return AggregateCrudDefinitions
				.<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse>aggregateCrudDefinition()
				.mutationPort(mutationPort)
				.fetchPort(fetchPort)
				.createBuilder(createBuilder)
				.patcher(patcher)
				.responseBuilder(responseBuilder)
				.insertionPolicy(insertionPolicy)
				.patchPolicy(patchPolicy)
				.deletionPolicy(deletionPolicy)
				.securityPolicy(securityPolicy)
				.duplicateDefinition(duplicateDefinition)
				.build();
	}
}
