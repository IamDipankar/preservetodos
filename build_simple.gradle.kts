// Simplified build file that might work with basic Android SDK
buildscript {
    repositories {
        // Try local repository first
        flatDir {
            dirs '/usr/local/lib/android/sdk'
        }
    }
    dependencies {
        // Use the most basic Android plugin
        classpath 'com.android.tools.build:gradle:7.0.0'
        classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0'
    }
}

allprojects {
    repositories {
        flatDir {
            dirs '/usr/local/lib/android/sdk'
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}