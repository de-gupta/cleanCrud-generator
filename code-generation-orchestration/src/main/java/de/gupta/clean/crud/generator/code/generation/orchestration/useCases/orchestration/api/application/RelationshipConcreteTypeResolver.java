package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.LayerConcreteTypes;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.GeneratedRelationship;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
final class RelationshipConcreteTypeResolver
{
	LayerConcreteTypes merge(final LayerConcreteTypes configuredTypes, final List<GeneratedRelationship> relationships)
	{
		Map<String, String> domain = new LinkedHashMap<>(configuredTypes.domain());
		Map<String, String> persistence = new LinkedHashMap<>(configuredTypes.persistence());
		Map<String, String> api = new LinkedHashMap<>(configuredTypes.api());
		for (GeneratedRelationship relationship : relationships)
		{
			domain.put(relationship.genericPlaceholder(),
					"de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel<" + relationship.satelliteDomainIdType() + ", " + relationship.domainModelType() + ">");
			persistence.put(relationship.genericPlaceholder(), relationship.satellitePersistenceIdType());
			api.put(relationship.genericPlaceholder(), relationship.responseType());
		}
		return new LayerConcreteTypes(Map.copyOf(domain), Map.copyOf(persistence), Map.copyOf(api));
	}
}
