package de.gupta.clean.crud.generator.code.generation.writing.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;
import de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.application.service.SourceCodeFileWriterService;
import de.gupta.commons.utility.javaLanguage.classes.ClassWritingUtility;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
final class SourceCodeFileWriterServiceImpl implements SourceCodeFileWriterService
{
	private static final Pattern TOP_LEVEL_TYPE_PATTERN = Pattern.compile(
			"(?m)^\s*(?:public\s+)?(?:(?:final|abstract|sealed|non-sealed|static)\s+)*(?:class|interface|record|enum)\s+([A-Za-z_][A-Za-z0-9_]*)");
	@Override
	public void writeSourceCode(final SourceCodeWriteRequest request)
	{
		Objects.requireNonNull(request, "request must not be null");
		if (request.contentRootPath() == null || request.fileName() == null || request.sourceCode() == null)
		{
			throw new IllegalArgumentException(
					"Source code write request must have non-null contentRootPath, fileName, and sourceCode");
		}
		ClassWritingUtility.writeClass(resolveFileName(request), request.sourceCode(), request.contentRootPath(),
				request.overwriteExistingFile());
	}

	private String resolveFileName(final SourceCodeWriteRequest request)
	{
		return detectTopLevelTypeFileName(request.sourceCode()).orElse(request.fileName());
	}

	private Optional<String> detectTopLevelTypeFileName(final String sourceCode)
	{
		var matcher = TOP_LEVEL_TYPE_PATTERN.matcher(sourceCode);
		return matcher.find() ? Optional.of(matcher.group(1) + ".java") : Optional.empty();
	}
}