package ${basePackage()}.useCases.crud.configuration;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPort;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPortAdapter;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateMutationPort;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateMutationPortAdapter;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeletePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SavePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdatePersistenceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ${modelBaseName()}CrudPortsConfiguration
{
	@Bean
	@Qualifier("${beanNamePrefix()}AggregateMutationPort")
	AggregateMutationPort<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate,
			${modelBaseName()}DomainModelUpdatePatch> ${beanNamePrefix()}AggregateMutationPort(
			@Qualifier("${beanNamePrefix()}SavePersistenceService") final SavePersistenceService<Long, ${modelBaseName()}DomainModel> savePersistenceService,
			@Qualifier("${beanNamePrefix()}UpdatePersistenceService") final UpdatePersistenceService<Long, ${modelBaseName()}DomainModel> updatePersistenceService,
			@Qualifier("${beanNamePrefix()}DeletePersistenceService") final DeletePersistenceService<Long> deletePersistenceService)
	{
		return AggregateMutationPortAdapter.withPersistenceServices(
				savePersistenceService,
				updatePersistenceService,
				deletePersistenceService);
	}

	@Bean
	@Qualifier("${beanNamePrefix()}AggregateFetchPort")
	AggregateFetchPort<Long, ${modelBaseName()}DomainModel> ${beanNamePrefix()}AggregateFetchPort(
			@Qualifier("${beanNamePrefix()}FetchPersistenceService") final FetchPersistenceService<Long, ${modelBaseName()}DomainModel> fetchPersistenceService)
	{
		return AggregateFetchPortAdapter.withPersistenceService(fetchPersistenceService);
	}
}
