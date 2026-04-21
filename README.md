# cleanCrud-generator

Generator for `cleanCrud`.

## Build

From the repository root:

```powershell
mvn clean install
```

This builds all modules and produces a runnable jar at:

```text
cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar
```

## What The Generator Needs

The generator always starts from an existing base model file.

Examples:

- `PersonModel.java`
- `VersionModel.java`
- `NoteModel.java`
- `TaskModel.java`

The path must point to the actual source file, not just the repo root. Generated files are written back into the same
repository by deriving the content root and package structure from that file.

## Standalone First, Relationships Second

The generator is strongest when used in two steps:

1. generate each standalone aggregate first
2. then generate the owning aggregate with explicit relationship configuration

For example:

1. generate `Version`
2. generate `Note`
3. generate `Task`
4. add explicit relationship config for:
    - `Task -> Version` (`ONE`)
    - `Task -> Note` (`MANY`)

The owning aggregate usually comes last because its generated relationship wiring depends on the owned aggregates'
public CRUD contracts.

## Relationship Generation Model

Relationship generation is opt-in.

The generator now does two things:

- it **infers candidate relationships** from base-model properties such as:
    - `Optional<VersionAPIModelResponse>`
    - `Collection<NoteAPIModelResponse>`
- it **requires explicit relationship configuration** before generating relationship wiring

Property shape only gives the generator:

- property name
- likely cardinality
- satellite aggregate name

It does **not** decide lifecycle semantics, reconciliation strategy, or persistence-side satellite ID storage by itself.

If a property looks like a relationship candidate but no matching relationship config is provided, the generator warns
and
leaves that property as ordinary standalone model structure.

## Relationship Configuration

Relationship generation is configured with a `relationships` section.

Each entry describes one master-side relationship.

Example JSON:

```json
{
  "relationships": [
    {
      "masterProperty": "version",
      "satelliteAggregate": "Version",
      "cardinality": "ONE",
      "reconciliationStrategy": "REPLACE",
      "satelliteApiIdType": "Long",
      "cascadeCreate": true,
      "cascadeUpdate": true,
      "cascadeDelete": false,
      "orphanDelete": false,
      "hydrateOnFetch": true,
      "generateNestedCreate": true,
      "generateNestedUpdate": true
    },
    {
      "masterProperty": "notes",
      "satelliteAggregate": "Note",
      "cardinality": "MANY",
      "reconciliationStrategy": "MERGE_BY_ID",
      "satelliteApiIdType": "Long",
      "cascadeCreate": true,
      "cascadeUpdate": true,
      "cascadeDelete": false,
      "orphanDelete": false,
      "hydrateOnFetch": true,
      "generateNestedCreate": true,
      "generateNestedUpdate": true
    }
  ]
}
```

Example properties format:

```properties
relationships.0.masterProperty=version
relationships.0.satelliteAggregate=Version
relationships.0.cardinality=ONE
relationships.0.reconciliationStrategy=REPLACE
relationships.0.satelliteApiIdType=Long
relationships.0.cascadeCreate=true
relationships.0.cascadeUpdate=true
relationships.0.cascadeDelete=false
relationships.0.orphanDelete=false
relationships.0.hydrateOnFetch=true
relationships.0.generateNestedCreate=true
relationships.0.generateNestedUpdate=true

relationships.1.masterProperty=notes
relationships.1.satelliteAggregate=Note
relationships.1.cardinality=MANY
relationships.1.reconciliationStrategy=MERGE_BY_ID
relationships.1.satelliteApiIdType=Long
relationships.1.cascadeCreate=true
relationships.1.cascadeUpdate=true
relationships.1.cascadeDelete=false
relationships.1.orphanDelete=false
relationships.1.hydrateOnFetch=true
relationships.1.generateNestedCreate=true
relationships.1.generateNestedUpdate=true
```

## Why `satelliteApiIdType` Is Required

The base model expresses the **consumer-facing relationship shape**.

Example:

- `Optional<VersionAPIModelResponse>` on `TaskModel`
- `Collection<NoteAPIModelResponse>` on `TaskModel`

But persistence must store **satellite IDs**, not hydrated response objects.

So once a relationship is declared, the generator:

- keeps the response-oriented relationship shape on the domain/API side
- generates persistence-side relationship fields using the configured `satelliteApiIdType`

If a relationship config is present but `satelliteApiIdType` is missing, generation fails fast.

## Generated Relationship Output

When relationship generation is enabled for an aggregate, the generator adds:

- `CrudRelationshipConfiguration`
- relationship lifecycle semantics with sensible defaults
- create input resolvers
- patch input resolvers
- identity resolvers
- link strategies
- hydration strategies
- relationship definition beans
- relationship attachment in the aggregate CRUD definition

It also generates master-side nested patch item wrappers while keeping the standalone satellite update patch DTOs
id-less.

## Default Generated Semantics

Generated relationships start from these defaults:

- `cascadeCreate = true`
- `cascadeUpdate = true`
- `cascadeDelete = false`
- `orphanDelete = false`
- `hydrateOnFetch = true`

The generated relationship configuration includes `TODO` comments so consumers can review and change those semantics if
needed.

## Current Standalone Output Shape

For a normal standalone aggregate, the generator produces:

- shared `CommonPersistenceConfiguration` with both `PersistenceTransactionRunner` and `AggregateLifecycleEngine`
- aggregate ports configuration
- aggregate definition configuration
- aggregate services configuration using `AggregateCrudServices`

## Config File Formats

Supported today:

- `.json`
- `.yaml`
- `.yml`
- `.properties`

`.properties` is supported. YAML remains supported for compatibility, but JSON or properties are the preferred formats.

## Configuration Model

The generator now uses a modular additive config model with these top-level sections:

- `inputs`
- `genericTypes`
- `generation`
- `ownership`
- `overwrite`
- `historized`
- `relationships`

### `inputs`

Controls which source models already exist.

### `genericTypes`

Lets you supply different concrete generic substitutions for domain, persistence, and API layers.

### `generation`

Lets you include or exclude generation by:

- group
- template
- tag

Available groups:

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

Generated standalone CRUD modules use aggregate-based wiring under `useCases.crud.configuration`.
That means the generator emits:

- `<Model>CrudPortsConfiguration`
- `<Model>CrudDefinitionConfiguration`
- `<Model>CrudServicesConfiguration`

When relationships are configured, the generator also emits:

- `<Model>CrudRelationshipConfiguration`

### `ownership`

Lets you declare whether the generator or the user owns each top-level surface.

### `overwrite`

Overwrite can now be controlled at different levels.

### `historized`

Boolean flag controlling historization-aware generation where supported.

## Direct CLI Options

You can use `--config`, or supply options directly on the command line.

The direct CLI mode currently covers standalone generation and general selection/overwrite controls. Relationship
configuration is best supplied via JSON/properties config files.

## Notes

- Relative paths inside config files are resolved relative to the config file itself.
- Environment variables in config values use the `${NAME}` syntax.
- The generator does not require any special package root. It reads the package from the source file and generates
  alongside it.
