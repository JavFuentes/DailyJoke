# 😂 DailyJoke

[![Android](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-orange.svg)](https://m3.material.io)
[![API Level](https://img.shields.io/badge/API-26%2B-yellow.svg)](https://developer.android.com/tools/releases/platforms)

> A modern Android application that brings you a smile every day with random jokes. Built with the latest Android technologies following development best practices.

## ✨ Features

🎯 **Random Jokes** - Get fresh and funny jokes instantly  
💖 **Favorites System** - Save your preferred jokes to view them later  
🎨 **Material 3 Design** - Modern interface with dynamic colors  
💾 **Local Persistence** - Your favorites are saved automatically  
🌟 **Edge-to-Edge** - Immersive visual experience  
⚡ **Optimized Performance** - Smooth animations and fast response  
🧪 **Complete Testing** - Developed with TDD (Test-Driven Development)

## 🚀 Screenshots

> *Application screenshots will be added soon*

## 🛠️ Technologies

This project is built using cutting-edge Android technologies:

- **[Kotlin](https://kotlinlang.org)** - 100% Kotlin, modern and concise language
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Declarative toolkit for native UI
- **[Material 3](https://m3.material.io)** - Google's latest design system
- **[Retrofit](https://square.github.io/retrofit/)** - Type-safe HTTP client for Android
- **[Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Elegant asynchronous programming
- **[StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)** - Reactive state management
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)** - UI state handling
- **[SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences)** - Lightweight local persistence

### 🧪 Testing

- **[JUnit](https://junit.org/)** - Unit testing framework
- **[Mockk](https://mockk.io/)** - Mocking library for Kotlin
- **[Turbine](https://github.com/cashapp/turbine)** - Testing for Flow
- **[Truth](https://truth.dev/)** - Fluent assertions for tests

## 🏗️ Architecture

The project follows the **MVVM (Model-View-ViewModel)** pattern with **Clean Architecture**:

```
📁 app/src/main/java/dev/javfuentes/dailyjoke/
├── 📁 data/
│   ├── 📁 datasource/          # Data sources (API, Local)
│   ├── 📁 model/               # Data models and API
│   └── 📁 repository/          # Repository pattern implementation
├── 📁 di/                      # Manual dependency injection
├── 📁 network/                 # Network configuration and APIs
├── 📁 ui/
│   ├── 📁 components/          # Reusable UI components
│   ├── 📁 navigation/          # Screen navigation
│   ├── 📁 screens/             # Main screens
│   └── 📁 theme/               # Material 3 theme
├── 📁 utils/                   # Utilities and constants
└── 📁 viewmodel/               # ViewModels and UI state
```

### 🔄 Data Flow

```
UI (Compose) ↔ ViewModel ↔ Repository ↔ DataSource
                    ↓
                StateFlow/UiState
```

## 📱 Technical Features

- **Single Activity Architecture** - Single Activity with Composable navigation
- **Reactive Programming** - StateFlow for reactive state management
- **Error Handling** - Robust network and API error handling
- **Offline Support** - Favorites work without connection
- **Material Design 3** - Dynamic colors and modern theming
- **Edge-to-Edge Layout** - Full support for modern screens

## 🎨 API

The application consumes the free [JokeAPI](https://jokeapi.dev/):

- **Endpoint**: `https://v2.jokeapi.dev/joke/Programming`
- **Format**: JSON
- **Types**: Two-part jokes (setup + punchline)
- **Filters**: Safe mode enabled
- **Language**: English

## 🚀 Installation

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or higher
- JDK 11 or higher
- Android SDK API 26+
- Device/Emulator with Android 8.0+ (API 26)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/DailyJoke.git
   cd DailyJoke
   ```

2. **Open in Android Studio**
   - File → Open → Select the project folder

3. **Sync the project**
   - Android Studio will automatically sync dependencies

4. **Run the application**
   - Connect a device or start an emulator
   - Click Run ▶️

### 🏗️ Build Commands

```bash
# Clean project
./gradlew clean

# Build debug
./gradlew assembleDebug

# Run tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Lint check
./gradlew lint
```

## 📖 Usage

1. **Main Screen**
   - View random jokes
   - Tap "New Joke" to get a new joke
   - Use the ❤️ button to save favorites

2. **Favorites Screen**
   - Access from "Favorites" button
   - See all your saved jokes
   - Remove favorites with "Remove" button

3. **Navigation**
   - Simple navigation between screens
   - Back button in favorites

## 🧪 Testing

The project includes comprehensive tests following TDD approach:

```bash
# Unit tests
./gradlew test

# UI tests (requires device)
./gradlew connectedAndroidTest

# Coverage report
./gradlew jacocoTestReport
```

### 📊 Test Coverage

- ✅ ViewModels (Business Logic)
- ✅ Repository Pattern
- ✅ Data Sources
- ✅ UI Components (in development)

## 📄 License

This project is under the MIT License. See the [LICENSE](LICENSE) file for more details.

## 👨‍💻 Author

**Javier Fuentes**
- GitHub: [@javfuentes](https://github.com/javfuentes)
- Email: dev.javfuentes@gmail.com

## 🙏 Acknowledgments

- [JokeAPI](https://jokeapi.dev/) for providing the free jokes API
- [Android Jetpack](https://developer.android.com/jetpack) for modern libraries
- [Material Design](https://material.io/) for the design system
- Android community for best practices

---

<div align="center">

**Did you like the project? Give it a ⭐!**

</div>