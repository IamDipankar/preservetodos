# Building the PreserveToDos Android APK

## Prerequisites

To build this Android app, you need one of the following:

### Option 1: Android Studio (Recommended)
1. **Download Android Studio** from https://developer.android.com/studio
2. **Install Android Studio** with the Android SDK
3. **Open the project** in Android Studio
4. **Sync Gradle** files (Android Studio will prompt you)
5. **Build APK** using Build → Build Bundle(s) / APK(s) → Build APK(s)

### Option 2: Command Line with Android SDK
1. **Download Android SDK Command Line Tools** from https://developer.android.com/studio#command-tools
2. **Set ANDROID_HOME** environment variable to SDK location
3. **Add SDK tools to PATH**:
   - `%ANDROID_HOME%\platform-tools`
   - `%ANDROID_HOME%\tools`
   - `%ANDROID_HOME%\tools\bin`

## Building Commands

Once you have the Android SDK set up:

```bash
# Debug APK (for testing)
./gradlew assembleDebug

# Release APK (for distribution)
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

## Output Location

The built APK will be located at:
- **Debug**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release**: `app/build/outputs/apk/release/app-release.apk`

## Required Android SDK Components

The project requires:
- **Compile SDK**: 34 (Android 14)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Build Tools**: 34.0.0
- **Kotlin**: 1.9.10

## Troubleshooting

### Gradle Build Issues
- Ensure you have Java 8 or higher installed
- Check that ANDROID_HOME is set correctly
- Verify internet connection for dependency downloads

### Kotlin Compilation Issues
- The project uses Kotlin 1.9.10
- Ensure your Android Studio supports this version

### Missing Dependencies
- Run `./gradlew build --refresh-dependencies` to refresh
- Check that repositories (Google, Maven Central) are accessible

## Project Structure Verification

Your project should have this structure:
```
PreserveToDos/
├── app/
│   ├── build.gradle (app module config)
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/todoapp/preservetodos/
│           └── res/
├── build.gradle.kts (root project config)
├── settings.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
└── gradle/wrapper/gradle-wrapper.properties
```

## Quick Start with Android Studio

1. **Open Android Studio**
2. **Select "Open an existing project"**
3. **Navigate to** `c:\Users\Dipankar Mitra\Desktop\app`
4. **Click "Open"**
5. **Wait for Gradle sync** to complete
6. **Click the green play button** or go to Build → Build Bundle(s) / APK(s) → Build APK(s)

The APK will be generated and you can install it on any Android device running Android 7.0 or higher.
