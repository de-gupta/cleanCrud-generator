<#-- Template for generating PersistenceModelJpaFetchRepository class -->
package ${basePackage}.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.repository.AbstractPersistenceModelJpaFetchRepository;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelName}PersistenceModelJpaFetchRepository
extends AbstractPersistenceModelJpaFetchRepository${"<"}${modelName}PersistenceModel, UUID, ${modelName}PersistenceModelImpl${">"}
implements FetchPersistenceModelRepository${"<"}${modelName}PersistenceModel, UUID${">"}
{
${modelName}PersistenceModelJpaFetchRepository(
final JpaRepository${"<"}${modelName}PersistenceModelImpl, UUID${">"} jpaRepository)
{
super(jpaRepository);
}
}