package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;

import java.util.List;
import java.util.Map;
import java.util.SequencedCollection;
import java.util.Set;

public final class TemplateModelFactory
{
	public static TemplateModel create(final String packageName, final String modelName)
	{
		return create(packageName, modelName, List.of(), Set.of(), Map.of(), Map.of(), Map.of(), Set.of(), false,
				List.of());
	}

	public static TemplateModel create(
			final String packageName,
			final String modelName,
			final SequencedCollection<String> genericTypeParameters,
			final Set<Property> properties,
			final Map<String, String> domainGenericTypes,
			final Map<String, String> persistenceGenericTypes,
			final Map<String, String> apiGenericTypes,
			final Set<String> domainGenericImports,
			final boolean historized,
			final java.util.List<GeneratedRelationship> relationships)
	{
		AggregateDescriptor aggregate = new AggregateDescriptorImpl(packageName, modelName, historized);
		AggregateComposition composition = new AggregateCompositionImpl(properties, relationships);
		TemplateTypeBindings types = new TemplateTypeBindingsImpl(
				genericTypeParameters,
				domainGenericTypes,
				persistenceGenericTypes,
				apiGenericTypes);
		DomainProjection domain = new DomainProjectionImpl(composition, domainGenericTypes, domainGenericImports);
		PersistenceProjection persistence = new PersistenceProjectionImpl(
				aggregate,
				composition,
				persistenceGenericTypes,
				domainGenericImports);
		ApiProjection api = new ApiProjectionImpl(composition, apiGenericTypes, domainGenericImports);
		return new TemplateModelImpl(aggregate, composition, domain, persistence, api, types);
	}

	private TemplateModelFactory()
	{
	}
}
