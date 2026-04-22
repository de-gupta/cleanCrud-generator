package de.gupta.clean.crud.implementation.examples.person.useCases.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gupta.clean.crud.implementation.examples.note.useCases.crud.common.dto.NoteAPIModelCreate;
import de.gupta.clean.crud.implementation.examples.note.useCases.crud.common.dto.NoteAPIModelResponse;
import de.gupta.clean.crud.implementation.examples.person.useCases.crud.common.dto.PersonAPIModelCreate;
import de.gupta.clean.crud.implementation.examples.person.useCases.crud.common.dto.PersonAPIModelResponse;
import de.gupta.clean.crud.implementation.examples.setup.IntegrationTest;
import de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelCreate;
import de.gupta.clean.crud.implementation.examples.version.useCases.crud.common.dto.VersionAPIModelResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Transactional
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class AbstractPersonITCase
{
	private static final AtomicLong VERSION_COUNTER = new AtomicLong(1_000L);

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	protected VersionAPIModelResponse createVersion() throws Exception
	{
		long versionNumber = VERSION_COUNTER.incrementAndGet();
		var result = mockMvc.perform(post("/version/save")
										 .contentType(MediaType.APPLICATION_JSON)
										 .content(objectMapper.writeValueAsString(new VersionAPIModelCreate(versionNumber))))
								.andExpect(status().isCreated())
								.andReturn();
		return objectMapper.readValue(result.getResponse().getContentAsString(), VersionAPIModelResponse.class);
	}

	protected PersonAPIModelResponse createPerson(final PersonAPIModelCreate personToCreate) throws Exception
	{
		MvcResult result = mockMvc.perform(post("/person/save")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(objectMapper.writeValueAsString(personToCreate)))
								  .andDo(print())
								  .andExpect(status().isCreated())
								  .andReturn();

		return objectMapper.readValue(result.getResponse().getContentAsString(), PersonAPIModelResponse.class);
	}

	protected PersonAPIModelCreate requestFor(
			final PersonScenario scenario,
			final VersionAPIModelResponse currentVersion,
			final Optional<VersionAPIModelResponse> lastKnownVersion)
	{
		return new PersonAPIModelCreate(
				scenario.user(),
				scenario.something(),
				scenario.title(),
				scenario.firstName(),
				scenario.lastName(),
				scenario.birthDate(),
				scenario.notesToCreate(),
				currentVersion.id(),
				lastKnownVersion.map(VersionAPIModelResponse::id));
	}

	protected void assertPersonMatchesScenario(
			final PersonScenario scenario,
			final PersonAPIModelResponse actual,
			final VersionAPIModelResponse currentVersion,
			final Optional<VersionAPIModelResponse> lastKnownVersion)
	{
		assertThat(actual.id()).isNotNull();
		assertThat(actual.user()).isEqualTo(scenario.user());
		assertThat(actual.something()).isEqualTo(scenario.something());
		assertThat(actual.title()).isEqualTo(scenario.title());
		assertThat(actual.firstName()).isEqualTo(scenario.firstName());
		assertThat(actual.lastName()).isEqualTo(scenario.lastName());
		assertThat(actual.birthDate()).isEqualTo(scenario.birthDate());

		assertThat(actual.currentVersion()).isNotNull();
		assertThat(actual.currentVersion().id()).isEqualTo(currentVersion.id());
		assertThat(actual.currentVersion().version()).isEqualTo(currentVersion.version());

		assertThat(actual.lastKnownVersion().map(VersionAPIModelResponse::id))
				.isEqualTo(lastKnownVersion.map(VersionAPIModelResponse::id));
		assertThat(actual.lastKnownVersion().map(VersionAPIModelResponse::version))
				.isEqualTo(lastKnownVersion.map(VersionAPIModelResponse::version));

		assertThat(actual.notes())
				.extracting(NoteAPIModelResponse::note)
				.containsExactlyElementsOf(scenario.expectedNoteTexts());
		assertThat(actual.notes())
				.extracting(NoteAPIModelResponse::id)
				.allMatch(id -> id != null);
	}

	protected static Stream<Arguments> personCreateScenarios()
	{
		return Stream.of(
				Arguments.of(PersonScenario.minimal()),
				Arguments.of(PersonScenario.withOptionalScalars()),
				Arguments.of(PersonScenario.withOwnedNotes()),
				Arguments.of(PersonScenario.fullRelationships()));
	}

	protected record PersonScenario(
			String label,
			String user,
			Optional<Integer> something,
			Optional<String> title,
			String firstName,
			Optional<String> lastName,
			LocalDate birthDate,
			List<NoteAPIModelCreate> notesToCreate,
			boolean includeLastKnownVersion)
	{
		static PersonScenario minimal()
		{
			return new PersonScenario(
					"minimal valid",
					"101",
					Optional.empty(),
					Optional.empty(),
					"Alice",
					Optional.empty(),
					LocalDate.of(1990, 1, 15),
					List.of(),
					false);
		}

		static PersonScenario withOptionalScalars()
		{
			return new PersonScenario(
					"optional scalars present",
					"202",
					Optional.of(42),
					Optional.of("Dr"),
					"Beatrice",
					Optional.of("Carter"),
					LocalDate.of(1985, 5, 20),
					List.of(),
					false);
		}

		static PersonScenario withOwnedNotes()
		{
			return new PersonScenario(
					"owned notes present",
					"303",
					Optional.of(77),
					Optional.empty(),
					"Charlie",
					Optional.of("Delta"),
					LocalDate.of(1978, 9, 10),
					List.of(
							new NoteAPIModelCreate("first note"),
							new NoteAPIModelCreate("second note")),
					false);
		}

		static PersonScenario fullRelationships()
		{
			return new PersonScenario(
					"full relationship variant",
					"404",
					Optional.of(88),
					Optional.of("Mx"),
					"Dana",
					Optional.of("Edwards"),
					LocalDate.of(1995, 12, 1),
					List.of(
							new NoteAPIModelCreate("linked note A"),
							new NoteAPIModelCreate("linked note B")),
					true);
		}

		List<String> expectedNoteTexts()
		{
			return notesToCreate.stream().map(NoteAPIModelCreate::note).toList();
		}

		@Override
		public String toString()
		{
			return label;
		}
	}
}
