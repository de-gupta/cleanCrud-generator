package de.gupta.clean.crud.implementation.examples.person.useCases.crud;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonDELETEITCase extends AbstractPersonITCase
{
	@Test
	void shouldDeletePersonById() throws Exception
	{
		var scenario = PersonScenario.fullRelationships();
		var currentVersion = createVersion();
		var lastKnownVersion = Optional.of(createVersion());
		var createdPerson = createPerson(requestFor(scenario, currentVersion, lastKnownVersion));

		mockMvc.perform(delete("/person/delete/{id}", createdPerson.id()))
			   .andExpect(status().isNoContent());

		mockMvc.perform(get("/person/fetch/{id}", createdPerson.id()))
			   .andExpect(status().isNotFound());
	}
}
