<#-- Template for generating PersistenceModelJpaDeleteRepository class -->
package ${basePackage}.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.repository.AbstractPersistenceModelJpaDeleteRepository;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.service.DeletePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("${modelName?uncap_first}DeletePersistenceModelRepository")
final class ${modelName}PersistenceModelJpaDeleteRepository
extends AbstractPersistenceModelJpaDeleteRepository${"<"}${modelName}PersistenceModel, UUID, ${modelName}PersistenceModelImpl${">"}
implements DeletePersistenceModelRepository${"<"}UUID${">"}
{
${modelName}PersistenceModelJpaDeleteRepository(final JpaRepository${"<"}${modelName}PersistenceModelImpl, UUID${">"} jpaRepository)
{
super(jpaRepository);
}
}