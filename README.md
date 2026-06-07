# cleanCrud-generator

`cleanCrud-generator` generates `cleanCrud` modules from an existing Java aggregate model.

It is designed for teams who:

- already model aggregates in Java
- want the surrounding CRUD module structure generated for them
- want relationships between aggregates generated from explicit declarations instead of hand-written boilerplate

This repository contains the generator itself, not a generated application.

## What The Generator Expects From Consumers

Consumers do **not** write generator package code.

Consumers provide:

1. a base model source file
2. optionally, a sibling `*Relationships` declaration class
3. generator config carrying root aggregate generation inputs such as root id types

### 1. Base model

The base model is the authoritative source for the aggregate shape.

Example:

```java
public interface PersonModel<T, V, N>
{
    String firstName();

    T tag();

    V currentVersion();

    Optional<V> lastKnownVersion();

    Collection<N> notes();
}
```

### 2. Relationships declaration

If the aggregate has relationships, consumers place a sibling class next to the base model, typically in the same
package:

```java
public final class PersonRelationships implements Relationships
{
    @Override
    public Class<?> baseModelClass()
    {
        return PersonModel.class;
    }

    @Override
    public Collection<Relationship> relationships()
    {
        return List.of(
                Relationship.referenced("tag", TagModel.class)
                            .satelliteApiIdType(Long.class)
                            .satelliteDomainIdType(Long.class)
                            .satellitePersistenceIdType(UUID.class)
                            .build(),
                Relationship.referenced("currentVersion", VersionModel.class)
                            .satelliteApiIdType(Long.class)
                            .satelliteDomainIdType(Long.class)
                            .satellitePersistenceIdType(UUID.class)
                            .build(),
                Relationship.referenced("lastKnownVersion", VersionModel.class)
                            .satelliteApiIdType(Long.class)
                            .satelliteDomainIdType(Long.class)
                            .satellitePersistenceIdType(UUID.class)
                            .build(),
                Relationship.owned("notes", NoteModel.class)
                            .satelliteApiIdType(Long.class)
                            .satelliteDomainIdType(Long.class)
                            .satellitePersistenceIdType(UUID.class)
                            .reconciliationStrategy(ReconciliationStrategy.MERGE_BY_ID)
                            .build());
    }
}
```

Important:

- this class depends only on `cleanCrud`
- it does **not** import generator packages
- it is a normal consumer source artifact

### 3. Generator config

The generator config remains responsible for root aggregate generation concerns, not satellite semantics.

That includes:

- root aggregate API id type
- root aggregate domain id type
- root aggregate persistence id type
- template/group selection
- overwrite behavior
- historization
- source paths

So the split is:

- `cleanCrud` relationship declarations describe **satellite relationships**
- generator config describes **root aggregate generation inputs**

## What The Generator Infers

The generator does **not** require cardinality to be declared separately.

It infers cardinality from the base model property shape:

- `U` -> required `ONE`
- `Optional<U>` -> optional `ONE`
- `Collection<U>` -> `MANY`

So the consumer provides:

- property name
- relationship kind
- lifecycle / reconciliation
- satellite id types

and the generator derives:

- cardinality
- API / domain / persistence relationship projections
- accessors and replacers
- runtime relationship configuration

## Relationship Meaning Comes From `cleanCrud`

The generator now consumes the `cleanCrud` domain relationship declarations directly:

- `Relationship`
- `RelationshipKind`
- `Relationships`
- `LifecycleSemantics`
- `ReconciliationStrategy`

That matters because the generator no longer invents its own parallel relationship meaning model.

Instead:

- `Relationship` is the semantic source of truth
- the generator derives generated code from it

## What The Generator Emits For Relationships

For relationship-bearing aggregates, the generator now emits:

- the normal CRUD module structure
- relationship-aware API/domain/persistence projections
- runtime relationship configuration using the high-level runtime DSL:
    - `AggregateRelationshipDefinitions.fromRelationship(...)`

So generated relationship configuration is now shorter and more semantic.

