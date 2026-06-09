package ${subprocesses().deletePackage()};

import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationKind;
import de.gupta.clean.crud.template.useCases.process.domain.definition.DurableProcessPayload;

public record ${subprocesses().deletePayloadTypeName()}(
		Long domainId,
		PostCommitMutationKind kind) implements DurableProcessPayload
{
}
