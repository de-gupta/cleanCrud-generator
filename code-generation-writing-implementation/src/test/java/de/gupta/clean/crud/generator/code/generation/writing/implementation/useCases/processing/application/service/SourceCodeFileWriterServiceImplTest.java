package de.gupta.clean.crud.generator.code.generation.writing.implementation.useCases.processing.application.service;

import de.gupta.clean.crud.generator.code.generation.writing.api.domain.model.SourceCodeWriteRequest;
import de.gupta.commons.utility.javaLanguage.classes.ClassWritingUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SourceCodeFileWriterServiceImplTest
{

	private static final String SEPARATOR = File.separator;
	private final SourceCodeFileWriterServiceImpl service = new SourceCodeFileWriterServiceImpl();

	@Nested
	@DisplayName("Tests for createPath method")
	class CreatePathTests
	{

		static Stream<Arguments> createPathTestCases()
		{
			return Stream.of(
					// Normal cases
					Arguments.of("src/main", "com.example", "Test.java",
							"src/main" + SEPARATOR + "com" + SEPARATOR + "example" + SEPARATOR + "Test.java"),
					Arguments.of("C:\\Project", "org.test", "Main.java",
							"C:\\Project" + SEPARATOR + "org" + SEPARATOR + "test" + SEPARATOR + "Main.java"),

					// Empty package name
					Arguments.of("root", "", "File.txt",
							"root" + SEPARATOR + "File.txt"),

					// Deep package structure
					Arguments.of("/home/user", "a.very.deep.package.structure", "DeepClass.java",
							"/home/user" + SEPARATOR + "a" + SEPARATOR + "very" + SEPARATOR + "deep" + SEPARATOR +
									"package" + SEPARATOR + "structure" + SEPARATOR + "DeepClass.java"),

					// Content root with trailing separator
					Arguments.of("base" + SEPARATOR, "com.test", "File.java",
							"base" + SEPARATOR + "com" + SEPARATOR + "test" + SEPARATOR + "File.java"),

					// Special characters in file name
					Arguments.of("root", "org.example", "Test$Inner.class",
							"root" + SEPARATOR + "org" + SEPARATOR + "example" + SEPARATOR + "Test$Inner.class"),

					// Empty content root
					Arguments.of("", "org.test", "Empty.java",
							SEPARATOR + "org" + SEPARATOR + "test" + SEPARATOR + "Empty.java")
			);
		}

		static Stream<Arguments> createPathEdgeCases()
		{
			return Stream.of(
					// Null content root
					Arguments.of(null, "com.example", "Test.java", NullPointerException.class),

					// Null package name
					Arguments.of("src", null, "Test.java", NullPointerException.class),

					// Null file name
					Arguments.of("src", "com.example", null, NullPointerException.class)
			);
		}
	}

	@Nested
	@DisplayName("Tests for writeSourceCode method")
	class WriteSourceCodeTests
	{

		static Stream<Arguments> writeSourceCodeTestCases()
		{
			return Stream.of(
					Arguments.of("Standard case",
							new SourceCodeWriteRequest(
									"src/main/java",
									"Test.java",
									"public class Test {}",
									true
							),
							"src/main/java" + SEPARATOR + "com" + SEPARATOR + "example" + SEPARATOR + "Test.java"
					),

					Arguments.of(
							"Empty package",
							new SourceCodeWriteRequest(
									"src/main/resources",
									"config.properties",
									"key=value",
									false
							),
							"src/main/resources" + SEPARATOR + "config.properties"
					),

					Arguments.of(
							"Deep package structure",
							new SourceCodeWriteRequest(
									"src/main/java",
									"DeepClass.java",
									"package org.example.deep.structure;\npublic class DeepClass {}",
									true
							),
							"src/main/java" + SEPARATOR + "org" + SEPARATOR + "example" + SEPARATOR +
									"deep" + SEPARATOR + "structure" + SEPARATOR + "DeepClass.java"
					),

					Arguments.of(
							"Windows-style paths",
							new SourceCodeWriteRequest(
									"C:\\Project\\src",
									"WindowsPath.java",
									"package com.windows.path;\npublic class WindowsPath {}",
									false
							),
							"C:\\Project\\src" + SEPARATOR + "com" + SEPARATOR + "windows" + SEPARATOR +
									"path" + SEPARATOR + "WindowsPath.java"
					)
			);
		}

		static Stream<Arguments> nullFieldsTestCases()
		{
			return Stream.of(
					Arguments.of(
							"Null contentRootPath",
							new SourceCodeWriteRequest(
									null,
									"Test.java",
									"public class Test {}",
									true
							)
					),
					Arguments.of(
							"Null packageName",
							new SourceCodeWriteRequest(
									"src/main/java",
									"Test.java",
									"public class Test {}",
									true
							)
					),
					Arguments.of(
							"Null fileName",
							new SourceCodeWriteRequest(
									"src/main/java",
									null,
									"public class Test {}",
									true
							)
					),
					Arguments.of(
							"Null sourceCode",
							new SourceCodeWriteRequest(
									"src/main/java",
									"Test.java",
									null,
									true
							)
					)
			);
		}

		@ParameterizedTest(name = "{index}: {0}")
		@MethodSource("writeSourceCodeTestCases")
		@DisplayName("Should correctly write source code files")
		void shouldWriteSourceCodeCorrectly(String testName, SourceCodeWriteRequest request, String expectedPath)
		{
			try (MockedStatic<ClassWritingUtility> mockedUtility = mockStatic(ClassWritingUtility.class))
			{
				service.writeSourceCode(request);

				// Then
				mockedUtility.verify(
						() -> ClassWritingUtility.writeClass(
								request.fileName(),
								request.sourceCode(),
								request.contentRootPath(),
								request.overwriteExistingFile()
						),
						times(1)
				);
			}
		}

		@Test
		@DisplayName("Should throw NullPointerException when request is null")
		void shouldThrowExceptionWhenRequestIsNull()
		{
			// Given
			SourceCodeWriteRequest request = null;

			// When/Then
			assertThatThrownBy(() -> service.writeSourceCode(request))
					.as("Exception when calling writeSourceCode with null request")
					.isInstanceOf(NullPointerException.class);
		}

		@ParameterizedTest(name = "{index}: {0}")
		@MethodSource("nullFieldsTestCases")
		@DisplayName("Should throw IllegalArgumentException when request has null fields")
		void shouldThrowExceptionWhenRequestHasNullFields(String testName, SourceCodeWriteRequest request)
		{
			// Given
			// Parameters from method source

			// When/Then
			assertThatThrownBy(() -> service.writeSourceCode(request))
					.as("Exception when calling writeSourceCode with request having null fields")
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining("No package found in the given class content");
		}

		@Test
		@DisplayName("Should handle special characters in file paths")
		void shouldHandleSpecialCharactersInFilePaths()
		{
			// Given
			SourceCodeWriteRequest request = new SourceCodeWriteRequest(
					"src/main/java",
					"Special@Test.java",
					"public class Special {}",
					true
			);

			// When
			try (MockedStatic<ClassWritingUtility> mockedUtility = mockStatic(ClassWritingUtility.class))
			{
				service.writeSourceCode(request);

				// Then
				mockedUtility.verify(
						() -> ClassWritingUtility.writeClass(
								request.fileName(),
								request.sourceCode(),
								request.contentRootPath(),
								request.overwriteExistingFile()
						),
						times(1)
				);
			}
		}
	}
}