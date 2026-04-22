package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultOwnershipPolicyTest
{
	private final DefaultOwnershipPolicy policy = new DefaultOwnershipPolicy();

	@Test
	void resolvesLayerSpecificOwnershipDefaults()
	{
		var configuration = new CodeGenerationConfiguration(
				new GenerationInputs("TaskModel.java", null, null, null),
				LayerConcreteTypes.defaults(),
				GenerationSelection.defaults(),
				List.of(),
				new OwnershipConfiguration(
						GeneratedArtifactOwnership.USER,
						GeneratedArtifactOwnership.GENERATED,
						GeneratedArtifactOwnership.USER,
						GeneratedArtifactOwnership.GENERATED,
						java.util.Map.of(),
						java.util.Map.of(),
						java.util.Map.of()
				),
				OverwriteConfiguration.defaults(),
				false
		);

		assertEquals(GeneratedArtifactOwnership.USER, policy.ownershipOf("BaseModel", configuration));
		assertEquals(GeneratedArtifactOwnership.GENERATED, policy.ownershipOf("DomainModel", configuration));
		assertEquals(GeneratedArtifactOwnership.USER, policy.ownershipOf("PersistenceModel", configuration));
		assertEquals(GeneratedArtifactOwnership.GENERATED, policy.ownershipOf("APIModelCreateDTO", configuration));
		assertEquals(GeneratedArtifactOwnership.GENERATED, policy.ownershipOf("CrudPortsConfiguration", configuration));
	}
}