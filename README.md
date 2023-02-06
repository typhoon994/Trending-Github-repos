# Trending-Github-repos

Simple showcase app that implements use cases related to fetching Github repositories. 
Demonstrates modern Android UI toolkit, architecture, and setup.

## Tech stack
### UI layer
- Jetpack Compose 
- [Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3)
- [Coil Compose](https://coil-kt.github.io/coil/compose/) image loading library: At the moment the best alternative for industy standard library - Glide, which is in alpha for Compose.
It exposes mechanisms for async image fetching and displaying in Compose friendly way. 

### Domain & Data layer
- Retrofit and OkHttp: Industry standard for networking on Android at the moment
- [Moshi](https://github.com/square/moshi): Modern JSON library for Android, Java and Kotlin by Square. Replacement for deprecated GSON library. 

### Dependency injection
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

### Testing
- JUnit4 + Compose + Hilt
- [Mockk](https://github.com/mockk/mockk): Modern replacement for Mockito. Since it's written in Kotlin it out-of-box supports Kotlin specifics and coroutines, with smaller footprint compared to Mockito.

### CI/CD
- Github Actions
- [Detekt](https://detekt.dev/docs/intro/): Static code analysis tool for the Kotlin programming language
- GitFlow

### Agile
- Github Issues
