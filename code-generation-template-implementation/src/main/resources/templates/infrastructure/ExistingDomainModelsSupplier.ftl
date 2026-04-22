<#-- Template for generating ExistingDomainModelsSupplier class -->
package ${aggregate().basePackage()}.infrastructure.persistence.service;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}JpaRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Supplier;

@Component
final class ${aggregate().baseName()}ExistingDomainModelsSupplier
		implements Supplier<Collection<${aggregate().baseName()}DomainModel>>
{
	private final ${aggregate().baseName()}JpaRepository repository;
	private final DomainPersistenceModelAdapter<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}PersistenceModel> modelAdapter;

	@Override
	public Collection<${aggregate().baseName()}DomainModel> get()
	{
		return repository.findAll()
						 .stream()
						 .map(modelAdapter::toDomainModel)
						 .toList();
	}

	${aggregate().baseName()}ExistingDomainModelsSupplier(
			final ${aggregate().baseName()}JpaRepository repository,
			final DomainPersistenceModelAdapter<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}PersistenceModel> modelAdapter)
	{
		this.repository = repository;
		this.modelAdapter = modelAdapter;
	}
}