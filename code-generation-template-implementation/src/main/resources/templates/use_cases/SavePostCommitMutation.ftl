package ${postCommitHooks().savePackage()};

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutation;
import de.gupta.clean.crud.template.useCases.crud.aggregate.definition.PostCommitMutationContext;
import org.springframework.stereotype.Component;

@Component("${postCommitHooks().saveQualifier()}")
class ${postCommitHooks().saveTypeName()} implements PostCommitMutation<Long, ${aggregate().baseName()}DomainModel>
{
	@Override
	public void accept(final PostCommitMutationContext<Long, ${aggregate().baseName()}DomainModel> context)
	{
		// TODO: implement post-commit behavior for successful save/create operations.
	}
}
