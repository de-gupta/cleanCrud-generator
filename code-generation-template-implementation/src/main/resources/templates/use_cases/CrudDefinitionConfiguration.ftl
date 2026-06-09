package ${aggregate().basePackage()}.useCases.crud.configuration;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.mapping.save.DomainModelBuilder;
import de.gupta.clean.crud.template.domain.mapping.update.DomainModelPatcher;
import de.gupta.clean.crud.template.domain.service.crud.policy.DeletionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.InsertionPolicy;
import de.gupta.clean.crud.template.domain.service.crud.policy.PatchPolicy;
import de.gupta.clean.crud.template.domain.service.equality.DuplicateDefinition;
import de.gupta.clean.crud.template.domain.service.security.DomainSecurityPolicy;
import de.gupta.clean.crud.template.useCases.crud.aggregate.builder.AggregateCrudDefinitions;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.AggregateCrudDefinition;
<#if postCommitHooks().anyEnabled()>
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutation;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationContext;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationKind;
</#if>
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPort;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateMutationPort;
<#if composition().hasRelationships()>
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.AggregateRelationshipDefinitionContract;
</#if>
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ${aggregate().baseName()}CrudDefinitionConfiguration
{
	@Bean
	@Qualifier("${aggregate().beanNamePrefix()}AggregateCrudDefinition")
	AggregateCrudDefinition<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate,
			${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse> ${aggregate().beanNamePrefix()}AggregateCrudDefinition(
			@Qualifier("${aggregate().beanNamePrefix()}AggregateMutationPort") final AggregateMutationPort<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch> mutationPort,
			@Qualifier("${aggregate().beanNamePrefix()}AggregateFetchPort") final AggregateFetchPort<Long, ${aggregate().baseName()}DomainModel> fetchPort,
			@Qualifier("${aggregate().beanNamePrefix()}DomainModelBuilder") final DomainModelBuilder<${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModel> createBuilder,
			@Qualifier("${aggregate().beanNamePrefix()}DomainModelPatcher") final DomainModelPatcher<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelUpdatePatch> patcher,
			@Qualifier("${aggregate().beanNamePrefix()}DomainResponseBuilder") final DomainResponseBuilder<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelResponse> responseBuilder,
			@Qualifier("${aggregate().beanNamePrefix()}InsertionPolicy") final InsertionPolicy<${aggregate().baseName()}DomainModel> insertionPolicy,
			@Qualifier("${aggregate().beanNamePrefix()}PatchPolicy") final PatchPolicy<${aggregate().baseName()}DomainModel> patchPolicy,
			@Qualifier("${aggregate().beanNamePrefix()}DeletionPolicy") final DeletionPolicy<${aggregate().baseName()}DomainModel> deletionPolicy,
			@Qualifier("${aggregate().beanNamePrefix()}DomainSecurityPolicy") final DomainSecurityPolicy<${aggregate().baseName()}DomainModel> securityPolicy,
			@Qualifier("${aggregate().beanNamePrefix()}DuplicateDefinition") final DuplicateDefinition<${aggregate().baseName()}DomainModel> duplicateDefinition<#if postCommitHooks().saveEnabled()>,
			@Qualifier("${postCommitHooks().saveQualifier()}") final PostCommitMutation<Long, ${aggregate().baseName()}DomainModel> savePostCommitMutation</#if><#if postCommitHooks().updateEnabled()>,
			@Qualifier("${postCommitHooks().updateQualifier()}") final PostCommitMutation<Long, ${aggregate().baseName()}DomainModel> updatePostCommitMutation</#if><#if postCommitHooks().deleteEnabled()>,
			@Qualifier("${postCommitHooks().deleteQualifier()}") final PostCommitMutation<Long, ${aggregate().baseName()}DomainModel> deletePostCommitMutation</#if><#if composition().hasRelationships()>,
<#list composition().relationships() as relationship>
			@Qualifier("${relationship.relationshipBeanNamePrefix()}RelationshipDefinition") final AggregateRelationshipDefinitionContract<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch> ${relationship.relationshipBeanNamePrefix()}RelationshipDefinition<#if relationship_has_next>,</#if>
</#list>
</#if>)
	{
		return AggregateCrudDefinitions
				.<Long, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${aggregate().baseName()}DomainModelResponse>aggregateCrudDefinition()
				.mutationPort(mutationPort)
				.fetchPort(fetchPort)
				.createBuilder(createBuilder)
				.patcher(patcher)
				.responseBuilder(responseBuilder)
				.insertionPolicy(insertionPolicy)
				.patchPolicy(patchPolicy)
				.deletionPolicy(deletionPolicy)
				.securityPolicy(securityPolicy)
				.duplicateDefinition(duplicateDefinition)
<#if postCommitHooks().anyEnabled()>
				.postCommitMutation(postCommitMutation(<#if postCommitHooks().saveEnabled()>savePostCommitMutation</#if><#if postCommitHooks().saveEnabled() && (postCommitHooks().updateEnabled() || postCommitHooks().deleteEnabled())>, </#if><#if postCommitHooks().updateEnabled()>updatePostCommitMutation</#if><#if postCommitHooks().updateEnabled() && postCommitHooks().deleteEnabled()>, </#if><#if postCommitHooks().deleteEnabled()>deletePostCommitMutation</#if>))
</#if>
<#list composition().relationships() as relationship>
				.relationshipDefinition(${relationship.relationshipBeanNamePrefix()}RelationshipDefinition)
</#list>
				.build();
	}

<#if postCommitHooks().anyEnabled()>
	private PostCommitMutation<Long, ${aggregate().baseName()}DomainModel> postCommitMutation(
<#if postCommitHooks().saveEnabled()>
			final PostCommitMutation<Long, ${aggregate().baseName()}DomainModel> savePostCommitMutation<#if postCommitHooks().updateEnabled() || postCommitHooks().deleteEnabled()>,</#if>
</#if>
<#if postCommitHooks().updateEnabled()>
			final PostCommitMutation<Long, ${aggregate().baseName()}DomainModel> updatePostCommitMutation<#if postCommitHooks().deleteEnabled()>,</#if>
</#if>
<#if postCommitHooks().deleteEnabled()>
			final PostCommitMutation<Long, ${aggregate().baseName()}DomainModel> deletePostCommitMutation
</#if>
	)
	{
		return context ->
		{
			switch (context.kind())
			{
				case CREATE ->
				{
<#if postCommitHooks().saveEnabled()>
					savePostCommitMutation.accept(context);
</#if>
				}
				case PUT, PATCH ->
				{
<#if postCommitHooks().updateEnabled()>
					updatePostCommitMutation.accept(context);
</#if>
				}
				case DELETE ->
				{
<#if postCommitHooks().deleteEnabled()>
					deletePostCommitMutation.accept(context);
</#if>
				}
			}
		};
	}
</#if>
}
