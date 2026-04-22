<#-- Template for generating DomainPersistenceAdapterHistoryModel class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.AbstractDomainPersistenceAdapterHistoryModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterHistoryModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "${persistence().adapterHistoryTableName()}",
		indexes = {
				@Index(name = "${aggregate().baseName()?lower_case}_domain_persistence_history_idx_entity_id", columnList = "entity_id"),
				@Index(name = "${aggregate().baseName()?lower_case}_domain_persistence_history_idx_persistence_id", columnList = "persistence_id"),
				@Index(name = "${aggregate().baseName()?lower_case}_domain_persistence_history_idx_validity", columnList = "valid_from, valid_to")
		})
public class ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel
		extends AbstractDomainPersistenceAdapterHistoryModel<Long, UUID>
		implements DomainPersistenceAdapterHistoryModel<Long, UUID>
{
	public static DomainPersistenceAdapterHistoryModel.Builder<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel> builder()
	{
		return new Builder();
	}

	protected ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel()
	{
		super();
	}

	private static final class Builder extends AbstractBuilder<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel>
			implements DomainPersistenceAdapterHistoryModel.Builder<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel>,
			de.gupta.clean.crud.template.domain.model.builder.ModelBuilder<${aggregate().baseName()}DomainPersistenceAdapterHistoryModel>
	{
		@Override
		protected ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel doBuild()
		{
			return (${aggregate().baseName()}DomainPersistenceAdapterHistoryModel) model;
		}

		private Builder()
		{
			super(new ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel());
		}
	}
}