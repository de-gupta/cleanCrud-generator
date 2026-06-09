package ${subprocesses().updatePackage()};

import de.gupta.clean.crud.template.useCases.process.application.registration.DurableRegisteredProcess;
import de.gupta.clean.crud.template.useCases.process.domain.definition.DurableProcessDefinition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ${subprocesses().updateConfigurationTypeName()}
{
	@Bean
	@Qualifier("${subprocesses().updateDefinitionQualifier()}")
	DurableProcessDefinition<${subprocesses().updateTriggerTypeName()}, ${subprocesses().updatePayloadTypeName()}> ${subprocesses().updateDefinitionQualifier()}()
	{
		return DurableProcessDefinition.of(
				"${subprocesses().updateProcessType()}",
				${subprocesses().updateTriggerTypeName()}.class,
				${subprocesses().updatePayloadTypeName()}.class);
	}

	@Bean
	DurableRegisteredProcess<${subprocesses().updateTriggerTypeName()}, ${subprocesses().updatePayloadTypeName()}> ${aggregate().beanNamePrefix()}UpdateRegisteredSubprocess(
			@Qualifier("${subprocesses().updateDefinitionQualifier()}") final DurableProcessDefinition<${subprocesses().updateTriggerTypeName()}, ${subprocesses().updatePayloadTypeName()}> definition,
			final ${subprocesses().updateExecutorTypeName()} executor)
	{
		return new DurableRegisteredProcess<>(definition, executor);
	}
}
