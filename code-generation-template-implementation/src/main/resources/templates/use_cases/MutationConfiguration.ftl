package ${aggregate().basePackage()}.useCases.mutation.configuration;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.AggregateCrudDefinition;
import de.gupta.clean.crud.template.useCases.crud.aggregate.engine.AggregateLifecycleEngine;
import de.gupta.clean.crud.template.useCases.mutation.aggregate.service.AggregateMutationServices;
import de.gupta.clean.crud.template.useCases.mutation.api.application.MutationApplicationController;
import de.gupta.clean.crud.template.useCases.mutation.api.application.MutationApplicationControllers;
import de.gupta.clean.crud.template.useCases.mutation.application.service.MutationService;
import de.gupta.clean.crud.template.useCases.mutation.domain.handler.MutationHandlerRegistry;
import de.gupta.clean.crud.template.useCases.mutation.domain.handler.RegisteredMutationHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
class ${aggregate().baseName()}MutationConfiguration
{
	@Bean
	@Qualifier("${aggregate().beanNamePrefix()}MutationHandlerRegistry")
	MutationHandlerRegistry<${aggregate().baseName()}DomainModel> ${aggregate().beanNamePrefix()}MutationHandlerRegistry(
			final Collection<RegisteredMutationHandler<${aggregate().baseName()}DomainModel, ?>> handlers)
	{
		return MutationHandlerRegistry.of(handlers);
	}

	@Bean
	@Qualifier("${aggregate().beanNamePrefix()}MutationService")
	MutationService<Long, ${aggregate().baseName()}DomainModel> ${aggregate().beanNamePrefix()}MutationService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine,
			@Qualifier("${aggregate().beanNamePrefix()}MutationHandlerRegistry") final MutationHandlerRegistry<${aggregate().baseName()}DomainModel> handlerRegistry)
	{
		return AggregateMutationServices.mutationService(definition, aggregateLifecycleEngine, handlerRegistry);
	}

	@Bean
	@Qualifier("${aggregate().beanNamePrefix()}MutationApplicationController")
	MutationApplicationController<Long, ${aggregate().baseName()}DomainModel> ${aggregate().beanNamePrefix()}MutationApplicationController(
			@Qualifier("${aggregate().beanNamePrefix()}MutationService") final MutationService<Long, ${aggregate().baseName()}DomainModel> service)
	{
		return MutationApplicationControllers.controller(service);
	}
}
