package ${subprocesses().savePackage()};

import de.gupta.clean.crud.template.useCases.process.application.execution.DurableProcessExecutionContext;
import de.gupta.clean.crud.template.useCases.process.application.execution.DurableProcessExecutor;
import de.gupta.clean.crud.template.useCases.process.domain.model.outcome.DurableProcessOutcome;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ${subprocesses().saveExecutorTypeName()} implements DurableProcessExecutor<${subprocesses().savePayloadTypeName()}>
{
	@Override
	public DurableProcessOutcome execute(
			final ${subprocesses().savePayloadTypeName()} payload,
			final DurableProcessExecutionContext context)
	{
		// TODO: implement durable subprocess behavior for save/create operations.
		return DurableProcessOutcome.succeeded(List.of(), "TODO_UNIMPLEMENTED");
	}
}
