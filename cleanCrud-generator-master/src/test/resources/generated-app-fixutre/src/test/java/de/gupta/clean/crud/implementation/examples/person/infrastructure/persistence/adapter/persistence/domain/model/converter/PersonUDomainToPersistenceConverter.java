package de.gupta.clean.crud.implementation.examples.person.infrastructure.persistence.adapter.persistence.domain.model.converter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Qualifier("personUDomainToPersistenceConverter")
final class PersonUDomainToPersistenceConverter implements Function<String, Integer>
{
	@Override
	public Integer apply(final String domainValue)
	{
		return domainValue == null ? null : Integer.valueOf(domainValue);
	}
}
