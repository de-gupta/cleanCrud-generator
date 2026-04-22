package de.gupta.clean.crud.implementation.examples.person.useCases.crud;

import de.gupta.clean.crud.implementation.examples.person.useCases.crud.common.dto.PersonAPIModelResponse;
import de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonGETITCase extends AbstractPersonITCase
{
	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("personCreateScenarios")
	void shouldFetchCreatedPerson(final PersonScenario scenario) throws Exception
	{
		VersionAPIModelResponse currentVersion = createVersion();
		Optional<VersionAPIModelResponse> lastKnownVersion = scenario.includeLastKnownVersion()
				? Optional.of(createVersion())
				: Optional.empty();

		PersonAPIModelResponse createdPerson = createPerson(requestFor(scenario, currentVersion, lastKnownVersion));

		var result = mockMvc.perform(get("/person/fetch/{id}", createdPerson.id()))
							.andDo(print())
							.andExpect(status().isOk())
							.andReturn();

		PersonAPIModelResponse fetchedPerson =
				objectMapper.readValue(result.getResponse().getContentAsString(), PersonAPIModelResponse.class);

		assertPersonMatchesScenario(scenario, fetchedPerson, currentVersion, lastKnownVersion);
	}
}
