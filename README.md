![Maven Central Version](https://img.shields.io/maven-central/v/dev.nextftc/ftc)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/t/NextFTC/NextFTC?label=Commits)](https://github.com/NextFTC/NextFTC/commits/main/)

# NextFTC

NextFTC is our command-based framework. It has three building-blocks: commands,
subsystems, and components. It also has an optional hardware module that
provides built-in, ready-to-use hardware commands for almost all of your
hardware needs. Read the [docs](https://nextftc.dev).

## Installation

In the TeamCode `build.gradle`, go to the `dependencies` block.
Add the following lines:

::: tabs key:gradle

== .gradle

```groovy
implementation 'dev.nextftc:ftc:1.0.0'
implementation 'dev.nextftc:hardware:1.0.0' // If you would like to use the hardware module
```

== .gradle.kts

```kotlin
implementation("dev.nextftc:ftc:1.0.0")
implementation("dev.nextftc:hardware:1.0.0") // If you would like to use the hardware module
```

:::

Then, press the `Sync Now` button that appeared as a banner at the top of your
Gradle file.

*You're good to go!*
