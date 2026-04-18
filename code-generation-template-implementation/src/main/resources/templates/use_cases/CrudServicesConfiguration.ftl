package ${basePackage()}.useCases.crud.configuration;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.AggregateCrudDefinition;
import de.gupta.clean.crud.template.useCases.crud.aggregate.engine.AggregateLifecycleEngine;
import de.gupta.clean.crud.template.useCases.crud.aggregate.service.AggregateCrudServices;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeleteService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchService;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SaveService;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ${modelBaseName()}CrudServicesConfiguration
{
	@Bean
	SaveService<${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelResponse, Long> ${beanNamePrefix()}SaveService(
			@Qualifier("${beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.saveService(definition, aggregateLifecycleEngine);
	}

	@Bean
	FetchService<${modelBaseName()}DomainModel, Long> ${beanNamePrefix()}FetchService(
			@Qualifier("${beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.fetchService(definition, aggregateLifecycleEngine);
	}

	@Bean
	UpdateService<${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse, Long> ${beanNamePrefix()}UpdateService(
			@Qualifier("${beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.updateService(definition, aggregateLifecycleEngine);
	}

	@Bean
	@Qualifier("${beanNamePrefix()}DeleteService")
	DeleteService<Long> ${beanNamePrefix()}DeleteService(
			@Qualifier("${beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${modelBaseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.deleteService(definition, aggregateLifecycleEngine);
	}
}
