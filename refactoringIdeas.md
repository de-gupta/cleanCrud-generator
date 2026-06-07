# Generator Refactoring Ideas & Code Quality Critique

Produced from a full architectural review of the committed generator source.
The interface + abstract-class pattern used throughout is intentional and excluded from scope.

---

## Part 1 — Code Quality & Duplication

### 1.1 Validation Logic Duplicated Across Two Classes (Highest Priority)

`GenerationSpecificationConfigurationAssembler` and `RelationshipGenerationConfigurationValidator`
both validate relationship configuration. The assembler validates the raw `Relationships`
declaration as it converts it; the validator validates the already-assembled
`RelationshipGenerationConfiguration`. In practice they check overlapping concerns:

- Assembler: `validateBaseModel`, `validateRootIds`, `validateRelationshipCoverage`,
  `validatePlaceholderConsistency`
- Validator: property existence, `relationshipEligible`, cardinality consistency,
  required fields, MERGE_BY_ID + ONE_TO_MANY constraint

The MERGE_BY_ID + ONE_TO_MANY rule is a semantic constraint on the *declaration*. By the time
the validator sees it, it has already been silently defaulted to `REPLACE` in the assembler
for ONE cardinality relationships. Any rule that must fire early (before lossy defaulting)
has to live in the assembler; any rule in the validator may therefore be a dead check or
may fire on already-normalised data whose shape differs from what the author expected to guard.

**Fix:** Define a single validation phase. Either: (a) move all validation into the assembler
before any transformation, so checks run on the original declaration; or (b) keep a two-phase
model but make the boundary explicit — the assembler's four methods become a dedicated
`RelationshipDeclarationValidator` that fires first, the existing `RelationshipGenerationConfigurationValidator`
handles structural integrity of the assembled object only and checks nothing the declaration
validator already checked.

---

### 1.2 Cardinality Inference Repeated in Three Places

The logic "if cardinality is null, infer from whether the property is collection-valued" appears
in all three of these classes:

1. `GenerationSpecificationConfigurationAssembler.toConfiguration()`
2. `RelationshipGenerationConfigurationValidator` — cardinality consistency check
3. `GeneratedRelationshipFactory` — `"MANY".equals(effectiveCardinality) ? ...`

Each copy uses slightly different expressions. The factory's copy also has a third branch for
`relationship.cardinality() == null`, meaning it infers independently rather than consuming the
already-assembled value.

**Fix:** Extract a single `CardinalityResolver.resolve(Relationship, Property)` method used by
all three. The resolver is the single source of truth; callers receive a non-null `Cardinality`
enum value and do not branch on nullability themselves.

---

### 1.3 Aggregate-Name Normalisation ("Model" Suffix Stripping) in Three Places

The transformation from class name (e.g. `PersonModel`) to aggregate name (e.g. `Person`) is
performed independently in:

1. `GenerationSpecificationConfigurationAssembler.toConfiguration()` — strips `"Model"` suffix
2. `RelationshipGenerationConfiguration.normalizeAggregateName()` — same strip inside `normalized()`
3. `GeneratedRelationshipFactory` — constructs aggregate name from `domainModelType` string

Each encodes the `"Model"` suffix convention separately. Adding or changing the convention
requires three edits.

**Fix:** Extract a single `AggregateNameNormalizer.fromClassName(String)` utility. If the
convention ever changes to `"DomainModel"` or a configurable suffix, only one place changes.

---

### 1.4 String-Typed Semantic Fields in `GeneratedRelationship`

`GeneratedRelationship` stores `cardinality`, `reconciliationStrategy`, and `relationshipKind`
as plain `String` fields. Methods like `many()`, `owned()`, and `reconcileByMerge()` are
predicates that do `"MANY".equals(cardinality)` etc. at runtime. The template layer uses
`relationship.many()` throughout, so an invalid string (e.g. `"many"` lowercase) silently
produces wrong output.

This is a real module-boundary constraint: the template implementation module cannot depend on
the orchestration module (which owns the enums), because doing so would create a layering
violation. The string representation is the only way to cross that boundary without introducing
a shared-types module.

**Fix options:**

