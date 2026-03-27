package de.gupta.clean.crud.generator.code.generation.model.implementation.useCases.parsing.application.service.parsing;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.exceptions.PackageNotFoundException;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.exceptions.ParsingFailedException;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.exceptions.TypeNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.SequencedCollection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class JavaParserBasedCodeParser implements CodeParser
{
	private static final Set<String> SIMPLE_JAVA_TYPES = Set.of(
			"String",
			"Boolean",
			"Byte",
			"Short",
			"Integer",
			"Long",
			"Float",
			"Double",
			"Character",
			"Object",
			"Void"
	);
	private static final Map<String, String> WELL_KNOWN_TYPES = Map.ofEntries(
			Map.entry("Optional", "java.util.Optional"),
			Map.entry("UUID", "java.util.UUID"),
			Map.entry("LocalDate", "java.time.LocalDate"),
			Map.entry("LocalDateTime", "java.time.LocalDateTime"),
			Map.entry("LocalTime", "java.time.LocalTime"),
			Map.entry("Instant", "java.time.Instant"),
			Map.entry("OffsetDateTime", "java.time.OffsetDateTime"),
			Map.entry("ZonedDateTime", "java.time.ZonedDateTime"),
			Map.entry("BigDecimal", "java.math.BigDecimal"),
			Map.entry("BigInteger", "java.math.BigInteger")
	);

	@Override
	public String typeName(final String sourceCodeFilePath)
	{
		return extractType(sourceCodeFilePath).getNameAsString();
	}

	@Override
	public String packageName(final String sourceCodeFilePath)
	{
		return parseSourceCodeFile(sourceCodeFilePath, new JavaParser())
				.getPackageDeclaration()
				.map(PackageDeclaration::getName)
				.map(Name::asString)
				.orElseThrow(() -> PackageNotFoundException.withMessage(
						"Package not found in file " + sourceCodeFilePath));
	}

	@Override
	public Path contentRootPath(final String sourceCodeFilePath)
	{
		return extractContentRoot(sourceCodeFilePath, packageName(sourceCodeFilePath));
	}

	@Override
	public SequencedCollection<String> genericTypesParameters(final String sourceCodeFilePath)
	{
		return extractType(sourceCodeFilePath)
				.asClassOrInterfaceDeclaration()
				.getTypeParameters()
				.stream()
				.map(TypeParameter::asString)
				.toList();
	}

	@Override
	public Set<Property> properties(final String sourceCodeFilePath)
	{
		final var sourcePackageName = packageName(sourceCodeFilePath);
		final var compilationUnit = parseSourceCodeFile(sourceCodeFilePath, createConfiguredParser(sourceCodeFilePath));
		final var sourceType = extractType(sourceCodeFilePath);
		final var genericTypeParameters = sourceType.asClassOrInterfaceDeclaration()
		                                            .getTypeParameters()
		                                            .stream()
		                                            .map(TypeParameter::getNameAsString)
		                                            .collect(Collectors.toSet());
		return sourceType.getMembers()
		                 .stream()
		                 .filter(BodyDeclaration::isMethodDeclaration)
		                 .map(BodyDeclaration::asMethodDeclaration)
		                 .filter(this::isPropertyAccessor)
		                 .map(methodDeclaration -> extractProperty(methodDeclaration, sourceCodeFilePath,
				                 sourcePackageName,
								 compilationUnit, genericTypeParameters))
		                 .collect(Collectors.toCollection(LinkedHashSet::new));
	}

	private boolean isPropertyAccessor(final MethodDeclaration methodDeclaration)
	{
		return methodDeclaration.getParameters().isEmpty()
				&& methodDeclaration.getBody().isEmpty()
				&& !methodDeclaration.isStatic();
	}

	private TypeDeclaration<?> extractType(final String sourceCodeFilePath)
	{
		return parseSourceCodeFile(sourceCodeFilePath, createConfiguredParser(sourceCodeFilePath))
				.getTypes()
				.stream()
				.filter(TypeDeclaration::isTopLevelType)
				.findFirst()
				.orElseThrow(() -> TypeNotFoundException.withMessage(
						"No top level type found in file " + sourceCodeFilePath));
	}

	private CompilationUnit parseSourceCodeFile(final String sourceCodeFilePath, final JavaParser parser)
	{
		try
		{
			return parser.parse(new File(sourceCodeFilePath))
			             .getResult()
			             .orElseThrow(() -> ParsingFailedException.withMessage(
								 "Could not parse file " + sourceCodeFilePath));
		}
		catch (FileNotFoundException e)
		{
			throw ParsingFailedException.withMessage("File not found " + sourceCodeFilePath);
		}
	}

	private JavaParser createConfiguredParser(final String sourceCodeFilePath)
	{
		ParserConfiguration configuration = new ParserConfiguration()
				.setSymbolResolver(computeSymbolResolver(sourceCodeFilePath, packageName(sourceCodeFilePath)));
		return new JavaParser(configuration);
	}

	private SymbolResolver computeSymbolResolver(final String sourceCodeFilePath, final String packageName)
	{
		CombinedTypeSolver typeSolver = new CombinedTypeSolver();
		typeSolver.add(new ReflectionTypeSolver());
		typeSolver.add(new JavaParserTypeSolver(extractContentRoot(sourceCodeFilePath, packageName)));

		return new JavaSymbolSolver(typeSolver);
	}

	private Path extractContentRoot(final String sourceCodeFilePath, final String packageName)
	{
		return ascend(Paths.get(sourceCodeFilePath).toAbsolutePath().getParent(), packageName.split("\\.").length);
	}

	private Path ascend(final Path path, final int levels)
	{
		return levels == 0 ? path : ascend(path.getParent(), levels - 1);
	}

	private Property extractProperty(
			final MethodDeclaration methodDeclaration,
			final String sourceCodeFilePath,
			final String sourcePackageName,
			final CompilationUnit compilationUnit,
			final Set<String> genericTypeParameters)
	{
		final String methodName = methodDeclaration.getNameAsString();
		final Type propertyType = methodDeclaration.getType();
		return Property.of(
				methodName,
				propertyType.toString(),
				qualifyTypeName(propertyType, sourcePackageName, compilationUnit, genericTypeParameters),
				isEnumType(propertyType.toString(), sourceCodeFilePath, sourcePackageName, compilationUnit));
	}

	private boolean isEnumType(
			final String declaredType,
			final String sourceCodeFilePath,
			final String sourcePackageName,
			final CompilationUnit compilationUnit)
	{
		final String baseTypeName = baseTypeName(declaredType);
		if (baseTypeName.isBlank() || baseTypeName.contains(".") || Character.isLowerCase(baseTypeName.charAt(0)))
		{
			return false;
		}
		final Path candidate = contentRootPath(sourceCodeFilePath)
				.resolve(resolveQualifiedTypeName(baseTypeName, sourcePackageName, compilationUnit).replace('.',
						File.separatorChar) + ".java");
		if (!Files.exists(candidate))
		{
			return false;
		}
		return parseSourceCodeFile(candidate.toString(), new JavaParser())
				.getTypes()
				.stream()
				.filter(TypeDeclaration::isTopLevelType)
				.filter(typeDeclaration -> typeDeclaration.getNameAsString().equals(baseTypeName))
				.anyMatch(TypeDeclaration::isEnumDeclaration);
	}

	private String resolveQualifiedTypeName(
			final String baseTypeName,
			final String sourcePackageName,
			final CompilationUnit compilationUnit)
	{
		return compilationUnit.getImports()
		                      .stream()
		                      .map(ImportDeclaration::getNameAsString)
		                      .filter(importName -> importName.endsWith("." + baseTypeName))
		                      .findFirst()
		                      .orElse(sourcePackageName + "." + baseTypeName);
	}

	private String baseTypeName(final String declaredType)
	{
		final String trimmed = declaredType.trim();
		final int genericStart = trimmed.indexOf('<');
		final int genericEnd = trimmed.lastIndexOf('>');
		if (genericStart >= 0 && genericEnd > genericStart)
		{
			return baseTypeName(trimmed.substring(genericStart + 1, genericEnd));
		}
		return trimmed;
	}

	private String qualifyTypeName(
			final Type type,
			final String sourcePackageName,
			final CompilationUnit compilationUnit,
			final Set<String> genericTypeParameters)
	{
		if (type.isPrimitiveType())
		{
			return type.asPrimitiveType().asString();
		}
		if (type.isVoidType())
		{
			return "void";
		}
		if (type.isArrayType())
		{
			final ArrayType arrayType = type.asArrayType();
			return qualifyTypeName(arrayType.getComponentType(), sourcePackageName, compilationUnit,
					genericTypeParameters) + "[]";
		}
		if (type.isWildcardType())
		{
			final var wildcardType = type.asWildcardType();
			if (wildcardType.getExtendedType().isPresent())
			{
				return "? extends " + qualifyTypeName(wildcardType.getExtendedType().orElseThrow(), sourcePackageName,
						compilationUnit, genericTypeParameters);
			}
			if (wildcardType.getSuperType().isPresent())
			{
				return "? super " + qualifyTypeName(wildcardType.getSuperType().orElseThrow(), sourcePackageName,
						compilationUnit, genericTypeParameters);
			}
			return "?";
		}
		if (type.isTypeParameter())
		{
			return type.asTypeParameter().getNameAsString();
		}
		if (type.isClassOrInterfaceType())
		{
			final ClassOrInterfaceType classOrInterfaceType = type.asClassOrInterfaceType();
			final String qualifiedBaseType = qualifySimpleType(
					classOrInterfaceType.getNameAsString(),
					sourcePackageName,
					compilationUnit,
					genericTypeParameters);
			if (classOrInterfaceType.getTypeArguments().isEmpty())
			{
				return qualifiedBaseType;
			}
			return qualifiedBaseType + "<" + classOrInterfaceType.getTypeArguments().orElseThrow()
			                                                     .stream()
			                                                     .map(argument -> qualifyTypeName(argument,
																		 sourcePackageName,
																		 compilationUnit,
																		 genericTypeParameters))
			                                                     .collect(Collectors.joining(", ")) + ">";
		}
		return type.toString();
	}

	private String qualifySimpleType(
			final String simpleTypeName,
			final String sourcePackageName,
			final CompilationUnit compilationUnit,
			final Set<String> genericTypeParameters)
	{
		if (simpleTypeName.contains(".") || SIMPLE_JAVA_TYPES.contains(simpleTypeName) ||
				genericTypeParameters.contains(simpleTypeName))
		{
			return simpleTypeName;
		}
		if (WELL_KNOWN_TYPES.containsKey(simpleTypeName))
		{
			return WELL_KNOWN_TYPES.get(simpleTypeName);
		}
		return compilationUnit.getImports()
		                      .stream()
		                      .map(ImportDeclaration::getNameAsString)
		                      .filter(importName -> importName.endsWith("." + simpleTypeName))
		                      .findFirst()
		                      .orElse(sourcePackageName + "." + simpleTypeName);
	}
}