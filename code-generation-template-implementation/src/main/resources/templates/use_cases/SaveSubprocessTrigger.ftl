package ${subprocesses().savePackage()};

import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationKind;
import de.gupta.clean.crud.template.useCases.process.domain.definition.DurableProcessTrigger;

public record ${subprocesses().saveTriggerTypeName()}(
		Long domainId,
		PostCommitMutationKind kind) implements DurableProcessTrigger
{
}
