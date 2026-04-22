package ${aggregate().basePackage()}.useCases.crud.configuration;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
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
class ${aggregate().baseName()}CrudPortsConfiguration
{
	@Bean
	@Qualifier("${aggregate().beanNamePrefix()}AggregateMutationPort")
	AggregateMutationPort<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate,
			${aggregate().baseName()}DomainModelUpdatePatch> ${aggregate().beanNamePrefix()}AggregateMutationPort(
			@Qualifier("${aggregate().beanNamePrefix()}SavePersistenceService") final SavePersistenceService<Long, ${aggregate().baseName()}DomainModel> savePersistenceService,
			@Qualifier("${aggregate().beanNamePrefix()}UpdatePersistenceService") final UpdatePersistenceService<Long, ${aggregate().baseName()}DomainModel> updatePersistenceService,
			@Qualifier("${aggregate().beanNamePrefix()}DeletePersistenceService") final DeletePersistenceService<Long> deletePersistenceService)
	{
		return AggregateMutationPortAdapter.withPersistenceServices(
				savePersistenceService,
				updatePersistenceService,
				deletePersistenceService);
	}

	@Bean
	@Qualifier("${aggregate().beanNamePrefix()}AggregateFetchPort")
	AggregateFetchPort<Long, ${aggregate().baseName()}DomainModel> ${aggregate().beanNamePrefix()}AggregateFetchPort(
			@Qualifier("${aggregate().beanNamePrefix()}FetchPersistenceService") final FetchPersistenceService<Long, ${aggregate().baseName()}DomainModel> fetchPersistenceService)
	{
		return AggregateFetchPortAdapter.withPersistenceService(fetchPersistenceService);
	}
}
