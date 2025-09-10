<#-- Template for generating PersistenceModelJpaCrudRepository class -->
package ${basePackage}.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.useCases.crud.all.infrastructure.persistence.repository.AbstractPersistenceModelJpaCrudRepository;
import de.gupta.clean.crud.template.useCases.crud.all.infrastructure.persistence.service.PersistenceModelCrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ${modelName}PersistenceModelJpaCrudRepository
extends AbstractPersistenceModelJpaCrudRepository${"<"}${modelName}PersistenceModel, UUID, ${modelName}PersistenceModelImpl${">"}
implements PersistenceModelCrudRepository${"<"}${modelName}PersistenceModel, UUID${">"}
{
${modelName}PersistenceModelJpaCrudRepository(
final JpaRepository${"<"}${modelName}PersistenceModelImpl, UUID${">"} jpaRepository)
{
super(jpaRepository);
}
}