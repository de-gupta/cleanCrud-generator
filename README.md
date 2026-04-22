# cleanCrud-generator

Generator for [`cleanCrud`](https://github.com/de-gupta/cleanCrud) modules from existing aggregate model source files.

This repository contains the generator itself, not a generated sample application. It is intended for teams that already
model aggregates in Java and want the surrounding clean architecture CRUD module structure generated around those
models.

## Scope

The generator starts from an existing base model source file such as:

- `PersonModel.java`
- `TaskModel.java`
- `VersionModel.java`
- `NoteModel.java`

From that source model, it generates a layered CRUD module structure around the aggregate, including domain models, API
DTOs and adapters, persistence models and repositories, security/configuration surfaces, and use-case wiring.

The generator is designed for:

- aggregate-centric module generation
- convention-based code generation around an existing Java source model
- incremental generation into an existing repository
- explicit relationship generation when the aggregate refers to other aggregates

The generator is not designed as:

- a generic Java scaffolding engine
- a runtime code generator
- a schema-first generator
- a one-shot full application generator

## Repository Layout

The repository is split into modules with distinct responsibilities:

- `code-generation-model-implementation`
    - parses and interprets Java model source
- `code-generation-template-implementation`
    - builds template-facing semantic models and processes Freemarker templates
- `code-generation-orchestration`
    - coordinates model parsing, relationship handling, selection, and writing
- `code-generation-writing-implementation`
    - writes generated source files
- `cleanCrud-generator-api`
    - CLI adapter and configuration loading
- `cleanCrud-generator-master`
    - runnable application assembly and end-to-end integration tests

## Build

From the repository root:

```powershell
mvn clean install
```

This builds all modules and produces a runnable jar at:

```text
cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar
```

## Running The Generator

The runnable entry point is the packaged jar in `cleanCrud-generator-master`.

Examples:

```powershell
java -jar cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar list-templates
```

```powershell
java -jar cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar generate --config E:\path\to\generator-config.json
```

```powershell
java -jar cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar generate E:\path\to\PersonModel.java --historized --group COMMON --group CONFIGURATION
```

The CLI exposes two subcommands:

- `list-templates`
    - prints the currently available template names in sorted order
- `generate`
    - generates code from a config file or direct CLI options

## What Consumers Must Provide

The generator always requires a base model source file.

Consumers must provide:

- a real Java source file for the aggregate base model
- a valid Java package declaration in that file
- a repository structure where generated code can be written alongside the model source tree
- any concrete type substitutions required by the model's generic parameters
- explicit relationship configuration when aggregate properties represent cross-aggregate relationships

Consumers may also provide:

- existing domain model source paths
- existing persistence model source paths
- existing API model source paths
- selection rules for groups, templates, and tags
- ownership rules
- overwrite rules
- historization flag

## What The Generator Assumes

The generator makes several important assumptions about the consumer repository.

It assumes:

- the base model file is the authoritative entry point for the aggregate
- the package structure in the model file reflects the desired generated package neighborhood
- generated code should be written back into the same repository tree
- related aggregate contracts already exist when relationship-bearing generation depends on them
- model property naming and DTO naming follow the conventions expected by the generator templates

It does not assume a fixed monorepo shape or a fixed repository root. It derives the content root and package-relative
placement from the supplied source file path and package declaration.

## Generated Output

For a normal standalone aggregate, the generator typically produces:

- aggregate CRUD configuration
- aggregate port wiring
- aggregate services wiring
- domain model and DTO surfaces
- API DTOs and adapters
- persistence models and repositories
- persistence history support when historization is enabled
- security/policy scaffolding where applicable
- shared common persistence support such as `CommonPersistenceConfiguration`

Current standalone CRUD configuration generation emits:

- `<Aggregate>CrudPortsConfiguration`
- `<Aggregate>CrudDefinitionConfiguration`
- `<Aggregate>CrudServicesConfiguration`

When relationships are configured, the generator also emits:

- `<Aggregate>CrudRelationshipConfiguration`

## Standalone First, Relationships Second

The safest usage pattern is:

1. generate standalone aggregates first
2. generate the owning aggregate with explicit relationship configuration after the referenced or owned aggregates
   already exist

Example order:

1. generate `Version`
2. generate `Note`
3. generate `Person` or `Task`
4. add explicit relationship config for the owning aggregate

This matters because the generated relationship wiring depends on the public contracts of the satellite aggregates.

## Relationship Generation

Relationship generation is opt-in.

The generator can infer that a property looks like a relationship candidate from shapes such as:

- `Optional<VersionAPIModelResponse>`
- `Collection<NoteAPIModelResponse>`

That inference is intentionally limited. It only gives the generator:

- master property name
- likely cardinality
- likely satellite aggregate name

It does not determine:

- relationship kind
- lifecycle semantics
- reconciliation behavior
- satellite ID storage type
- nested create/update generation policy

Those must be supplied explicitly when relationship generation is desired.

## Relationship Configuration Model

Relationship entries are provided in the `relationships` section of the config file.

Example:

```json
{
  "relationships": [
    {
      "masterProperty": "notes",
      "satelliteAggregate": "Note",
      "cardinality": "MANY",
      "reconciliationStrategy": "MERGE_BY_ID",
      "satelliteApiIdType": "Long",
      "relationshipKind": "OWNED"
    },
    {
      "masterProperty": "currentVersion",
      "satelliteAggregate": "Version",
      "cardinality": "ONE",
      "reconciliationStrategy": "REPLACE",
      "satelliteApiIdType": "Long",
      "relationshipKind": "REFERENCED"
    }
  ]
}
```

Important fields:

- `masterProperty`
    - the property on the owning aggregate
- `satelliteAggregate`
    - the related aggregate name
- `cardinality`
    - `ONE` or `MANY`
- `relationshipKind`
    - whether the relationship is `OWNED` or `REFERENCED`
- `reconciliationStrategy`
    - how nested changes are reconciled
- `satelliteApiIdType`
    - the ID type used when persistence stores satellite references by ID

### Why `relationshipKind` Is Required

The base model can imply that a property is relationship-shaped, but it cannot safely decide whether the master
aggregate owns the lifecycle of the satellite aggregate or merely references it.

That decision affects:

- nested create/update semantics
- persistence behavior
- generated patch structure
- hydration and attachment logic

So relationship generation fails fast when that information is missing.

### Why `satelliteApiIdType` Is Required

Consumer-facing models typically expose relationship state as response objects, while persistence usually stores
satellite IDs.

Example:

- base model: `Optional<VersionAPIModelResponse>`
- persistence concern: store `Long` satellite ID

The generator therefore needs an explicit persistence-side ID type once the relationship is declared.

## Default Relationship Semantics

Generated relationship definitions start from these defaults:

- `cascadeCreate = true`
- `cascadeUpdate = true`
- `cascadeDelete = false`
- `orphanDelete = false`
- `hydrateOnFetch = true`

These are starting points, not a substitute for consumer review. Generated relationship configuration intentionally
includes review-friendly surfaces so teams can adapt semantics for their domain.

## Configuration File Formats

Supported config formats:

- `.json`
- `.yaml`
- `.yml`
- `.properties`

JSON and properties are the preferred formats for maintainability and explicitness.

## Configuration Model

The top-level configuration model is additive and organized into these sections:

- `inputs`
- `genericTypes`
- `generation`
- `ownership`
- `overwrite`
- `historized`
- `relationships`

### `inputs`

Defines source file paths that already exist.

Relevant inputs include:

- `baseModelSourceCodeFilePath`
- existing domain model path
- existing persistence model path
- existing API model path

### `genericTypes`

Allows different concrete generic substitutions per layer:

- `domain`
- `persistence`
- `api`

This matters when a base model is generic but the generated layers need different concrete types.

### `generation`

Controls inclusion and exclusion by:

- group
- template
- tag

Supported groups include:

- `COMMON`
- `CONFIGURATION`
- `DOMAIN_MODELS`
- `DOMAIN_SUPPORT`
- `API_DTOS`
- `API_ADAPTERS`
- `API_CONTROLLERS`
- `PERSISTENCE_MODELS`
- `PERSISTENCE_ADAPTERS`
- `PERSISTENCE_REPOSITORIES`
- `PERSISTENCE_HISTORY`
- `SECURITY`
- `USE_CASE_FETCH`
- `USE_CASE_SAVE`
- `USE_CASE_UPDATE`
- `USE_CASE_DELETE`

### `ownership`

Controls whether generated artifacts are treated as generator-owned or user-owned.

Ownership can be declared at different scopes:

- base model
- domain model
- persistence model
- API model
- group rules
- template rules
- tag rules

### `overwrite`

Controls overwrite behavior at multiple levels:

- default overwrite behavior
- group rules
- template rules
- tag rules
- file-path rules

### `historized`

Boolean flag enabling historization-aware generation where supported by the templates.

### `relationships`

Explicit relationship-generation declarations for the owning aggregate.

## Direct CLI Options

The generator can be run with a config file or with direct CLI options.

Direct CLI mode supports:

- base model path selection
- existing source file path declarations for domain/persistence/API models
- generic substitutions by layer
- include and exclude rules for groups, templates, and tags
- ownership rules
- overwrite rules
- historization flag

Examples:

```powershell
java -jar cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar generate `
  --base-model E:\repo\src\main\java\de\gupta\clean\crud\implementation\examples\person\domain\model\PersonModel.java `
  --domain-type U=String `
  --domain-type V=Integer `
  --persistence-type U=Integer `
  --persistence-type V=String `
  --api-type U=String `
  --api-type V=Integer `
  --group COMMON `
  --group CONFIGURATION `
  --group DOMAIN_MODELS `
  --overwrite-default `
  --historized
```

Current recommendation:

- use direct CLI options for simpler standalone generation
- use config files for relationship-bearing generation and anything non-trivial

`--config` cannot be combined with direct generation options.

## Path Resolution, Environment Variables, and Layout Rules

Important behavior:

- relative paths inside config files are resolved relative to the config file location
- environment variable placeholders use `${NAME}` syntax
- generated files are written alongside the supplied source tree, based on the base model path and package declaration

This means consumers should think in terms of "point the generator at the actual aggregate model source file," not "
point the generator at the repository root."

## Limitations

Current limitations to keep in mind:

- the generator is convention-heavy and depends on expected naming and package shapes
- relationship generation requires explicit semantics and is not fully inferred from property shape
- the safest flow still assumes standalone aggregates are generated before owning aggregates with relationships
- generation quality depends on the base model being a clean, representative aggregate surface
- the generator is strongest for the architectural style and template conventions embodied by `cleanCrud`
- it is not intended to support arbitrary project structures or arbitrary domain modeling styles

The repository has been refactored to reduce architectural hotspots, but it is still an evolving generator with strong
conventions rather than a completely open-ended platform.

## Consumer Responsibilities

Consumers are expected to:

- provide a valid base model file
- review generated code before treating it as final domain behavior
- review relationship lifecycle defaults
- supply explicit generic substitutions where needed
- choose ownership and overwrite policy intentionally
- regenerate with care when changing naming or relationship conventions

The generator helps create structure quickly, but it does not replace domain design decisions.

## Development And Testing Of The Generator

The generator repository now has two confidence levels.

### Fast Confidence

Run:

```powershell
mvn test
```

This runs:

- unit tests
- architectural boundary tests
- smoke tests
- CLI integration tests
- the generated-app compile fixture

The generated-app compile fixture assembles an ephemeral sample application from in-repo test resources, copies stable
`note` and `version` modules, generates `person`, and verifies that the resulting mixed application compiles.

### Full Confidence

Run:

```powershell
mvn verify
```

This runs everything from `test`, plus the generated-app boot integration test through Maven Failsafe.

That full path:

1. assembles an ephemeral sample application from `cleanCrud-generator-master/src/test/resources/generated-app-fixutre`
2. copies stable `note` and `version` modules
3. generates the `person` module from the copied `PersonModel`
4. compiles the assembled application
5. boots the Spring application context with Testcontainers-backed PostgreSQL

This is the strongest in-repo confidence signal for day-to-day extension work.

## Test Fixture Scope

The generated-app fixture intentionally tests a mixed scenario:

- static copied modules: `note`, `version`
- generated module during the test run: `person`

That is deliberate. It verifies that newly generated output can coexist with already-existing neighboring modules in a
realistic application shape, which catches more regressions than isolated single-aggregate smoke generation.

## Requirements For Full Integration Tests

The full `verify` path assumes:

- Docker is available
- Testcontainers can connect to Docker
- the local environment can pull and run required test container images if not already cached

If Docker is unavailable, `mvn test` remains the fast compile-level confidence path, but `mvn verify` is the intended
full validation command.

## Maintainer Guidance

When extending the generator:

- prefer adding behavior behind clear semantic boundaries
- avoid turning central registry/model/orchestration files back into extension magnets
- add or update fixture coverage when introducing new generation behavior
- prefer preserving output compatibility unless a deliberate breaking change is intended
- validate relationship-bearing scenarios, not just standalone generation

For most changes:

1. run `mvn test`
2. if generation logic, templates, or wiring changed, run `mvn verify`

## Current State

The repository is architecturally healthier than before, but still convention-driven.

Strengths:

- clearer boundaries between orchestration, CLI assembly, and template-domain composition
- architectural guardrails
- semantic `TemplateModel` sub-models
- stronger in-repo end-to-end confidence via compile and boot fixtures

Remaining long-term stewardship areas:

- convention-heavy relationship modeling
- centralized template metadata
- JPMS/module-boundary clarity

## Summary

Use this generator when you already have an aggregate model and want `cleanCrud`-style module scaffolding generated
around it.

Use:

- `mvn test` for fast repository confidence
- `mvn verify` for full repository confidence

Use config files for non-trivial generation, especially relationships.

Treat generated code as a strong starting point and structured acceleration mechanism, not as a substitute for domain
review.