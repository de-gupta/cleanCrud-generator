package ${aggregate().basePackage()}.useCases.crud.configuration;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
<#if subprocesses().saveEnabled()>
import ${subprocesses().savePackage()}.${subprocesses().savePayloadTypeName()};
import ${subprocesses().savePackage()}.${subprocesses().saveTriggerTypeName()};
</#if>
<#if subprocesses().updateEnabled()>
import ${subprocesses().updatePackage()}.${subprocesses().updatePayloadTypeName()};
import ${subprocesses().updatePackage()}.${subprocesses().updateTriggerTypeName()};
</#if>
<#if subprocesses().deleteEnabled()>
import ${subprocesses().deletePackage()}.${subprocesses().deletePayloadTypeName()};
import ${subprocesses().deletePackage()}.${subprocesses().deleteTriggerTypeName()};
</#if>
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.AggregateCrudDefinition;
<#if subprocesses().anyEnabled()>
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationContext;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationKind;
</#if>
import de.gupta.clean.crud.template.useCases.crud.aggregate.engine.AggregateLifecycleEngine;
import de.gupta.clean.crud.template.useCases.crud.aggregate.service.AggregateCrudServices;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeleteService;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchService;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SaveService;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdateService;
<#if subprocesses().anyEnabled()>
import de.gupta.clean.crud.template.useCases.process.application.registration.DurableProcessStartRequest;
import de.gupta.clean.crud.template.useCases.process.domain.definition.DurableProcessDefinition;
import de.gupta.clean.crud.template.useCases.process.domain.model.id.CorrelationId;
import de.gupta.clean.crud.template.useCases.process.domain.model.policy.BackoffPolicy;
import de.gupta.clean.crud.template.useCases.process.domain.model.policy.RetryPolicy;
import java.time.Duration;
import java.util.Collection;
</#if>
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ${aggregate().baseName()}CrudServicesConfiguration
{
	@Bean
	SaveService<${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelResponse, Long> ${aggregate().beanNamePrefix()}SaveService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine<#if subprocesses().saveEnabled()>,
			@Qualifier("${subprocesses().saveDefinitionQualifier()}") final DurableProcessDefinition<${subprocesses().saveTriggerTypeName()}, ${subprocesses().savePayloadTypeName()}> saveSubprocessDefinition</#if>)
	{
<#if subprocesses().saveEnabled()>
		return AggregateCrudServices.saveService(
				definition,
				aggregateLifecycleEngine,
				savedModels -> saveSubprocessStartRequests(savedModels, saveSubprocessDefinition));
<#else>
		return AggregateCrudServices.saveService(definition, aggregateLifecycleEngine);
</#if>
	}

	@Bean
	FetchService<${aggregate().baseName()}DomainModel, Long> ${aggregate().beanNamePrefix()}FetchService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine)
	{
		return AggregateCrudServices.fetchService(definition, aggregateLifecycleEngine);
	}

	@Bean
	UpdateService<${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse, Long> ${aggregate().beanNamePrefix()}UpdateService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine<#if subprocesses().updateEnabled()>,
			@Qualifier("${subprocesses().updateDefinitionQualifier()}") final DurableProcessDefinition<${subprocesses().updateTriggerTypeName()}, ${subprocesses().updatePayloadTypeName()}> updateSubprocessDefinition</#if>)
	{
<#if subprocesses().updateEnabled()>
		return AggregateCrudServices.updateService(
				definition,
				aggregateLifecycleEngine,
				context -> updateSubprocessStartRequests(context, updateSubprocessDefinition));
<#else>
		return AggregateCrudServices.updateService(definition, aggregateLifecycleEngine);
</#if>
	}

	@Bean
	@Qualifier("${aggregate().beanNamePrefix()}DeleteService")
	DeleteService<Long> ${aggregate().beanNamePrefix()}DeleteService(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> definition,
			final AggregateLifecycleEngine aggregateLifecycleEngine<#if subprocesses().deleteEnabled()>,
			@Qualifier("${subprocesses().deleteDefinitionQualifier()}") final DurableProcessDefinition<${subprocesses().deleteTriggerTypeName()}, ${subprocesses().deletePayloadTypeName()}> deleteSubprocessDefinition</#if>)
	{
<#if subprocesses().deleteEnabled()>
		return AggregateCrudServices.deleteService(
				definition,
				aggregateLifecycleEngine,
				context -> deleteSubprocessStartRequests(context, deleteSubprocessDefinition));
<#else>
		return AggregateCrudServices.deleteService(definition, aggregateLifecycleEngine);
</#if>
	}

<#if subprocesses().saveEnabled()>
	private Collection<DurableProcessStartRequest<?, ?>> saveSubprocessStartRequests(
			final Collection<IdentifiedModel<Long, ${aggregate().baseName()}DomainModel>> savedModels,
			final DurableProcessDefinition<${subprocesses().saveTriggerTypeName()}, ${subprocesses().savePayloadTypeName()}> saveSubprocessDefinition)
	{
		return savedModels.stream()
		                  .<DurableProcessStartRequest<?, ?>>map(savedModel -> new DurableProcessStartRequest<>(
				                  saveSubprocessDefinition,
				                  new ${subprocesses().saveTriggerTypeName()}(savedModel.id(), PostCommitMutationKind.CREATE),
				                  new ${subprocesses().savePayloadTypeName()}(savedModel.id(), PostCommitMutationKind.CREATE),
				                  new CorrelationId("${aggregate().beanNamePrefix()}-save-" + savedModel.id()),
				                  new RetryPolicy(3, BackoffPolicy.fixed(Duration.ofSeconds(5)))))
		                  .toList();
	}
</#if>

<#if subprocesses().updateEnabled()>
	private Collection<DurableProcessStartRequest<?, ?>> updateSubprocessStartRequests(
			final PostCommitMutationContext<Long, ${aggregate().baseName()}DomainModel> context,
			final DurableProcessDefinition<${subprocesses().updateTriggerTypeName()}, ${subprocesses().updatePayloadTypeName()}> updateSubprocessDefinition)
	{
		return java.util.List.<DurableProcessStartRequest<?, ?>>of(new DurableProcessStartRequest<>(
				updateSubprocessDefinition,
				new ${subprocesses().updateTriggerTypeName()}(context.domainId(), context.kind()),
				new ${subprocesses().updatePayloadTypeName()}(context.domainId(), context.kind()),
				new CorrelationId("${aggregate().beanNamePrefix()}-update-" + context.domainId()),
				new RetryPolicy(3, BackoffPolicy.fixed(Duration.ofSeconds(5)))));
	}
</#if>

<#if subprocesses().deleteEnabled()>
	private Collection<DurableProcessStartRequest<?, ?>> deleteSubprocessStartRequests(
			final PostCommitMutationContext<Long, ${aggregate().baseName()}DomainModel> context,
			final DurableProcessDefinition<${subprocesses().deleteTriggerTypeName()}, ${subprocesses().deletePayloadTypeName()}> deleteSubprocessDefinition)
	{
		return java.util.List.<DurableProcessStartRequest<?, ?>>of(new DurableProcessStartRequest<>(
				deleteSubprocessDefinition,
				new ${subprocesses().deleteTriggerTypeName()}(context.domainId(), PostCommitMutationKind.DELETE),
				new ${subprocesses().deletePayloadTypeName()}(context.domainId(), PostCommitMutationKind.DELETE),
				new CorrelationId("${aggregate().beanNamePrefix()}-delete-" + context.domainId()),
				new RetryPolicy(3, BackoffPolicy.fixed(Duration.ofSeconds(5)))));
	}
</#if>
}
