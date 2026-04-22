package de.gupta.clean.crud.implementation.examples.person.useCases.crud;

import de.gupta.clean.crud.implementation.examples.person.useCases.crud.common.dto.PersonAPIModelResponse;
import de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;

class PersonPOSTITCase extends AbstractPersonITCase
{
	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("personCreateScenarios")
	void shouldCreatePerson(final PersonScenario scenario) throws Exception
	{
		VersionAPIModelResponse currentVersion = createVersion();
		Optional<VersionAPIModelResponse> lastKnownVersion = scenario.includeLastKnownVersion()
				? Optional.of(createVersion())
				: Optional.empty();

		PersonAPIModelResponse createdPerson = createPerson(requestFor(scenario, currentVersion, lastKnownVersion));

		assertPersonMatchesScenario(scenario, createdPerson, currentVersion, lastKnownVersion);
	}
}