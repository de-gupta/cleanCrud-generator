<#-- Template for generating ExistingDomainModelsSupplier class -->
package ${basePackage()}.infrastructure.persistence.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}JpaRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Supplier;

@Component
final class ${modelBaseName()}ExistingDomainModelsSupplier
		implements Supplier<Collection<${modelBaseName()}DomainModel>>
{
	private final ${modelBaseName()}JpaRepository repository;
	private final DomainPersistenceModelAdapter<${modelBaseName()}DomainModel, ${modelBaseName()}PersistenceModel> modelAdapter;

	@Override
	public Collection<${modelBaseName()}DomainModel> get()
	{
		return repository.findAll()
						 .stream()
						 .map(modelAdapter::toDomainModel)
						 .toList();
	}

	${modelBaseName()}ExistingDomainModelsSupplier(
			final ${modelBaseName()}JpaRepository repository,
			final DomainPersistenceModelAdapter<${modelBaseName()}DomainModel, ${modelBaseName()}PersistenceModel> modelAdapter)
	{
		this.repository = repository;
		this.modelAdapter = modelAdapter;
	}
}
