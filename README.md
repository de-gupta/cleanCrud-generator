# cleanCrud-generator

Generator for `cleanCrud`.

## Build

From the repository root:

```powershell
mvn clean install
```

This builds all modules and produces a runnable jar at:

```text
cleanCrud-generator-master\target\cleanCrud-generator-master-0.2.3-SNAPSHOT.jar
```

## What The Generator Needs

The generator always starts from an existing base model file.

Examples:

- `PersonModel.java`
- `TaskModel.java`

The path must point to the actual source file, not just the repo root. Generated files are written back into the same
target repository by deriving the content root and package structure from that file.

## Quick Start

Set the full absolute path to the base model file:

```powershell
$env:CLEANCRUD_BASE_MODEL_FILE="C:\Path\To\PersonModel.java"
```

Then run:

```powershell
java -jar .\cleanCrud-generator-master\target\cleanCrud-generator-master-0.2.3-SNAPSHOT.jar generate --config .\.run\generate.from.base-model.json
```

## Config File Formats

Supported today:

- `.json`
- `.yaml`
- `.yml`
- `.properties`

`.properties` is now supported. YAML is still supported for compatibility, but JSON or properties are the preferred
formats going forward.

## Configuration Model

The generator now uses a modular additive config model with these top-level sections:

- `inputs`
- `genericTypes`
- `generation`
- `ownership`
- `overwrite`
- `historized`

### `inputs`

Controls which source models already exist.

Example:

```json
{
  "inputs": {
    "baseModelSourceCodeFilePath": ".../PersonModel.java",
    "domainModelSourceCodeFilePath": null,
    "persistenceModelSourceCodeFilePath": null,
    "apiModelSourceCodeFilePath": null
  }
}
```

Meaning:

- `baseModelSourceCodeFilePath` is required
- the others are optional
- if a domain/api/persistence model path is provided, ownership defaults to `USER` unless explicitly overridden

### `genericTypes`

Lets you supply different concrete generic substitutions for domain, persistence, and API layers.

Example:

```json
{
  "genericTypes": {
    "domain": {
      "U": "String",
      "V": "Long"
    },
    "persistence": {
      "U": "UUID",
      "V": "UUID"
    },
    "api": {
      "U": "String",
      "V": "Long"
    }
  }
}
```

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

Example:

```json
{
  "generation": {
    "groups": [
      "DOMAIN_MODELS",
      "API_DTOS",
      "PERSISTENCE_REPOSITORIES"
    ],
    "excludeTemplates": [
      "ModuleConfiguration"
    ]
  }
}
```

### `ownership`

Lets you declare whether the generator or the user owns each top-level surface.

Possible values:

- `GENERATED`
- `USER`

Example:

```json
{
  "ownership": {
    "baseModel": "USER",
    "domainModel": "GENERATED",
    "persistenceModel": "USER",
    "apiModel": "GENERATED"
  }
}
```

If a surface is `USER`, the generator excludes the corresponding generated templates and only generates the surrounding
scaffolding.

### `overwrite`

Overwrite can now be controlled at different levels.

Supported scopes:

- default
- group
- template
- tag
- file

Precedence is:

1. file
2. template
3. tag
4. group
5. default

Example:

```json
{
  "overwrite": {
    "defaultOverwrite": false,
    "groups": {
      "API_CONTROLLERS": true
    },
    "templates": {
      "ModuleConfiguration": false
    },
    "tags": {
      "controller": true
    },
    "files": {
      "src/main/java/.../PersonModuleConfiguration.java": false
    }
  }
}
```

### `historized`

Boolean flag controlling historization-aware generation where supported.

## Example JSON Config

```json
{
  "inputs": {
    "baseModelSourceCodeFilePath": "${CLEANCRUD_BASE_MODEL_FILE}"
  },
  "genericTypes": {
    "domain": {
      "U": "String",
      "V": "Integer"
    },
    "persistence": {
      "U": "String",
      "V": "Integer"
    },
    "api": {
      "U": "String",
      "V": "Integer"
    }
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
  "ownership": {},
  "overwrite": {
    "defaultOverwrite": true
  },
  "historized": true
}
```

## Example Properties Config

```properties
inputs.baseModelSourceCodeFilePath=${CLEANCRUD_BASE_MODEL_FILE}

genericTypes.domain.U=String
genericTypes.domain.V=Integer
genericTypes.persistence.U=String
genericTypes.persistence.V=Integer
genericTypes.api.U=String
genericTypes.api.V=Integer

generation.groups=COMMON,CONFIGURATION,DOMAIN_MODELS,DOMAIN_SUPPORT,API_DTOS,API_ADAPTERS,API_CONTROLLERS,PERSISTENCE_MODELS,PERSISTENCE_ADAPTERS,PERSISTENCE_REPOSITORIES,PERSISTENCE_HISTORY,SECURITY,USE_CASE_FETCH,USE_CASE_SAVE,USE_CASE_UPDATE,USE_CASE_DELETE

overwrite.defaultOverwrite=true

historized=true
```

## Direct CLI Options

You can use `--config`, or supply options directly on the command line.

Examples:

```powershell
java -jar .\cleanCrud-generator-master\target\cleanCrud-generator-master-0.2.3-SNAPSHOT.jar generate `
  --base-model "C:\Path\To\PersonModel.java" `
  --domain-type U=String --domain-type V=Integer `
  --persistence-type U=UUID --persistence-type V=UUID `
  --api-type U=String --api-type V=Integer `
  --group DOMAIN_MODELS --group API_DTOS --group USE_CASE_SAVE `
  --own-persistence-model USER `
  --overwrite-template ModuleConfiguration=false `
  --historized
```

Supported direct options include:

- `--base-model`
- `--domain-model`
- `--persistence-model`
- `--api-model`
- `--domain-type`
- `--persistence-type`
- `--api-type`
- `--group`
- `--template`
- `--tag`
- `--exclude-group`
- `--exclude-template`
- `--exclude-tag`
- `--own-base-model`
- `--own-domain-model`
- `--own-persistence-model`
- `--own-api-model`
- `--overwrite-default`
- `--overwrite-group`
- `--overwrite-template`
- `--overwrite-tag`
- `--overwrite-file`
- `--historized`

## Checked-In Run Config

The checked-in run config uses:

- [.run/generate.from.base-model.json](E:/Projects/Professional/OpenSource/java/de-gupta/crud/cleanCrud-generator/cleanCrud-generator/.run/generate.from.base-model.json)

and expects:

- `CLEANCRUD_BASE_MODEL_FILE`

to be set to the full base model path.

## Notes

- Relative paths inside config files are resolved relative to the config file itself.
- Environment variables in config values use the `${NAME}` syntax.
- The generator does not require any special package root. It reads the package from the source file and generates
  alongside it.