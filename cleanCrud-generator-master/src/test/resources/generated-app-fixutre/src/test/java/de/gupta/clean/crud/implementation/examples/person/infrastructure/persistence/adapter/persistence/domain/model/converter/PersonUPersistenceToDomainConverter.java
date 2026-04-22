package de.gupta.clean.crud.implementation.examples.person.infrastructure.persistence.adapter.persistence.domain.model.converter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Qualifier("personUPersistenceToDomainConverter")
final class PersonUPersistenceToDomainConverter implements Function<Integer, String>
{
	@Override
	public String apply(final Integer persistenceValue)
	{
		return persistenceValue == null ? null : String.valueOf(persistenceValue);
	}
}
