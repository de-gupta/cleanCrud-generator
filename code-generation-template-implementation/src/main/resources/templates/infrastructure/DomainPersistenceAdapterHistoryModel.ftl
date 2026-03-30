<#-- Template for generating DomainPersistenceAdapterHistoryModel class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.AbstractDomainPersistenceAdapterHistoryModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterHistoryModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "${domainPersistenceAdapterHistoryTableName()}",
		indexes = {
				@Index(name = "${modelBaseName()?lower_case}_domain_persistence_history_idx_entity_id", columnList = "entity_id"),
				@Index(name = "${modelBaseName()?lower_case}_domain_persistence_history_idx_persistence_id", columnList = "persistence_id"),
				@Index(name = "${modelBaseName()?lower_case}_domain_persistence_history_idx_validity", columnList = "valid_from, valid_to")
		})
public class ${modelBaseName()}DomainPersistenceAdapterHistoryModel
		extends AbstractDomainPersistenceAdapterHistoryModel<Long, UUID>
		implements DomainPersistenceAdapterHistoryModel<Long, UUID>
{
	public static DomainPersistenceAdapterHistoryModel.Builder<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterHistoryModel> builder()
	{
		return new Builder();
	}

	protected ${modelBaseName()}DomainPersistenceAdapterHistoryModel()
	{
		super();
	}

	private static final class Builder extends AbstractBuilder<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterHistoryModel>
			implements DomainPersistenceAdapterHistoryModel.Builder<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterHistoryModel>,
			de.gupta.clean.crud.template.domain.model.builder.ModelBuilder<${modelBaseName()}DomainPersistenceAdapterHistoryModel>
	{
		@Override
		protected ${modelBaseName()}DomainPersistenceAdapterHistoryModel doBuild()
		{
			return (${modelBaseName()}DomainPersistenceAdapterHistoryModel) model;
		}

		private Builder()
		{
			super(new ${modelBaseName()}DomainPersistenceAdapterHistoryModel());
		}
	}
}