<#-- Template for generating DomainPersistenceAdapterHistoryJpaRepository interface -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${aggregate().baseName()}DomainPersistenceAdapterHistoryJpaRepository
		extends TriTemporalHistoryJpaRepository<Long, ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel>
{
}