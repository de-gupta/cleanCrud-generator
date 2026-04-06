package ${basePackage()}.infrastructure.persistence.repository;

import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.JpaTriTemporalHistoryRepositoryAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ${modelBaseName()}PersistenceModelHistoryRepository
		extends JpaTriTemporalHistoryRepositoryAdapter<UUID, ${modelBaseName()}PersistenceModelHistory>
		implements TriTemporalHistoryRepository<UUID, ${modelBaseName()}PersistenceModelHistory>
{
	public ${modelBaseName()}PersistenceModelHistoryRepository(
			final ${modelBaseName()}PersistenceModelHistoryJpaRepository repository)
	{
		super(repository);
	}
}