It no longer re-declares:

- owned vs referenced
- lifecycle semantics
- reconciliation strategy

Those are taken from `Relationship`.

The generator only emits the pieces that are still structural/runtime-specific:

- `current(...)` / `currentMany(...)`
- `replace(...)` / `replaceMany(...)`
- generated rebuild helpers

## Build

From the generator repository root:

```powershell
mvn clean install
```

The runnable jar is produced at:

```text
cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar
```

## Running The Generator

There are two normal ways to run it:

- with a config file
- with direct CLI options

For anything non-trivial, especially relationships, prefer the config file.

### Subcommands

The CLI exposes:

- `list-templates`
- `generate`

Examples:

```powershell
java -jar cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar list-templates
```

```powershell
java -jar cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar generate --config E:\path\to\generator-config.json
```

## Recommended Consumer Layout

The recommended consumer layout is:

- base model and relationships declaration in the same package
- generated code written back into the same repository source tree

Typical example:

```text
src/main/java/de/gupta/clean/crud/implementation/examples/person/domain/model/PersonModel.java
src/main/java/de/gupta/clean/crud/implementation/examples/person/domain/model/PersonRelationships.java
```

This keeps:

- model shape
- relationship meaning
- generated module

close together.

## Config File Example

Here is the practical shape for a relationship-bearing generation run:

```json
{
  "inputs": {
    "baseModelSourceCodeFilePath": "E:\\repo\\src\\main\\java\\de\\gupta\\clean\\crud\\implementation\\examples\\person\\domain\\model\\PersonModel.java",
    "relationshipsSourceCodeFilePath": "E:\\repo\\src\\main\\java\\de\\gupta\\clean\\crud\\implementation\\examples\\person\\domain\\model\\PersonRelationships.java"
  },
  "generation": {
    "groups": [
      "COMMON",
      "CONFIGURATION",
      "DOMAIN_MODELS",
      "DOMAIN_SUPPORT",
      "API_DTOS",
      "API_ADAPTERS",
      "API_CONTROLLERS",
      "PERSISTENCE_MODELS",
      "PERSISTENCE_ADAPTERS",
      "PERSISTENCE_REPOSITORIES",
      "PERSISTENCE_HISTORY",
      "SECURITY",
      "USE_CASE_FETCH",
      "USE_CASE_SAVE",
      "USE_CASE_UPDATE",
      "USE_CASE_DELETE"
    ]
  },
  "rootAggregateIdConfiguration": {
    "apiIdType": "Long",
    "domainIdType": "Long",
    "persistenceIdType": "UUID"
  },
  "overwrite": {
    "defaultOverwrite": true
  },
  "historized": true
}
```

### Required fields for a relationship-bearing run

At minimum you need:

- `inputs.baseModelSourceCodeFilePath`
- `inputs.relationshipsSourceCodeFilePath`
- root aggregate id configuration

If the aggregate is standalone, `relationshipsSourceCodeFilePath` may be omitted.

## Direct CLI Usage

Direct CLI mode is still fine for simpler standalone generation.

Example:

```powershell
java -jar cleanCrud-generator-master\target\cleanCrud-generator-master-<version>.jar generate `
  --base-model E:\repo\src\main\java\de\gupta\clean\crud\implementation\examples\person\domain\model\PersonModel.java `
  --relationships E:\repo\src\main\java\de\gupta\clean\crud\implementation\examples\person\domain\model\PersonRelationships.java `
  --root-api-id-type Long `
  --root-domain-id-type Long `
  --root-persistence-id-type UUID `
  --group COMMON `
  --group CONFIGURATION `
  --group DOMAIN_MODELS `
  --group DOMAIN_SUPPORT `
  --group API_DTOS `
  --group API_ADAPTERS `
  --group API_CONTROLLERS `
  --group PERSISTENCE_MODELS `
  --group PERSISTENCE_ADAPTERS `
  --group PERSISTENCE_REPOSITORIES `
  --group PERSISTENCE_HISTORY `
  --group SECURITY `
  --group USE_CASE_FETCH `
  --group USE_CASE_SAVE `
  --group USE_CASE_UPDATE `
  --group USE_CASE_DELETE `
  --overwrite-default `
  --historized
