package ${aggregate().basePackage()}.useCases.crud.configuration;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelCreate;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelUpdatePatch;
<#list composition().relationships() as relationship>
import ${relationship.domainModelImport()};
import ${relationship.domainCreateImport(aggregate().basePackage())};
import ${relationship.domainUpdatePatchImport(aggregate().basePackage())};
</#list>
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
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatelliteHydrationStrategy;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatelliteIdentityResolver;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatelliteLinkStrategy;
import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.SatellitePersistenceOrder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Configuration
class ${aggregate().baseName()}CrudRelationshipConfiguration
{
<#list composition().relationships() as relationship>
	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}LifecycleSemantics")
	LifecycleSemantics ${relationship.relationshipBeanNamePrefix()}LifecycleSemantics()
	{
		// TODO: Review the generated lifecycle defaults for `${relationship.propertyName()}` and change them if your semantics differ.
		return LifecycleSemanticsBuilder.lifecycleSemantics()
				<#if relationship.cascadeCreate()>.cascadeCreate()
				</#if><#if relationship.cascadeUpdate()>.cascadeUpdate()
				</#if><#if relationship.cascadeDelete()>.cascadeDelete()
				</#if><#if relationship.orphanDelete()>.orphanDelete()
				</#if><#if relationship.hydrateOnFetch()>.hydrateOnFetch()
				</#if>.build();
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}IdentityResolver")
	SatelliteIdentityResolver<${aggregate().baseName()}DomainModel, ${relationship.domainModelType()}, ${relationship.satelliteDomainIdType()}> ${relationship.relationshipBeanNamePrefix()}IdentityResolver()
	{
		return (masterDomainModel, satelliteDomainModel) ->
		{
			<#if relationship.many()>
			return masterDomainModel.${relationship.propertyName()}().stream()
					.filter(candidate -> candidate.model().equals(satelliteDomainModel))
					.map(IdentifiedModel::id)
					.findFirst();
			<#elseif relationship.optional()>
			return masterDomainModel.${relationship.propertyName()}()
					.filter(candidate -> candidate.model().equals(satelliteDomainModel))
					.map(IdentifiedModel::id);
			<#else>
			return Optional.ofNullable(masterDomainModel.${relationship.propertyName()}())
					.filter(candidate -> candidate.model().equals(satelliteDomainModel))
					.map(IdentifiedModel::id);
			</#if>
		};
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}LinkStrategy")
	SatelliteLinkStrategy<${aggregate().rootDomainIdType()}, ${aggregate().baseName()}DomainModel, ${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}> ${relationship.relationshipBeanNamePrefix()}LinkStrategy(
			final ModelBuilderFactory<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder> ${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateFetchPort") final AggregateFetchPort<${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}> ${relationship.relationshipVariablePrefix()}AggregateFetchPort)
	{
		return new SatelliteLinkStrategy<>()
		{
			@Override
			public SatellitePersistenceOrder persistenceOrder()
			{
				return SatellitePersistenceOrder.SATELLITE_BEFORE_MASTER;
			}

			@Override
			public Optional<${relationship.satelliteDomainIdType()}> currentLinkedSatelliteDomainId(
					final ${aggregate().baseName()}DomainModel masterDomainModel)
			{
				return currentLinkedSatelliteDomainIds(masterDomainModel).stream().findFirst();
			}

			@Override
			public Collection<${relationship.satelliteDomainIdType()}> currentLinkedSatelliteDomainIds(
					final ${aggregate().baseName()}DomainModel masterDomainModel)
			{
				<#if relationship.many()>
				return masterDomainModel.${relationship.propertyName()}().stream().map(IdentifiedModel::id).toList();
				<#elseif relationship.optional()>
				return masterDomainModel.${relationship.propertyName()}().stream().map(IdentifiedModel::id).toList();
				<#else>
				return Optional.ofNullable(masterDomainModel.${relationship.propertyName()}()).stream().map(IdentifiedModel::id).toList();
				</#if>
			}

			@Override
			public ${aggregate().baseName()}DomainModel replaceLinkedSatelliteDomainIds(
					final ${aggregate().baseName()}DomainModel masterDomainModel,
					final Collection<${relationship.satelliteDomainIdType()}> satelliteDomainIds)
			{
				return attachHydratedSatellites(
						masterDomainModel,
						satelliteDomainIds.stream()
								.map(satelliteDomainId -> ${relationship.relationshipVariablePrefix()}AggregateFetchPort.findById(satelliteDomainId)
										.orElseThrow(() -> ResourceNotFoundException.withId(satelliteDomainId)))
								.toList());
			}

			@Override
			public ${aggregate().baseName()}DomainModel attachHydratedSatellites(
					final ${aggregate().baseName()}DomainModel masterDomainModel,
					final Collection<IdentifiedModel<${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}>> satellites)
			{
				<#if relationship.many()>
				return rebuild${aggregate().baseName()}DomainModel(
						${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
						masterDomainModel,
<#list composition().relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()>satellites<#else>masterDomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				);
				<#elseif relationship.optional()>
				if (satellites.size() > 1)
				{
					throw InvalidRequestException.withMessage("${aggregate().baseName()} ${relationship.propertyName()} can hold at most one satellite");
				}
				return rebuild${aggregate().baseName()}DomainModel(
						${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
						masterDomainModel,
<#list composition().relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()>satellites.stream().findFirst()<#else>masterDomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				);
				<#else>
				if (satellites.size() > 1)
				{
					throw InvalidRequestException.withMessage("${aggregate().baseName()} ${relationship.propertyName()} can hold at most one satellite");
				}
				return rebuild${aggregate().baseName()}DomainModel(
						${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
						masterDomainModel,
<#list composition().relationships() as rebuildRelationship>
						<#if rebuildRelationship.propertyName() == relationship.propertyName()>satellites.stream().findFirst().orElse(null)<#else>masterDomainModel.${rebuildRelationship.propertyName()}()</#if><#if rebuildRelationship_has_next>,</#if>
</#list>
				);
				</#if>
			}
		};
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}HydrationStrategy")
	SatelliteHydrationStrategy<${aggregate().rootDomainIdType()}, ${aggregate().baseName()}DomainModel, ${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}> ${relationship.relationshipBeanNamePrefix()}HydrationStrategy()
	{
		return (master, satelliteFetchPort, satelliteLinkStrategy) ->
		{
			var hydratedSatellites = new ArrayList<IdentifiedModel<${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}>>();
			for (var satelliteDomainId : satelliteLinkStrategy.currentLinkedSatelliteDomainIds(master.model()))
			{
				satelliteFetchPort.findById(satelliteDomainId).ifPresent(hydratedSatellites::add);
			}
			return satelliteLinkStrategy.attachHydratedSatellites(master.model(), hydratedSatellites);
		};
	}

	@Bean
	@Qualifier("${relationship.relationshipBeanNamePrefix()}RelationshipDefinition")
	AggregateRelationshipDefinition<${aggregate().rootDomainIdType()}, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}> ${relationship.relationshipBeanNamePrefix()}RelationshipDefinition(
			@Qualifier("${relationship.relationshipBeanNamePrefix()}LifecycleSemantics") final LifecycleSemantics lifecycleSemantics,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}IdentityResolver") final SatelliteIdentityResolver<${aggregate().baseName()}DomainModel, ${relationship.domainModelType()}, ${relationship.satelliteDomainIdType()}> identityResolver,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}LinkStrategy") final SatelliteLinkStrategy<${aggregate().rootDomainIdType()}, ${aggregate().baseName()}DomainModel, ${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}> linkStrategy,
			@Qualifier("${relationship.relationshipBeanNamePrefix()}HydrationStrategy") final SatelliteHydrationStrategy<${aggregate().rootDomainIdType()}, ${aggregate().baseName()}DomainModel, ${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}> hydrationStrategy,
			@Qualifier("${relationship.satelliteQualifierPrefix()}AggregateCrudDefinition") final AggregateCrudDefinition<${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}, ?> ${relationship.relationshipVariablePrefix()}AggregateCrudDefinition)
	{
		return AggregateRelationshipDefinitions
				.<${aggregate().rootDomainIdType()}, ${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelCreate, ${aggregate().baseName()}DomainModelUpdatePatch, ${relationship.satelliteDomainIdType()}, ${relationship.domainModelType()}, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}>aggregateRelationshipDefinition()
				.name("${relationship.propertyName()}")
				.cardinality(Cardinality.${relationship.cardinality()})
				.lifecycleSemantics(lifecycleSemantics)
				.satelliteDefinition(${relationship.relationshipVariablePrefix()}AggregateCrudDefinition)
				.createInputResolver(masterCreate ->
				{
					<#if relationship.referenced()>
					<#if relationship.many()>
					return masterCreate.${relationship.propertyName()}().stream()
							.<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.domainCreateType()}>>map(SatelliteCreateIntent.ReferenceSatelliteCreateIntent::new)
							.toList();
					<#elseif relationship.optional()>
					return masterCreate.${relationship.propertyName()}().stream()
							.<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.domainCreateType()}>>map(SatelliteCreateIntent.ReferenceSatelliteCreateIntent::new)
							.toList();
					<#else>
					return List.<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.domainCreateType()}>>of(
							new SatelliteCreateIntent.ReferenceSatelliteCreateIntent<>(masterCreate.${relationship.propertyName()}()));
					</#if>
					<#else>
					<#if relationship.many()>
					return masterCreate.${relationship.propertyName()}().stream()
							.<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.domainCreateType()}>>map(SatelliteCreateIntent.InlineSatelliteCreateIntent::new)
							.toList();
					<#elseif relationship.optional()>
					return masterCreate.${relationship.propertyName()}().stream()
							.<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.domainCreateType()}>>map(SatelliteCreateIntent.InlineSatelliteCreateIntent::new)
							.toList();
					<#else>
					return List.<SatelliteCreateIntent<${relationship.satelliteDomainIdType()}, ${relationship.domainCreateType()}>>of(
							new SatelliteCreateIntent.InlineSatelliteCreateIntent<>(masterCreate.${relationship.propertyName()}()));
					</#if>
					</#if>
				})
				.patchInputResolver(masterPatch ->
				{
					var mutationIntents = new ArrayList<SatelliteMutationIntent<${relationship.satelliteDomainIdType()}, ${relationship.domainCreateType()}, ${relationship.domainUpdatePatchType()}>>();
					<#if relationship.referenced()>
					<#if relationship.many()>
					masterPatch.${relationship.propertyName()}().orElse(List.of()).forEach(satelliteDomainId ->
							mutationIntents.add(new SatelliteMutationIntent.ReferenceSatelliteMutationIntent<>(satelliteDomainId)));
					<#else>
					masterPatch.${relationship.propertyName()}().ifPresent(satelliteDomainId ->
							mutationIntents.add(new SatelliteMutationIntent.ReferenceSatelliteMutationIntent<>(satelliteDomainId)));
					</#if>
					<#else>
					<#if relationship.many()>
					masterPatch.${relationship.propertyName()}().orElse(List.of()).forEach(item ->
					{
						if (item.id().isPresent())
						{
							mutationIntents.add(new SatelliteMutationIntent.UpdateSatelliteMutationIntent<>(
									item.id().orElseThrow(),
									item.patch()));
						}
						else
						{
							mutationIntents.add(new SatelliteMutationIntent.CreateSatelliteMutationIntent<>(
									${relationship.domainCreateType()}.fromUpdatePatch(item.patch())));
						}
					});
					<#else>
					masterPatch.${relationship.propertyName()}().ifPresent(satelliteDomainModelUpdatePatch ->
							mutationIntents.add(new SatelliteMutationIntent.UpsertCurrentSatelliteMutationIntent<>(
									${relationship.domainCreateType()}.fromUpdatePatch(satelliteDomainModelUpdatePatch),
									satelliteDomainModelUpdatePatch)));
					</#if>
					</#if>
					masterPatch.${relationship.removeFieldName()}().forEach(satelliteDomainId ->
							mutationIntents.add(new SatelliteMutationIntent.RemoveSatelliteMutationIntent<>(satelliteDomainId)));
					return mutationIntents;
				})
				.identityResolver(identityResolver)
				.reconciliationStrategy(ReconciliationStrategy.${relationship.reconciliationStrategy()})
				.linkStrategy(linkStrategy)
				.hydrationStrategy(hydrationStrategy)
				.build();
	}

</#list>
	private ${aggregate().baseName()}DomainModel rebuild${aggregate().baseName()}DomainModel(
			final ModelBuilderFactory<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder> ${aggregate().beanNamePrefix()}DomainModelBuilderFactory,
			final ${aggregate().baseName()}DomainModel ${aggregate().beanNamePrefix()}DomainModel,
<#list composition().relationships() as relationship>
			final ${relationship.domainFieldType()} ${relationship.propertyName()}<#if relationship_has_next>,</#if>
</#list>)
	{
		return ${aggregate().beanNamePrefix()}DomainModelBuilderFactory.builder()
<#list composition().standaloneProperties() as property>
				.with${property.capitalizedName()}(${aggregate().beanNamePrefix()}DomainModel.${property.getter()}())
</#list>
<#list composition().relationships() as relationship>
				.with${relationship.propertyCapitalizedName()}(${relationship.propertyName()})
</#list>
				.build();
	}
}
