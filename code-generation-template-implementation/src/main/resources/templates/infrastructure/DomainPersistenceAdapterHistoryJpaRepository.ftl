<#-- Template for generating DomainPersistenceAdapterHistoryJpaRepository interface -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${modelBaseName()}DomainPersistenceAdapterHistoryJpaRepository
		extends TriTemporalHistoryJpaRepository<Long, ${modelBaseName()}DomainPersistenceAdapterHistoryModel>
{
}