```

Recommendation:

- use direct CLI for small experiments
- use config files for real modules and especially for relationship-bearing generation

## What The Generator Produces

For a normal aggregate, the generator typically emits:

- domain model and DTO surfaces
- API DTOs and adapters
- persistence models and adapters
- repositories
- fetch/save/update/delete persistence services
- CRUD ports
- CRUD definition
- CRUD services
- module configuration

The standard configuration classes are:

- `<Aggregate>CrudPortsConfiguration`
- `<Aggregate>CrudDefinitionConfiguration`
- `<Aggregate>CrudServicesConfiguration`

If relationships are declared, it additionally emits:

- `<Aggregate>CrudRelationshipConfiguration`

That relationship config now uses:

- `AggregateRelationshipDefinitions.fromRelationship(...)`

instead of emitting the full low-level resolver/link/hydration boilerplate.

## What The Generator Derives Across Layers

Given:

- base model
- `*Relationships`
- root aggregate id config

the generator derives:

### Domain model relationship fields

- `IdentifiedModel<SatelliteDomainId, SatelliteDomainModel>`
- optional / collection variants

### Persistence model relationship fields

- `SatellitePersistenceId`
- optional / collection variants

using the **same property names as the base model**

### API create / patch / response

These are derived from:

- `OWNED` vs `REFERENCED`
- inferred cardinality
- reconciliation strategy

Examples:

- referenced one -> API id field
- owned many merge -> `SatelliteUpdatePatchItem<Id, Patch>` plus remove ids

## What The Generator Does Not Ask The Consumer To Repeat

The generator should not make consumers restate structural relationship meaning in runtime code.

So it does **not** ask consumers to manually re-specify:

- owned vs referenced
- lifecycle semantics
- reconciliation strategy

Those belong in `Relationship`.

The generator lowers them into generated runtime code automatically.

## Safe Usage Order

The safest workflow remains:

1. generate standalone satellites first
2. generate the owning aggregate after those satellites exist

For example:

1. generate `Tag`
2. generate `Version`
3. generate `Note`
4. generate `Person`

This matters because the generated relationship-aware module depends on the already-existing satellite aggregate
contracts.

## Path Resolution

Important behavior:

- relative paths inside config files are resolved relative to the config file location
- environment placeholders use `${NAME}`
- generated files are written alongside the supplied source tree

So you point the generator at real source files, not at a repository root.

## Limitations

Current limitations:

- the generator is convention-heavy
- it assumes the base model is the authoritative aggregate entry point
- it assumes sibling `*Relationships` classes follow the `cleanCrud` declaration model
- it is strongest for the architectural style embodied by `cleanCrud`
- it is not meant to be a general Java scaffolding engine

It is a structured accelerator for `cleanCrud` modules, not a substitute for domain review.

## Consumer Checklist

For a standalone aggregate:

- provide `BaseModel.java`
- provide root aggregate id types in generator config
- run generator

For a relationship-bearing aggregate:

- provide `BaseModel.java`
- provide sibling `*Relationships.java`
- provide root aggregate id types in generator config
- make sure referenced/owned satellite aggregates already exist
- run generator

## Testing The Generator

From the generator repo root:

```powershell
mvn test
```

This covers:

- unit tests
- orchestration tests
- CLI tests
- smoke generation tests
- generated fixture compile tests

The relationship-aware smoke tests now verify:

- generated runtime config uses `fromRelationship(...)`
- repeated target aggregate types still bind by property name
- generated projects compile against the current `cleanCrud`

## Summary

The current contract is:

- consumers provide **base model + optional `*Relationships`**
- generator config provides **root aggregate generation inputs**
- `cleanCrud` provides the semantic relationship model
- the generator derives the repetitive layer-specific and runtime wiring from those inputs

That is the new baseline for relationship-aware generation.