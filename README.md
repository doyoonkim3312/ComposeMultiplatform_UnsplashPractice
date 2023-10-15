# Compose Multiplatform + Kotlin Desktop App Example
Compose Multiplatform (KMM) Practice Example - **`Unsplash Explorer`**  


## Overview
Compose multiplatform is a declarative framework for sharing UIs across multiple platforms with Kotlin (according to https://github.com/JetBrains/compose-multiplatform#) 
This means, it is possible to develop application for cross-platform, but at the same time, it also means it is absolutely possible to use Kotlin and Compose to develop 'Single Platform' program 
runs on specific environment. For example, instead of using Java and AWT/SWING, Compose Multiplatform and Kotlin could be better option. Of course, there are already existing options, including Flutter, etc., however,
for this example, a possibility of "replacing Java + AWT/SWING with Compose Multiplatform" has been evaluated, solidly based on personal experience and background.

### Basic Structure.
```txt
├── kotlin
│   ├── Main.kt
│   ├── SensitiveConstants.kt
│   ├── model
│   │   ├── ImageRepository.kt
│   │   ├── ImageResult.kt
│   │   └── UnsplashRemoteSource.kt
│   ├── screen
│   │   ├── ImageCard.kt
│   │   ├── SearchScreen.kt
│   │   └── component
│   ├── util
│   │   └── InstanceProvider.kt
│   └── viewmodel
│       └── SearchViewModel.kt
└── resources
    └── placeholder.png

```
This example has been developed by following MVVM with Clean Architecture recommended by Android. Since some useful tool, such as `viewModel` provided by Android SDK does not exist, thus, some custom approaches have been implemented to 'simulate' 
strcuture similar to one for Android. Additionally, to mimic dependency injection, Singleton pattern with necessary static functions have been declared.
To preserve and track State of Composable, `StateFlow` has been used. For tasks should be handled asynchronously, a Kotlin coroutine has been actively in used. At last, to generate a pipeline between Data source and Viewmodel, Kotlin Flow has been 
implemented.

<img width="1792" alt="Screenshot 2023-10-15 at 10 57 19 PM" src="https://github.com/doyoonkim3312/ComposeMultiplatform_UnsplashPractice/assets/61890844/b089421f-9bce-4d49-97f2-d044f708b32e">

All external libraries implemented are listed below, followed by brief description of corresponding feature where it has been used.
1. **`Ktor Http Client`**: Ktor Http Client library (core/serialization-json) has been used to handle network request to external service over HTTP. It enables to make @GET request to target server, receive corresponding result, and de-serialize it into appropriate data class. 

### Result
https://github.com/doyoonkim3312/ComposeMultiplatform_UnsplashPractice/assets/61890844/ab470d47-4d0f-4cda-96c3-5110cadc503a

<img width="1312" alt="Screenshot 2023-10-15 at 11 31 00 PM" src="https://github.com/doyoonkim3312/ComposeMultiplatform_UnsplashPractice/assets/61890844/39c74501-f76f-4530-9574-cbad53fdc3b6">

### Drawbacks & Issues Encountered.
1. Since Compose Multiplatform brought Jetpack Compsoe to scope of _'corss-platform'_, it is possible to assume that libraries for Jetpack Compose can be implemented in Compose Multiplatform project. However, unfortunately, it is not 
100 percent guaranteed that all libraries for Jetapck Compsoe can be implemented. This is mainly because some Jetpack Compsoe library have been developed with assumption that thier libraries
would be used for Android Project. Therefore, those libraries relies on Android SDK as well (for example, such as `androidx.lifecycle.*`)
2. (Issue) Originally, for processing network reqeust, a combination of **Retrofit2** (for making a request) and **GSON** (for de-serialize result into data class) was implemented. However, a GSON convertor generated an exception ONLY in release build, and accordingly, populating data to LazyStaggered Grid was impossible. After numbers of troubleshooting, it turned out that a proguard applied to the release build prevented GSON to find coressponding data class to store JSON data. To reslove this problem, Ktor HTTP Client was chosen as an alternative, and it successfully resloved described problem.
3. (Issue) Coil for Compose library was not able to be implemented. This problem is closely related to the drawbacks above. Coil is a coroutine-based lightweighted asynchronous image processing library. 
however, it was not possible to take advantages it provided in this project. Alternatively, a custom-defined **`AsyncImage`** composable has been implemented, based on a example code to process image asynchronously, from Compose-Multiplatform repository.

## Conclusion
Based on this project, it has been verified that Compose Multiplatform + Kotlin could be used to develop an application for desktop, regardless of the environment (thanks to JVM.) Also, as personal opinion, this combination might be the strongest candidate for replacing Java and AWT/SWING. (Technically, application developed with this combination uses AWT/SWING internally, since a Compose Multiplatform is a kind of 'shot-cut' of using AWT/SWING.) However, since it still shows some performance issues, such as suprisngly high memory consumption,
it could be extremely difficult to apply this combination for production-level application. Although it still requries continuous improvement, it is worthwhile to keep your eyes on this great technology.


