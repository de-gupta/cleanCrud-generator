<#-- Template for generating PersistenceModelJpaSaveRepository class -->
package ${basePackage}.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.repository.AbstractPersistenceModelJpaSaveRepository;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.SavePersistenceModelRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelName}PersistenceModelJpaSaveRepository extends
		AbstractPersistenceModelJpaSaveRepository${"<"}${modelName}PersistenceModel, UUID, ${modelName}PersistenceModelImpl${">"}
		implements SavePersistenceModelRepository${"<"}${modelName}PersistenceModel${">"}
{
${modelName}PersistenceModelJpaSaveRepository(final ${modelName}JpaRepository jpaRepository)
{
super(jpaRepository);
}
}