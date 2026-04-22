package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.SourceCodeFile;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.regex.Pattern;

@Component
final class SourceCodeTargetPathResolver
{
	private static final Pattern PACKAGE_PATTERN = Pattern.compile("(?m)^\\s*package\\s+([a-zA-Z_][\\w.]*)\\s*;");

	Path resolve(final Path contentRootPath, final SourceCodeFile sourceCodeFile)
	{
		var sourceCode = sourceCodeFile.sourceCode().sourceCode();
		var matcher = PACKAGE_PATTERN.matcher(sourceCode);
		if (!matcher.find())
		{
			return contentRootPath.resolve(sourceCodeFile.fileName());
		}
		return contentRootPath.resolve(matcher.group(1).replace('.', java.io.File.separatorChar))
		                      .resolve(sourceCodeFile.fileName());
	}
}
