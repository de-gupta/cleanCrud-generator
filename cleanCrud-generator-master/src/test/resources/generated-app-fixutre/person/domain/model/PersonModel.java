package de.gupta.clean.crud.implementation.examples.person.domain.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface PersonModel<V, N>
{
	Optional<String> title();
	String firstName();
	Optional<String> lastName();
	LocalDate birthDate();
	V currentVersion();
	Optional<V> lastKnownVersion();
	Collection<N> notes();
}
