package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.AbstractDomainPersistenceAdapterHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ${modelBaseName()}DomainPersistenceAdapterHistoryRepository
		extends AbstractDomainPersistenceAdapterHistoryJpaRepository<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterHistoryModel>
{
	public ${modelBaseName()}DomainPersistenceAdapterHistoryRepository(
			final ${modelBaseName()}DomainPersistenceAdapterHistoryJpaRepository repository)
	{
		super(repository);
	}
}
