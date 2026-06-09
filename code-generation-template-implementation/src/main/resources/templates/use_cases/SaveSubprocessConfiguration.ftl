package ${subprocesses().savePackage()};

import de.gupta.clean.crud.template.useCases.process.application.registration.DurableRegisteredProcess;
import de.gupta.clean.crud.template.useCases.process.domain.definition.DurableProcessDefinition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ${subprocesses().saveConfigurationTypeName()}
{
	@Bean
	@Qualifier("${subprocesses().saveDefinitionQualifier()}")
	DurableProcessDefinition<${subprocesses().saveTriggerTypeName()}, ${subprocesses().savePayloadTypeName()}> ${subprocesses().saveDefinitionQualifier()}()
	{
		return DurableProcessDefinition.of(
				"${subprocesses().saveProcessType()}",
				${subprocesses().saveTriggerTypeName()}.class,
				${subprocesses().savePayloadTypeName()}.class);
	}

	@Bean
	DurableRegisteredProcess<${subprocesses().saveTriggerTypeName()}, ${subprocesses().savePayloadTypeName()}> ${aggregate().beanNamePrefix()}SaveRegisteredSubprocess(
			@Qualifier("${subprocesses().saveDefinitionQualifier()}") final DurableProcessDefinition<${subprocesses().saveTriggerTypeName()}, ${subprocesses().savePayloadTypeName()}> definition,
			final ${subprocesses().saveExecutorTypeName()} executor)
	{
		return new DurableRegisteredProcess<>(definition, executor);
	}
}
