# Spring boot template

Spring boot template

## Code quality plugins

### spotless

Uses the [palantir-java-format](https://github.com/palantir/palantir-java-format) config in `gradle.build` to format source code.

**How to run**

Check
```bash
./gradlew spotlessCheck
```

Apply format
```bash
./gradlew spotlessApply
```

### checkstyle

Rules for checkstyle: `config/checkstyle/checkstyle.xml`

Rules for suppressions: `config/checkstyle/checkstyle-suppressions.xml`

Reports: `build/reports/checkstyle`

**How to run**

```bash
 ./gradlew checkstyleMain
```

```bash
 ./gradlew checkstyleTest
```


### spotbugs

Rules for exclusion at `config/spotbugs/findbugs-exclude.xml`

**How to run**

```bash
./gradlew spotbugsMain
```

```bash
./gradlew spotbugsTest
```

## Security

### dependency check

```bash
./gradlew dependencyCheckAnalyze
```
