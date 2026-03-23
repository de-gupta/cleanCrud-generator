<#-- Template for generating DomainPersistenceAdapterModel class -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.AbstractDomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

@Entity
@Table(name = "${modelName?lower_case}_domain_persistence_adapter_model",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "domain_id"),
				@UniqueConstraint(columnNames = "persistence_id")
		},
		indexes = {
				@Index(name = "${modelName?lower_case}_idx_domain_id", columnList = "domain_id"),
				@Index(name = "${modelName?lower_case}_idx_persistence_id", columnList = "persistence_id")
		})
public class ${modelName}DomainPersistenceAdapterModel extends AbstractDomainPersistenceAdapterModel<Long, UUID>
		implements DomainPersistenceAdapterModel<Long, UUID>
{
	public static DomainPersistenceAdapterModel.Builder<Long, UUID, ${modelName}DomainPersistenceAdapterModel> builder()
	{
		return new Builder();
	}

	protected ${modelName}DomainPersistenceAdapterModel()
	{
		super();
	}

	private static final class Builder extends AbstractBuilder<Long, UUID, ${modelName}DomainPersistenceAdapterModel>
			implements DomainPersistenceAdapterModel.Builder<Long, UUID, ${modelName}DomainPersistenceAdapterModel>,
			de.gupta.clean.crud.template.domain.model.builder.ModelBuilder<${modelName}DomainPersistenceAdapterModel>
	{
		@Override
		protected ${modelName}DomainPersistenceAdapterModel doBuild()
		{
			return (${modelName}DomainPersistenceAdapterModel) model;
		}

		private Builder()
		{
			super(new ${modelName}DomainPersistenceAdapterModel());
		}
	}
}