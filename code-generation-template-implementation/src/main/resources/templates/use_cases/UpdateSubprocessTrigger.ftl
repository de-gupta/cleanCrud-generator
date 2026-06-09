package ${subprocesses().updatePackage()};

import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationKind;
import de.gupta.clean.crud.template.useCases.process.domain.definition.DurableProcessTrigger;

public record ${subprocesses().updateTriggerTypeName()}(
		Long domainId,
		PostCommitMutationKind kind) implements DurableProcessTrigger
{
}
