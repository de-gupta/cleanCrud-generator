package ${basePackage()}.useCases.crud.configuration;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelCreate;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelUpdatePatch;
<#list relationships() as relationship>
import ${relationship.responseImport()?replace('.useCases.crud.common.dto.', '.domain.model.')?replace('APIModelResponse', 'DomainModel')};
import ${relationship.domainCreateImport(basePackage())};
import ${relationship.domainResponseImport(basePackage())};
import ${relationship.domainUpdatePatchImport(basePackage())};
import ${relationship.createImport()};
import ${relationship.updatePatchImport()};
import ${relationship.responseImport()};
</#list>
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.domain.model.exceptions.operation.InvalidRequestException;
import de.gupta.clean.crud.template.domain.model.exceptions.resource.ResourceNotFoundException;
import de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel;
import de.gupta.clean.crud.template.useCases.crud.aggregate.builder.AggregateRelationshipDefinitions;
import de.gupta.clean.crud.template.useCases.crud.aggregate.builder.LifecycleSemanticsBuilder;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.AggregateCrudDefinition;
import de.gupta.clean.crud.template.useCases.crud.aggregate.intent.SatelliteCreateIntent;
import de.gupta.clean.crud.template.useCases.crud.aggregate.intent.SatelliteMutationIntent;
import de.gupta.clean.crud.template.useCases.crud.aggregate.lifecycle.LifecycleSemantics;
import de.gupta.clean.crud.template.useCases.crud.aggregate.port.AggregateFetchPort;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.AggregateRelationshipDefinition;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.Cardinality;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.ReconciliationStrategy;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatelliteCreateInputResolver;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatelliteHydrationStrategy;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatelliteIdentityResolver;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatelliteLinkStrategy;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatellitePatchInputResolver;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatellitePersistenceOrder;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainCreateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.APIToDomainUpdateAdapter;
import de.gupta.clean.crud.template.useCases.crud.common.adapter.model.DomainToAPIResponseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Configuration
class ${modelBaseName()}CrudRelationshipConfiguration
{
<#list relationships() as relationship>
	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}LifecycleSemantics")
	LifecycleSemantics ${relationship.relationshipBeanNamePrefix()}LifecycleSemantics()
	{
		// TODO: Review the generated lifecycle defaults for `${relationship.propertyName()}` and change them if your ownership semantics differ.
		return LifecycleSemanticsBuilder.lifecycleSemantics()
				<#if relationship.cascadeCreate()>.cascadeCreate()
				</#if><#if relationship.cascadeUpdate()>.cascadeUpdate()
				</#if><#if relationship.cascadeDelete()>.cascadeDelete()
				</#if><#if relationship.orphanDelete()>.orphanDelete()
				</#if><#if relationship.hydrateOnFetch()>.hydrateOnFetch()
				</#if>.build();
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}CreateInputResolver")
	SatelliteCreateInputResolver<${modelBaseName()}DomainModelCreate, Collection<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelCreate>>> ${relationship.relationshipBeanNamePrefix()}CreateInputResolver(
			@Qualifier("${relationship.satelliteQualifierPrefix()}APIToDomainCreateAdapter") final APIToDomainCreateAdapter<${relationship.createType()}, ${relationship.satelliteAggregate()}DomainModelCreate> ${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter)
	{
		return ${beanNamePrefix()}DomainModelCreate ->
		{
			<#if relationship.generateNestedCreate()>
			<#if relationship.many()>
			return ${beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}().stream()
			                                              .<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelCreate>>map(
					${relationship.relationshipVariablePrefix()}Item -> new SatelliteCreateIntent.InlineSatelliteCreateIntent<>(
							${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter.mapToDomainModelCreate(${relationship.relationshipVariablePrefix()}Item)))
			                                              .toList();
			<#elseif relationship.optional()>
			return ${beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}().stream()
			                                              .<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelCreate>>map(
					${relationship.relationshipVariablePrefix()}Item -> new SatelliteCreateIntent.InlineSatelliteCreateIntent<>(
							${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter.mapToDomainModelCreate(${relationship.relationshipVariablePrefix()}Item)))
			                                              .toList();
			<#else>
			return List.of(new SatelliteCreateIntent.InlineSatelliteCreateIntent<>(
					${relationship.relationshipVariablePrefix()}APIToDomainCreateAdapter.mapToDomainModelCreate(
							${beanNamePrefix()}DomainModelCreate.${relationship.propertyName()}())));
			</#if>
			<#else>
			return List.of();
			</#if>
		};
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}PatchInputResolver")
	SatellitePatchInputResolver<${modelBaseName()}DomainModelUpdatePatch, Collection<SatelliteMutationIntent<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelCreate, ${relationship.satelliteAggregate()}DomainModelUpdatePatch>>> ${relationship.relationshipBeanNamePrefix()}PatchInputResolver(
			@Qualifier("${relationship.satelliteQualifierPrefix()}APIToDomainUpdateAdapter") final APIToDomainUpdateAdapter<${relationship.updatePatchType()}, ${relationship.satelliteAggregate()}DomainModelUpdatePatch> ${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter)
	{
		return ${beanNamePrefix()}DomainModelUpdatePatch ->
		{
			var intents = new ArrayList<SatelliteMutationIntent<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelCreate,
					${relationship.satelliteAggregate()}DomainModelUpdatePatch>>();
			<#if relationship.generateNestedUpdate()>
			<#if relationship.many()>
			${beanNamePrefix()}DomainModelUpdatePatch.${relationship.propertyName()}().ifPresent(${relationship.propertyName()} -> ${relationship.propertyName()}.forEach(${relationship.relationshipVariablePrefix()}Item ->
			{
				var patch = ${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter.mapToDomainModelUpdatePatch(
						${relationship.relationshipVariablePrefix()}Item.patch());
				if (${relationship.relationshipVariablePrefix()}Item.id().isPresent())
				{
					intents.add(new SatelliteMutationIntent.UpdateSatelliteMutationIntent<>(
							${relationship.relationshipVariablePrefix()}Item.id().orElseThrow(),
							patch));
					return;
				}
				try
				{
					intents.add(new SatelliteMutationIntent.CreateSatelliteMutationIntent<>(
							${relationship.satelliteAggregate()}DomainModelCreate.fromUpdatePatch(patch)));
				}
				catch (final RuntimeException exception)
				{
					throw InvalidRequestException.withMessage(
							"An id-less `${relationship.propertyName()}` mutation requires a complete payload");
				}
			}));
			${beanNamePrefix()}DomainModelUpdatePatch.${relationship.removeFieldName()}().forEach(
					${relationship.relationshipVariablePrefix()}Id -> intents.add(
							new SatelliteMutationIntent.RemoveSatelliteMutationIntent<>(${relationship.relationshipVariablePrefix()}Id)));
			<#else>
			${beanNamePrefix()}DomainModelUpdatePatch.${relationship.propertyName()}().ifPresent(${relationship.relationshipVariablePrefix()}Item ->
			{
				var patch = ${relationship.relationshipVariablePrefix()}APIToDomainUpdateAdapter.mapToDomainModelUpdatePatch(
						${relationship.relationshipVariablePrefix()}Item.patch());
				if (${relationship.relationshipVariablePrefix()}Item.id().isPresent())
				{
					intents.add(new SatelliteMutationIntent.UpdateSatelliteMutationIntent<>(
							${relationship.relationshipVariablePrefix()}Item.id().orElseThrow(),
							patch));
					return;
				}
				try
				{
					intents.add(new SatelliteMutationIntent.UpsertCurrentSatelliteMutationIntent<>(
							${relationship.satelliteAggregate()}DomainModelCreate.fromUpdatePatch(patch),
							patch));
				}
				catch (final RuntimeException exception)
				{
					throw InvalidRequestException.withMessage(
							"An id-less `${relationship.propertyName()}` mutation requires a complete payload");
				}
			});
			if (${beanNamePrefix()}DomainModelUpdatePatch.${relationship.removeFieldName()}())
			{
				intents.add(new SatelliteMutationIntent.RemoveCurrentSatelliteMutationIntent<>());
			}
			</#if>
			</#if>
			return intents;
		};
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}IdentityResolver")
	SatelliteIdentityResolver<${modelBaseName()}DomainModel, ${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteDomainIdType()}> ${relationship.relationshipBeanNamePrefix()}IdentityResolver()
	{
		<#if relationship.many()>
		return (_, _) -> Optional.empty();
		<#elseif relationship.optional()>
		return (${beanNamePrefix()}DomainModel, _) -> ${beanNamePrefix()}DomainModel.${relationship.propertyName()}().map(${relationship.responseType()}::id);
		<#else>
		return (${beanNamePrefix()}DomainModel, _) -> Optional.ofNullable(${beanNamePrefix()}DomainModel.${relationship.propertyName()}()).map(${relationship.responseType()}::id);
		</#if>
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}LinkStrategy")
	SatelliteLinkStrategy<Long, ${modelBaseName()}DomainModel, ${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel> ${relationship.relationshipBeanNamePrefix()}LinkStrategy(
			final ModelBuilderFactory<${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder> ${beanNamePrefix()}DomainModelBuilderFactory,
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateFetchPort") final AggregateFetchPort<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel> ${relationship.relationshipVariablePrefix()}AggregateFetchPort,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainResponseBuilder") final DomainResponseBuilder<${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainResponseBuilder,
			@Qualifier("${relationship.satelliteQualifierPrefix()}DomainToAPIResponseAdapter") final DomainToAPIResponseAdapter<${relationship.responseType()}, Long, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter)
	{
		return new SatelliteLinkStrategy<>()
		{
			@Override
			public SatellitePersistenceOrder persistenceOrder()
			{
				return SatellitePersistenceOrder.SATELLITE_BEFORE_MASTER;
			}

			@Override
			public Optional<${relationship.satelliteDomainIdType()}> currentLinkedSatelliteDomainId(final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel)
			{
				<#if relationship.many()>
				return ${beanNamePrefix()}DomainModel.${relationship.propertyName()}().stream().findFirst().map(${relationship.responseType()}::id);
				<#elseif relationship.optional()>
				return ${beanNamePrefix()}DomainModel.${relationship.propertyName()}().map(${relationship.responseType()}::id);
				<#else>
				return Optional.ofNullable(${beanNamePrefix()}DomainModel.${relationship.propertyName()}()).map(${relationship.responseType()}::id);
				</#if>
			}

			@Override
			public Collection<${relationship.satelliteDomainIdType()}> currentLinkedSatelliteDomainIds(final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel)
			{
				<#if relationship.many()>
				return ${beanNamePrefix()}DomainModel.${relationship.propertyName()}().stream().map(${relationship.responseType()}::id).toList();
				<#elseif relationship.optional()>
				return ${beanNamePrefix()}DomainModel.${relationship.propertyName()}().stream().map(${relationship.responseType()}::id).toList();
				<#else>
				return Optional.ofNullable(${beanNamePrefix()}DomainModel.${relationship.propertyName()}()).stream().map(${relationship.responseType()}::id).toList();
				</#if>
			}

			@Override
			public ${modelBaseName()}DomainModel replaceLinkedSatelliteDomainIds(
					final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel,
					final Collection<${relationship.satelliteDomainIdType()}> satelliteDomainIds)
			{
				return rebuild${modelBaseName()}DomainModel(
						${beanNamePrefix()}DomainModelBuilderFactory,
						${beanNamePrefix()}DomainModel,
<#list relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()>
							<#if relationship.many()>satelliteDomainIds.stream().map(satelliteDomainId -> to${relationship.propertyCapitalizedName()}Response(${relationship.relationshipVariablePrefix()}AggregateFetchPort, ${relationship.relationshipVariablePrefix()}DomainResponseBuilder, ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter, satelliteDomainId)).toList()<#elseif relationship.optional()>satelliteDomainIds.stream().findFirst().map(satelliteDomainId -> to${relationship.propertyCapitalizedName()}Response(${relationship.relationshipVariablePrefix()}AggregateFetchPort, ${relationship.relationshipVariablePrefix()}DomainResponseBuilder, ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter, satelliteDomainId))<#else>satelliteDomainIds.stream().findFirst().map(satelliteDomainId -> to${relationship.propertyCapitalizedName()}Response(${relationship.relationshipVariablePrefix()}AggregateFetchPort, ${relationship.relationshipVariablePrefix()}DomainResponseBuilder, ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter, satelliteDomainId)).orElse(null)</#if>
						<#else>
							${beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()
						</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				);
			}

			@Override
			public ${modelBaseName()}DomainModel attachHydratedSatellites(
					final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel,
					final Collection<IdentifiedModel<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel>> satellites)
			{
				return rebuild${modelBaseName()}DomainModel(
						${beanNamePrefix()}DomainModelBuilderFactory,
						${beanNamePrefix()}DomainModel,
<#list relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()>
							<#if relationship.many()>satellites.stream().map(satellite -> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter.mapToAPIModelResponse(IdentifiedModel.of(satellite.id(), ${relationship.relationshipVariablePrefix()}DomainResponseBuilder.toResponse(satellite.model())))).toList()<#elseif relationship.optional()>satellites.stream().findFirst().map(satellite -> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter.mapToAPIModelResponse(IdentifiedModel.of(satellite.id(), ${relationship.relationshipVariablePrefix()}DomainResponseBuilder.toResponse(satellite.model()))))<#else>satellites.stream().findFirst().map(satellite -> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter.mapToAPIModelResponse(IdentifiedModel.of(satellite.id(), ${relationship.relationshipVariablePrefix()}DomainResponseBuilder.toResponse(satellite.model())))).orElse(null)</#if>
						<#else>
							${beanNamePrefix()}DomainModel.${rebuildRelationship.propertyName()}()
						</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				);
			}
		};
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}HydrationStrategy")
	SatelliteHydrationStrategy<Long, ${modelBaseName()}DomainModel, ${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel> ${relationship.relationshipBeanNamePrefix()}HydrationStrategy()
	{
		return (${beanNamePrefix()}DomainModel, satelliteFetchPort, satelliteLinkStrategy) -> hydrate(${beanNamePrefix()}DomainModel, satelliteFetchPort, satelliteLinkStrategy);
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}RelationshipDefinition")
	AggregateRelationshipDefinition<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelCreate, ${relationship.satelliteAggregate()}DomainModelUpdatePatch> ${relationship.relationshipBeanNamePrefix()}RelationshipDefinition(
			@Qualifier("${relationship.relationshipBeanNamePrefix()}LifecycleSemantics") final LifecycleSemantics lifecycleSemantics,
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelCreate, ${relationship.satelliteAggregate()}DomainModelUpdatePatch, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}AggregateCrudDefinition,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}CreateInputResolver") final SatelliteCreateInputResolver<${modelBaseName()}DomainModelCreate, Collection<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelCreate>>> createInputResolver,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}PatchInputResolver") final SatellitePatchInputResolver<${modelBaseName()}DomainModelUpdatePatch, Collection<SatelliteMutationIntent<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModelCreate, ${relationship.satelliteAggregate()}DomainModelUpdatePatch>>> patchInputResolver,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}IdentityResolver") final SatelliteIdentityResolver<${modelBaseName()}DomainModel, ${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteDomainIdType()}> identityResolver,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}LinkStrategy") final SatelliteLinkStrategy<Long, ${modelBaseName()}DomainModel, ${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel> linkStrategy,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}HydrationStrategy") final SatelliteHydrationStrategy<Long, ${modelBaseName()}DomainModel, ${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel> hydrationStrategy)
	{
		// TODO: Review reconciliation and lifecycle semantics for `${relationship.propertyName()}` before using this generated relationship in production.
		return AggregateRelationshipDefinitions
				.<Long, ${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelCreate, ${modelBaseName()}DomainModelUpdatePatch, ${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelCreate, ${relationship.satelliteAggregate()}DomainModelUpdatePatch>aggregateRelationshipDefinition()
				.name("${relationship.propertyName()}")
				.cardinality(Cardinality.${relationship.cardinality()})
				.lifecycleSemantics(lifecycleSemantics)
				.satelliteDefinition(${relationship.relationshipVariablePrefix()}AggregateCrudDefinition)
				.createInputResolver(createInputResolver)
				.patchInputResolver(patchInputResolver)
				.identityResolver(identityResolver)
				.reconciliationStrategy(ReconciliationStrategy.${relationship.reconciliationStrategy()})
				.linkStrategy(linkStrategy)
				.hydrationStrategy(hydrationStrategy)
				.build();
	}

</#list>
	private <SatelliteDomainId, SatelliteDomainModel, MasterDomainId, MasterDomainModel>
	MasterDomainModel hydrate(
			final IdentifiedModel<MasterDomainId, MasterDomainModel> master,
			final AggregateFetchPort<SatelliteDomainId, SatelliteDomainModel> satelliteFetchPort,
			final SatelliteLinkStrategy<MasterDomainId, MasterDomainModel, SatelliteDomainId, SatelliteDomainModel> satelliteLinkStrategy)
	{
		return satelliteLinkStrategy.currentLinkedSatelliteDomainIds(master.model())
		                            .stream()
		                            .map(satelliteFetchPort::findById)
		                            .flatMap(Optional::stream)
		                            .collect(java.util.stream.Collectors.collectingAndThen(
									java.util.stream.Collectors.toList(),
									satellites -> satellites.isEmpty()
											? master.model()
											: satelliteLinkStrategy.attachHydratedSatellites(master.model(), satellites)));
	}

	private ${modelBaseName()}DomainModel rebuild${modelBaseName()}DomainModel(
			final ModelBuilderFactory<${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder> ${beanNamePrefix()}DomainModelBuilderFactory,
			final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel,
<#list relationships() as relationship>
			final ${relationship.responseFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>)
	{
		return ${beanNamePrefix()}DomainModelBuilderFactory.builder()
<#list standaloneProperties() as property>
				.with${property.capitalizedName()}(${beanNamePrefix()}DomainModel.${property.getter()}())
</#list>
<#list relationships() as relationship>
				.with${relationship.propertyCapitalizedName()}(${relationship.propertyName()})
</#list>
				.build();
	}

<#list relationships() as relationship>
	private ${relationship.responseType()} to${relationship.propertyCapitalizedName()}Response(
			final AggregateFetchPort<${relationship.satelliteDomainIdType()}, ${relationship.satelliteAggregate()}DomainModel> ${relationship.relationshipVariablePrefix()}AggregateFetchPort,
			final DomainResponseBuilder<${relationship.satelliteAggregate()}DomainModel, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainResponseBuilder,
			final DomainToAPIResponseAdapter<${relationship.responseType()}, Long, ${relationship.satelliteAggregate()}DomainModelResponse> ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter,
			final ${relationship.satelliteDomainIdType()} satelliteDomainId)
	{
		var ${relationship.satelliteBeanNamePrefix()}DomainModel = ${relationship.relationshipVariablePrefix()}AggregateFetchPort.findById(satelliteDomainId)
		                                                                                                  .orElseThrow(() -> ResourceNotFoundException.withId(satelliteDomainId));
		return ${relationship.relationshipVariablePrefix()}DomainToAPIResponseAdapter.mapToAPIModelResponse(
				IdentifiedModel.of(
						${relationship.satelliteBeanNamePrefix()}DomainModel.id(),
						${relationship.relationshipVariablePrefix()}DomainResponseBuilder.toResponse(${relationship.satelliteBeanNamePrefix()}DomainModel.model())));
	}

</#list>
}


