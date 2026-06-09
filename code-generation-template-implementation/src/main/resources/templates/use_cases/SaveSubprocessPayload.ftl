package ${subprocesses().savePackage()};

import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationKind;
import de.gupta.clean.crud.template.useCases.process.domain.definition.DurableProcessPayload;

public record ${subprocesses().savePayloadTypeName()}(
		Long domainId,
		PostCommitMutationKind kind) implements DurableProcessPayload
{
}
