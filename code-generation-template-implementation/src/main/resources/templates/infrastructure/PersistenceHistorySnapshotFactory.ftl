<#-- Template for generating PersistenceHistorySnapshotFactory class -->
package ${aggregate().basePackage()}.infrastructure.persistence.repository;

import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.AbstractPersistenceHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TemporalChangeType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public final class ${aggregate().baseName()}PersistenceHistorySnapshotFactory
		extends AbstractPersistenceHistorySnapshotFactory<UUID, ${aggregate().baseName()}PersistenceModel, ${aggregate().baseName()}PersistenceModelHistory>
		implements TriTemporalHistorySnapshotFactory<UUID, ${aggregate().baseName()}PersistenceModel, ${aggregate().baseName()}PersistenceModelHistory>
{
	@Override
	public ${aggregate().baseName()}PersistenceModelHistory snapshotOf(
			final ${aggregate().baseName()}PersistenceModel model,
			final TemporalChangeType changeType,
			final Instant decisionTime,
			final Instant validFrom,
			final Instant validTo)
	{
		return ${aggregate().baseName()}PersistenceModelHistory.snapshotOf(model, changeType, decisionTime, validFrom, validTo);
	}
}