- Introduce a `code-generation-model-api` shared module (or expand the existing
  `cleanCrud-generator-api`) that declares lightweight enums (`Cardinality`, `ReconciliationStrategy`,
  `RelationshipKind`) with no orchestration dependency. Both modules can then depend on this
  shared module, and `GeneratedRelationship` gains typed fields with no coupling problem.
- Alternatively, keep strings but add a `validate()` call inside `GeneratedRelationship`'s
  constructor (or the factory) that asserts only the known string values are accepted, so
  an invalid string fails at construction time rather than silently at template render time.

---

### 1.5 Implicit Lifecycle Defaults Buried in `normalized()` Ternary Chain

`RelationshipGenerationConfiguration.normalized()` applies lifecycle defaults via a chain of
bare ternary expressions:

```java
cascadeCreate !=null?cascadeCreate :normalizedRelationshipKind ==RelationshipKind.OWNED,
cascadeUpdate ==null||cascadeUpdate,    // true unless explicitly false
cascadeDelete !=null&&cascadeDelete,    // false unless explicitly true
orphanDelete  !=null&&orphanDelete,     // false unless explicitly true
hydrateOnFetch ==null||hydrateOnFetch, // true unless explicitly false
```

The three distinct null-handling patterns (`!= null ? x : default`, `== null || x`,
`!= null && x`) encode semantically meaningful defaulting rules but are not named or documented.
A reader cannot determine the default values without decoding each expression. When the defaults
were discussed by the team or changed, the change site gives no indication of what the old
value was.

**Fix:** Replace the ternary chain with named constant defaults and explicit helper methods:

```java
private static final boolean DEFAULT_CASCADE_CREATE_OWNED = true;
private static final boolean DEFAULT_CASCADE_UPDATE = true;
// ...

private boolean effectiveCascadeCreate()
{
	return cascadeCreate != null ? cascadeCreate
			: normalizedRelationshipKind == RelationshipKind.OWNED && DEFAULT_CASCADE_CREATE_OWNED;
}
```

The defaults become self-documenting and diffable.

---

## Part 2 — Fragility & Hidden Assumptions

### 2.1 `JavaGenerationSpecificationLoader` Classpath Resolution Is Maven-Specific and Uncached

`JavaGenerationSpecificationLoader` compiles the consumer's `Relationships` Java source at
generation time using `javax.tools.JavaCompiler`. Two fragility points:

**Classpath resolution:** The method `resolveCompilationClasspath()` reads `java.class.path`,
adds protection-domain code sources, and — as a fallback — walks `~/.m2/repository/io/github/de-gupta/cleanCrud/`
to find the latest installed version. This means:

- Gradle projects have no `java.class.path` and will fail unless the protection-domain path
  happens to include the framework jar.
- The `~/.m2` fallback picks the lexicographically-last version directory, which may not be the
  version in use by the consumer project.
- In CI environments without a warm Maven cache the fallback silently produces an empty or
  incomplete classpath, giving a cryptic `ClassNotFoundException` at compile time.

**No caching:** Each invocation of `loadSpecification()` recompiles from source. For a generator
that may be invoked in a build loop or from an IDE plugin, this is unnecessary repeated I/O and
compiler spin-up. The compiled class is discarded after `newInstance()`.

**Source root heuristic:** `resolveSourceRoot()` walks up the directory tree looking for
`src/main/java` or `src/test/java`. This is a Maven/Gradle convention, not a contract. A project
with a non-standard layout (e.g. `sources/main/java`) will silently use the wrong root or fail.

**Fix:**

