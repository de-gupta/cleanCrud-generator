package ${subprocesses().deletePackage()};

import de.gupta.clean.crud.template.useCases.process.application.registration.DurableRegisteredProcess;
import de.gupta.clean.crud.template.useCases.process.domain.definition.DurableProcessDefinition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ${subprocesses().deleteConfigurationTypeName()}
{
	@Bean
	@Qualifier("${subprocesses().deleteDefinitionQualifier()}")
	DurableProcessDefinition<${subprocesses().deleteTriggerTypeName()}, ${subprocesses().deletePayloadTypeName()}> ${subprocesses().deleteDefinitionQualifier()}()
	{
		return DurableProcessDefinition.of(
				"${subprocesses().deleteProcessType()}",
				${subprocesses().deleteTriggerTypeName()}.class,
				${subprocesses().deletePayloadTypeName()}.class);
	}

	@Bean
	DurableRegisteredProcess<${subprocesses().deleteTriggerTypeName()}, ${subprocesses().deletePayloadTypeName()}> ${aggregate().beanNamePrefix()}DeleteRegisteredSubprocess(
			@Qualifier("${subprocesses().deleteDefinitionQualifier()}") final DurableProcessDefinition<${subprocesses().deleteTriggerTypeName()}, ${subprocesses().deletePayloadTypeName()}> definition,
			final ${subprocesses().deleteExecutorTypeName()} executor)
	{
		return new DurableRegisteredProcess<>(definition, executor);
	}
}
