# cleanCrud-generator

This repository contains the code generator for `cleanCrud`.

## Build

From the repository root:

```powershell
mvn clean install
```

This builds all modules and produces a directly runnable jar at:

```text
cleanCrud-generator-master\target\cleanCrud-generator-master-0.0.2-SNAPSHOT.jar
```

## Run The Generator

You can run the generator either from IntelliJ or directly from the terminal.

### Prerequisites

The generator expects the base model file to already exist.

Examples:

- `PersonModel.java`
- `TaskModel.java`
- any other consumer-defined base model interface

The generator derives the target source root from the location of that base model file, so generated files are written
back into the same target repository.

### Environment Variable

Set the full absolute path to the base model file in this environment variable:

```powershell
$env:CLEANCRUD_BASE_MODEL_FILE="C:\Path\PersonModel.java"
```

Important:

- this must be the full file path, not just the repo path
- the file must already exist
- the package does not need to be anything special; the generator reads the package from the model file itself

### Using The Checked-In Config

The checked-in config file is:

- .run/generate.from.base-model.json

It uses the `CLEANCRUD_BASE_MODEL_FILE` environment variable and generates the full system:

- `API`
- `DOMAIN`
- `INFRASTRUCTURE`
- `USE_CASES`
- `COMMON`

`COMMON` generation is auto-detected:

- if matching common files already exist in the target app, they are skipped
- otherwise they are generated

### Run From Terminal

From the repository root:

```powershell
java -jar .\cleanCrud-generator-master\target\cleanCrud-generator-master-0.0.2-SNAPSHOT.jar generate --config .\.run\generate.from.base-model.json
```

### Run From IntelliJ

Use the checked-in run configuration:

- `.run/generate.from.base-model.run.xml`

Before running it, make sure `CLEANCRUD_BASE_MODEL_FILE` is set in the run configuration or in your shell/session.

## Notes

The default checked-in config currently uses these generic substitutions:

- `U -> String`
- `V -> Integer`

If your model needs different concrete types, edit:

- .run/generate.from.base-model.json

## Sanity Check

A quick validation flow is:

```powershell
mvn clean install
$env:CLEANCRUD_BASE_MODEL_FILE="<full path to your base model file>"
java -jar .\cleanCrud-generator-master\target\cleanCrud-generator-master-0.0.2-SNAPSHOT.jar generate --config .\.run\generate.from.base-model.json
```

After generation, compile or run the target consumer project normally.