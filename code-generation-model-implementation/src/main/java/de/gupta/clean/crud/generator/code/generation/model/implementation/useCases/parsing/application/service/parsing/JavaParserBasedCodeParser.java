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
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import de.gupta.aletheia.functional.Unfolding;
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
import java.util.SequencedCollection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class JavaParserBasedCodeParser implements CodeParser
{
	@Override
	public String typeName(final String sourceCodeFilePath)
	{
		return Unfolding.of(extractType(sourceCodeFilePath))
						.metamorphose(NodeWithSimpleName::getNameAsString)
						.summon();
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
		return extractType(sourceCodeFilePath)
				.getMembers()
				.stream()
				.filter(BodyDeclaration::isMethodDeclaration)
				.map(BodyDeclaration::asMethodDeclaration)
				.filter(this::isPropertyAccessor)
				.map(methodDeclaration -> extractProperty(methodDeclaration, sourceCodeFilePath, sourcePackageName,
						compilationUnit))
				.collect(Collectors.toSet());
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
			final CompilationUnit compilationUnit)
	{
		final String methodName = methodDeclaration.getNameAsString();
		final Type propertyType = methodDeclaration.getType();

		return Unfolding.of(propertyType)
						.interlace(Type::resolve)
						.metamorphose(p -> Property.of(
								methodName,
								p.first().toString(),
								p.second().describe(),
								isEnumType(p.second()) || isEnumType(p.first().toString(), sourceCodeFilePath,
										sourcePackageName, compilationUnit)))
						.summon();
	}

	private boolean isEnumType(final ResolvedType resolvedType)
	{
		if (!resolvedType.isReferenceType())
		{
			return false;
		}
		var referenceType = resolvedType.asReferenceType();
		if (referenceType.getTypeDeclaration().map(typeDeclaration -> typeDeclaration.isEnum()).orElse(false))
		{
			return true;
		}
		return referenceType.getQualifiedName().equals("java.util.Optional")
				&& !referenceType.typeParametersValues().isEmpty()
				&& isEnumType(referenceType.typeParametersValues().getFirst());
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
}