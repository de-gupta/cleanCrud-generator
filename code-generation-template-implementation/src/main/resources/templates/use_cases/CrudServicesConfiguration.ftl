package ${aggregate().basePackage()}.useCases.crud.configuration;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
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
class ${aggregate().baseName()}CrudServicesConfiguration
{
	@Bean
	SaveService<${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelResponse, Long> ${aggregate().beanNamePrefix()}SaveService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.saveService(definition, aggregateLifecycleEngine);
	}

	@Bean
	FetchService<${aggregate().baseName()}DomainModel, Long> ${aggregate().beanNamePrefix()}FetchService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.fetchService(definition, aggregateLifecycleEngine);
	}

	@Bean
	UpdateService<${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse, Long> ${aggregate().beanNamePrefix()}UpdateService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.updateService(definition, aggregateLifecycleEngine);
	}

	@Bean
	@Qualifier("${aggregate().beanNamePrefix()}DeleteService")
	DeleteService<Long> ${aggregate().beanNamePrefix()}DeleteService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.deleteService(definition, aggregateLifecycleEngine);
	}
}
