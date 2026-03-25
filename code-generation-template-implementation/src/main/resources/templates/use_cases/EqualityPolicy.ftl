<#-- Template for generating DomainEqualityPolicy implementation -->
package ${basePackage()}.domain.service.equality;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import de.gupta.clean.crud.template.domain.service.equality.DomainEqualityPolicy;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
final class ${modelBaseName()}DomainEqualityPolicy implements DomainEqualityPolicy<${modelBaseName()}DomainModel>
{
	@Override
	public boolean areEqual(final ${modelBaseName()}DomainModel left, final ${modelBaseName()}DomainModel right)
	{
		// TODO from Template: replace this with the business-key equality your API should use for duplicate detection.
		return Objects.equals(left, right);
	}
}