- Accept the framework classpath as an explicit constructor parameter (passed in by the Maven
  plugin's `MojoExecution` classpath or equivalent), removing the `~/.m2` heuristic entirely.
- Accept the source root as an explicit parameter rather than walking the directory tree.
- Cache the compiled `Class<?>` keyed on source file last-modified timestamp, or accept the
  instantiated `Relationships` object directly as an alternative entry point for callers that
  can construct it without compilation.

---

### 2.2 `GeneratedRelationship.satelliteBasePackage()` Silent Wrong Output on Convention Violation

```java
return canonical.endsWith(".domain.model."+satelliteAggregate +"Model")
    ?canonical.

substring(0,canonical.length() -(".domain.model."+satelliteAggregate +"Model").

length())
		:canonical.

substring(0,canonical.lastIndexOf('.'));
```

If the consumer places a satellite model outside the `{base}.domain.model.{Aggregate}Model`
convention, the fallback takes the last dot-segment as the package, producing a wrong but
plausible-looking result. Generated code will compile if the template happens to produce valid
syntax, but the generated import paths or package declarations will reference the wrong package.

The bug is silent: no exception, no warning, just incorrect generated output that may only
surface at consumer compile time, far from the root cause.

**Fix:** Remove the silent fallback. If the canonical class name does not match the expected
pattern, throw an `IllegalArgumentException` with a message that names the offending class and
describes the required convention. Alternatively, make `satelliteBasePackage()` accept an
explicit base package parameter that is passed in from the assembler (which already has the root
package from the generation context), removing the need for string-parsing inference entirely.

---

### 2.3 Hardcoded `IdentifiedModel` Fully-Qualified Class Name in `RelationshipConcreteTypeResolver`

`RelationshipConcreteTypeResolver` embeds the full class name of `IdentifiedModel` as a string
literal:

```java
"de.gupta.clean.crud.template.domain.model.identified.IdentifiedModel<"
		+relationship.satelliteDomainIdType() +", "
		+relationship.

domainModelType() +">"
```

If `IdentifiedModel` is ever moved, renamed, or the package structure of the framework changes,
this string silently becomes a reference to a nonexistent type. The generator will produce
syntactically valid but semantically broken Java. The error surfaces only when the consumer
compiles the generated output.

**Fix:** Derive the class name from the actual class object, which is already on the classpath
at generation time (the orchestration module requires `de.gupta.clean.crud`):

```java
IdentifiedModel .class.

getCanonicalName() +"<"+...+">"
```

This makes refactoring the framework a compile-time error in the generator rather than a
silent runtime-to-compile-time propagation delay.

---

## Part 3 — Design Clarity

### 3.1 `Property` Has Two Relationship Detection Mechanisms That Serve Different Purposes

`Property` exposes two distinct ways to determine if a property represents a relationship:

1. **Placeholder-based** (`relationshipEligible(genericTypeParameters)`,
   `relationshipGenericPlaceholder()`) — checks whether the property's type is a declared
   generic type parameter of the containing model. This is the mechanism used by the main
   generation flow.

2. **Heuristic-based** (`candidateAggregateType()`, `aggregateRelationshipCandidate()`) —
   strips known suffixes (`"Model"`, `"APIModelResponse"`) and checks against
   `NON_AGGREGATE_SIMPLE_TYPES` to infer whether the type looks like an aggregate.

The heuristic mechanism appears unused in the current main generation path but is present in
the `Property` API. Its presence suggests either a former approach that was not fully removed,
or an alternative entry point intended for future use that has not been documented as such.
Unused public API on a core model class is a maintenance liability: it will be assumed live
by future contributors, may be picked up by templates, and accumulates technical debt silently.

**Fix:** If the heuristic mechanism (`candidateAggregateType`, `aggregateRelationshipCandidate`,
`NON_AGGREGATE_SIMPLE_TYPES`) is unused in the current generation flow, remove it. If it is
needed for a planned feature, document that intent with a comment pointing to the planned use
case and suppress the "unused" warning explicitly. Do not leave two mechanisms in the same class
with no indication of which is authoritative.

---

### 3.2 `TemplateModelFactory.create()` Has Thirteen Parameters

`TemplateModelFactory` exposes a single static `create()` method with 13 positional parameters.
All 13 are required. There is no overload, no builder, and no named-parameter syntax in Java.
Callers must count positions to identify which argument maps to which concept. A future
maintainer adding a fourteenth parameter has no IDE assistance to find all call sites that need
updating if the parameter is injected mid-list.

This is distinct from the interface/abstract-class pattern concern: it is purely about the
factory method signature being opaque at call sites.

**Fix:** Replace the 13-parameter static method with a builder or a constructor-injected
intermediate value object (`TemplateModelRequest` record) that groups the parameters by concern.
The call site becomes self-documenting and adding a new parameter becomes a compile-time failure
at all call sites rather than a positional counting exercise.