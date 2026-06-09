package ${subprocesses().deletePackage()};

import de.gupta.clean.crud.template.useCases.process.application.execution.DurableProcessExecutionContext;
import de.gupta.clean.crud.template.useCases.process.application.execution.DurableProcessExecutor;
import de.gupta.clean.crud.template.useCases.process.domain.model.outcome.DurableProcessOutcome;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ${subprocesses().deleteExecutorTypeName()} implements DurableProcessExecutor<${subprocesses().deletePayloadTypeName()}>
{
	@Override
	public DurableProcessOutcome execute(
			final ${subprocesses().deletePayloadTypeName()} payload,
			final DurableProcessExecutionContext context)
	{
		// TODO: implement durable subprocess behavior for delete operations.
		return DurableProcessOutcome.succeeded(List.of(), "TODO_UNIMPLEMENTED");
	}
}
