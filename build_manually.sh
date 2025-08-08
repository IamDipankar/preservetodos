#!/bin/bash

# Android Build Script for PreserveToDos
set -e

# Configuration
ANDROID_SDK=/usr/local/lib/android/sdk
PLATFORM_VERSION=34
BUILD_TOOLS_VERSION=34.0.0
APP_NAME=PreserveToDos
PACKAGE_NAME=com.todoapp.preservetodos

# Paths
PLATFORM_DIR=$ANDROID_SDK/platforms/android-$PLATFORM_VERSION
BUILD_TOOLS_DIR=$ANDROID_SDK/build-tools/$BUILD_TOOLS_VERSION
PLATFORM_TOOLS_DIR=$ANDROID_SDK/platform-tools

# Set PATH
export PATH=$BUILD_TOOLS_DIR:$PLATFORM_TOOLS_DIR:$PATH

echo "=== Building $APP_NAME ==="
echo "Platform: $PLATFORM_DIR"
echo "Build Tools: $BUILD_TOOLS_DIR"

# Check if required files exist
if [ ! -d "$PLATFORM_DIR" ]; then
    echo "Error: Android platform $PLATFORM_VERSION not found!"
    exit 1
fi

if [ ! -d "$BUILD_TOOLS_DIR" ]; then
    echo "Error: Build tools $BUILD_TOOLS_VERSION not found!"
    exit 1
fi

# Step 1: Generate R.java using aapt
echo "=== Step 1: Generating R.java ==="
aapt package -f -m \
    -J build/gen \
    -M app/src/main/AndroidManifest.xml \
    -S app/src/main/res \
    -I $PLATFORM_DIR/android.jar \
    --auto-add-overlay

if [ $? -eq 0 ]; then
    echo "✓ R.java generated successfully"
else
    echo "✗ Failed to generate R.java"
    exit 1
fi

# Step 2: Compile Kotlin sources (simplified - would need kotlinc with Android dependencies)
echo "=== Step 2: Compiling sources ==="
echo "Note: This would require kotlinc with Android classpath configuration"
echo "For now, creating a basic APK structure..."

# Step 3: Create APK structure 
echo "=== Step 3: Creating APK structure ==="
mkdir -p build/apk/META-INF

# Step 4: Package resources
echo "=== Step 4: Packaging resources ==="
aapt package -f \
    -M app/src/main/AndroidManifest.xml \
    -S app/src/main/res \
    -I $PLATFORM_DIR/android.jar \
    -F build/apk/resources.ap_ \
    --auto-add-overlay

if [ $? -eq 0 ]; then
    echo "✓ Resources packaged successfully"
else
    echo "✗ Failed to package resources"
    exit 1
fi

echo "=== Build Summary ==="
echo "✓ R.java generated"
echo "✓ Resources packaged"
echo "- Kotlin compilation skipped (requires proper classpath setup)"
echo "- APK creation skipped (requires compiled classes)"
echo ""
echo "Generated files:"
find build -type f -name "*.java" -o -name "*.ap_" | head -10

echo ""
echo "To complete the build, you would need:"
echo "1. Kotlin compiler with Android classpath"
echo "2. Room, WorkManager, and other dependencies"
echo "3. Proper DEX conversion"
echo "4. APK signing"