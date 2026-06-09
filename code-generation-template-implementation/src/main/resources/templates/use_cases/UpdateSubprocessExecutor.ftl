package ${subprocesses().updatePackage()};

import de.gupta.clean.crud.template.useCases.process.application.execution.DurableProcessExecutionContext;
import de.gupta.clean.crud.template.useCases.process.application.execution.DurableProcessExecutor;
import de.gupta.clean.crud.template.useCases.process.domain.model.outcome.DurableProcessOutcome;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ${subprocesses().updateExecutorTypeName()} implements DurableProcessExecutor<${subprocesses().updatePayloadTypeName()}>
{
	@Override
	public DurableProcessOutcome execute(
			final ${subprocesses().updatePayloadTypeName()} payload,
			final DurableProcessExecutionContext context)
	{
		// TODO: implement durable subprocess behavior for PUT/PATCH operations.
		return DurableProcessOutcome.succeeded(List.of(), "TODO_UNIMPLEMENTED");
	}
}
