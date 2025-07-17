# 😂 DailyJoke

[![Android](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-orange.svg)](https://m3.material.io)
[![API Level](https://img.shields.io/badge/API-26%2B-yellow.svg)](https://developer.android.com/tools/releases/platforms)

> Una aplicación Android moderna que te trae una sonrisa cada día con chistes aleatorios. Construida con las últimas tecnologías de Android y siguiendo las mejores prácticas de desarrollo.

## ✨ Características

🎯 **Chistes Aleatorios** - Obtén chistes frescos y divertidos al instante  
💖 **Sistema de Favoritos** - Guarda tus chistes preferidos para verlos después  
🎨 **Diseño Material 3** - Interfaz moderna con colores dinámicos  
💾 **Persistencia Local** - Tus favoritos se guardan automáticamente  
🌟 **Edge-to-Edge** - Experiencia visual inmersiva  
⚡ **Performance Optimizada** - Smooth animations y respuesta rápida  
🧪 **Testing Completo** - Desarrollado con TDD (Test-Driven Development)

## 🚀 Screenshots

> *Screenshots de la aplicación se añadirán próximamente*

## 🛠️ Tecnologías

Este proyecto está construido usando tecnologías de vanguardia de Android:

- **[Kotlin](https://kotlinlang.org)** - 100% Kotlin, lenguaje moderno y conciso
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Toolkit declarativo para UI nativa
- **[Material 3](https://m3.material.io)** - Sistema de diseño más reciente de Google
- **[Retrofit](https://square.github.io/retrofit/)** - Cliente HTTP type-safe para Android
- **[Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Programación asíncrona elegante
- **[StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)** - Gestión de estado reactiva
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)** - Manejo del estado de UI
- **[SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences)** - Persistencia local ligera

### 🧪 Testing

- **[JUnit](https://junit.org/)** - Framework de testing unitario
- **[Mockk](https://mockk.io/)** - Mocking library para Kotlin
- **[Turbine](https://github.com/cashapp/turbine)** - Testing para Flow
- **[Truth](https://truth.dev/)** - Aserciones fluidas para tests

## 🏗️ Arquitectura

El proyecto sigue el patrón **MVVM (Model-View-ViewModel)** con **Clean Architecture**:

```
📁 app/src/main/java/dev/javfuentes/dailyjoke/
├── 📁 data/
│   ├── 📁 datasource/          # Fuentes de datos (API, Local)
│   ├── 📁 model/               # Modelos de datos y API
│   └── 📁 repository/          # Implementación del patrón Repository
├── 📁 di/                      # Inyección de dependencias manual
├── 📁 network/                 # Configuración de red y APIs
├── 📁 ui/
│   ├── 📁 components/          # Componentes reutilizables de UI
│   ├── 📁 navigation/          # Navegación entre pantallas
│   ├── 📁 screens/             # Pantallas principales
│   └── 📁 theme/               # Tema Material 3
├── 📁 utils/                   # Utilidades y constantes
└── 📁 viewmodel/               # ViewModels y estado de UI
```

### 🔄 Flujo de Datos

```
UI (Compose) ↔ ViewModel ↔ Repository ↔ DataSource
                    ↓
                StateFlow/UiState
```

## 📱 Características Técnicas

- **Single Activity Architecture** - Una sola Activity con navegación por Composables
- **Reactive Programming** - StateFlow para gestión de estado reactiva
- **Error Handling** - Manejo robusto de errores de red y API
- **Offline Support** - Los favoritos funcionan sin conexión
- **Material Design 3** - Colores dinámicos y theming moderno
- **Edge-to-Edge Layout** - Soporte completo para pantallas modernas

## 🎨 API

La aplicación consume la [JokeAPI](https://jokeapi.dev/) gratuita:

- **Endpoint**: `https://v2.jokeapi.dev/joke/Programming`
- **Formato**: JSON
- **Tipos**: Two-part jokes (setup + punchline)
- **Filtros**: Safe mode habilitado
- **Idioma**: Inglés

## 🚀 Instalación

### Prerrequisitos

- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 11 o superior
- Android SDK API 26+
- Dispositivo/Emulador con Android 8.0+ (API 26)

### Pasos

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/yourusername/DailyJoke.git
   cd DailyJoke
   ```

2. **Abre en Android Studio**
   - File → Open → Selecciona la carpeta del proyecto

3. **Sincroniza el proyecto**
   - Android Studio sincronizará automáticamente las dependencias

4. **Ejecuta la aplicación**
   - Conecta un dispositivo o inicia un emulador
   - Haz clic en Run ▶️

### 🏗️ Build Commands

```bash
# Limpiar proyecto
./gradlew clean

# Build debug
./gradlew assembleDebug

# Ejecutar tests
./gradlew test

# Ejecutar tests instrumentados
./gradlew connectedAndroidTest

# Lint check
./gradlew lint
```

## 📖 Uso

1. **Pantalla Principal**
   - Visualiza chistes aleatorios
   - Toca "New Joke" para obtener un chiste nuevo
   - Usa el botón ❤️ para guardar favoritos

2. **Pantalla de Favoritos**
   - Accede desde el botón "Favorites"
   - Ve todos tus chistes guardados
   - Elimina favoritos con el botón "Remove"

3. **Navegación**
   - Navegación simple entre pantallas
   - Botón de regreso en favoritos

## 🧪 Testing

El proyecto incluye tests completos siguiendo el enfoque TDD:

```bash
# Tests unitarios
./gradlew test

# Tests de UI (requiere dispositivo)
./gradlew connectedAndroidTest

# Coverage report
./gradlew jacocoTestReport
```

### 📊 Cobertura de Tests

- ✅ ViewModels (Business Logic)
- ✅ Repository Pattern
- ✅ Data Sources
- ✅ UI Components (en desarrollo)

## 🛣️ Roadmap

- [ ] 🌐 Soporte para múltiples idiomas
- [ ] 🎭 Más categorías de chistes
- [ ] 📤 Compartir chistes en redes sociales
- [ ] 🎨 Temas personalizables
- [ ] 📊 Estadísticas de uso
- [ ] 🔄 Widget para pantalla de inicio
- [ ] 🌙 Modo oscuro mejorado

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/amazing-feature`)
3. Commit tus cambios (`git commit -m 'Add amazing feature'`)
4. Push a la rama (`git push origin feature/amazing-feature`)
5. Abre un Pull Request

### 📋 Guidelines

- Sigue las convenciones de código Kotlin
- Añade tests para nuevas funcionalidades
- Actualiza la documentación cuando sea necesario
- Mantén los commits atómicos y descriptivos

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

**Javier Fuentes**
- GitHub: [@javfuentes](https://github.com/javfuentes)
- Email: dev.javfuentes@gmail.com

## 🙏 Agradecimientos

- [JokeAPI](https://jokeapi.dev/) por proporcionar la API gratuita de chistes
- [Android Jetpack](https://developer.android.com/jetpack) por las librerías modernas
- [Material Design](https://material.io/) por el sistema de diseño
- Comunidad de Android por las mejores prácticas

---

<div align="center">

**¿Te gustó el proyecto? ¡Dale una ⭐!**

</div>