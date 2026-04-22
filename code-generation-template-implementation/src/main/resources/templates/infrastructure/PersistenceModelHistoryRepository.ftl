package ${aggregate().basePackage()}.infrastructure.persistence.repository;

import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.JpaTriTemporalHistoryRepositoryAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ${aggregate().baseName()}PersistenceModelHistoryRepository
		extends JpaTriTemporalHistoryRepositoryAdapter<UUID, ${aggregate().baseName()}PersistenceModelHistory>
		implements TriTemporalHistoryRepository<UUID, ${aggregate().baseName()}PersistenceModelHistory>
{
	public ${aggregate().baseName()}PersistenceModelHistoryRepository(
			final ${aggregate().baseName()}PersistenceModelHistoryJpaRepository repository)
	{
		super(repository);
	}
}
