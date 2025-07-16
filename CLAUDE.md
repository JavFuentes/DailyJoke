# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

DailyJoke is a modern Android application built with Jetpack Compose and Material 3 design system. The project uses single Activity architecture with Compose UI and follows current Android development best practices.

## Essential Commands

### Build & Install
```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Build release APK
./gradlew assembleRelease
```

### Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Run all tests
./gradlew check
```

### Development
```bash
# Lint code
./gradlew lint

# Check for dependency updates
./gradlew dependencyUpdates
```

## Architecture & Framework

- **Framework**: Jetpack Compose with Material 3
- **Language**: Kotlin (100%)
- **Architecture**: Single Activity with Compose UI
- **Min SDK**: API 26 (Android 8.0)
- **Target SDK**: API 36
- **Build System**: Gradle with Kotlin DSL and Version Catalogs

## Key Technologies

- **UI**: Jetpack Compose with Material 3 design components
- **Theming**: Material 3 dynamic color support (Android 12+)
- **Display**: Edge-to-edge layout support
- **Dependencies**: Managed through `gradle/libs.versions.toml` version catalog
- **Testing**: JUnit for unit tests, Espresso + Compose UI testing for instrumented tests

## Project Structure

```
app/src/main/java/dev/javfuentes/dailyjoke/
├── MainActivity.kt                    # Single Activity with Compose
└── ui/theme/                         # Material 3 theming
    ├── Color.kt                      # Color definitions
    ├── Theme.kt                      # Theme configuration
    └── Type.kt                       # Typography definitions
```

## Development Guidelines

- All UI components should be built with Jetpack Compose (no XML layouts)
- Follow Material 3 design patterns and use provided theme components
- Use the version catalog in `gradle/libs.versions.toml` for dependency management
- Package structure follows `dev.javfuentes.dailyjoke.*` hierarchy
- Target modern Android versions while maintaining API 26+ compatibility
- Leverage edge-to-edge display capabilities in new features

## Configuration

- **JVM Target**: Java 11
- **Kotlin Version**: 2.0.21 with Compose Compiler
- **AGP Version**: 8.11.1
- **Gradle JVM Args**: `-Xmx2048m -Dfile.encoding=UTF-8`