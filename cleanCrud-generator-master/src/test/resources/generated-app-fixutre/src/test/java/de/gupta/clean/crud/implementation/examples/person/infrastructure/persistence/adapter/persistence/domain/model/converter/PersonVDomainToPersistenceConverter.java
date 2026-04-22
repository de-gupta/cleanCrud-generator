package de.gupta.clean.crud.implementation.examples.person.infrastructure.persistence.adapter.persistence.domain.model.converter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Qualifier("personVDomainToPersistenceConverter")
final class PersonVDomainToPersistenceConverter implements Function<Integer, String>
{
	@Override
	public String apply(final Integer domainValue)
	{
		return domainValue == null ? null : String.valueOf(domainValue);
	}
}
