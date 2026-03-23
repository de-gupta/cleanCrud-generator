<#-- Template for generating DomainPersistenceAdapterHistoryModel class -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.AbstractDomainPersistenceAdapterHistoryModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterHistoryModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "${modelName?lower_case}_domain_persistence_adapter_model_history",
		indexes = {
				@Index(name = "${modelName?lower_case}_domain_persistence_history_idx_entity_id", columnList = "entity_id"),
				@Index(name = "${modelName?lower_case}_domain_persistence_history_idx_persistence_id", columnList = "persistence_id"),
				@Index(name = "${modelName?lower_case}_domain_persistence_history_idx_validity", columnList = "valid_from, valid_to")
		})
public class ${modelName}DomainPersistenceAdapterHistoryModel
		extends AbstractDomainPersistenceAdapterHistoryModel<Long, UUID>
		implements DomainPersistenceAdapterHistoryModel<Long, UUID>
{
	public static DomainPersistenceAdapterHistoryModel.Builder<Long, UUID, ${modelName}DomainPersistenceAdapterHistoryModel> builder()
	{
		return new Builder();
	}

	protected ${modelName}DomainPersistenceAdapterHistoryModel()
	{
		super();
	}

	private static final class Builder extends AbstractBuilder<Long, UUID, ${modelName}DomainPersistenceAdapterHistoryModel>
			implements DomainPersistenceAdapterHistoryModel.Builder<Long, UUID, ${modelName}DomainPersistenceAdapterHistoryModel>,
			de.gupta.clean.crud.template.domain.model.builder.ModelBuilder<${modelName}DomainPersistenceAdapterHistoryModel>
	{
		@Override
		protected ${modelName}DomainPersistenceAdapterHistoryModel doBuild()
		{
			return (${modelName}DomainPersistenceAdapterHistoryModel) model;
		}

		private Builder()
		{
			super(new ${modelName}DomainPersistenceAdapterHistoryModel());
		}
	}
}