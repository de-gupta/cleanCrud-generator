package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.AbstractDomainPersistenceAdapterHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ${aggregate().baseName()}DomainPersistenceAdapterHistoryRepository
		extends AbstractDomainPersistenceAdapterHistoryJpaRepository<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel>
{
	public ${aggregate().baseName()}DomainPersistenceAdapterHistoryRepository(
			final ${aggregate().baseName()}DomainPersistenceAdapterHistoryJpaRepository repository)
	{
		super(repository);
	}
}
