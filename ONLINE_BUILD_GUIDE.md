# Using GitHub Actions to Build Your APK

## Quick Setup Guide

### Step 1: Create GitHub Repository
1. Go to https://github.com and create a new repository
2. Name it "PreserveToDos" or any name you prefer
3. Make it public (for free Actions minutes)

### Step 2: Upload Your Project
```bash
# In your project directory
git init
git add .
git commit -m "Initial commit - Android ToDo App"
git branch -M main
git remote add origin https://github.com/yourusername/preservetodos.git
git push -u origin main
```

### Step 3: Automatic Build
- The GitHub Action workflow is already configured
- Every time you push code, it will automatically build the APK
- Go to the "Actions" tab in your repository to see the build progress

### Step 4: Download APK
1. Go to your repository's "Actions" tab
2. Click on the latest successful workflow run
3. Scroll down to "Artifacts"
4. Download "app-debug" or "app-release"

## Alternative: Direct Upload Method

If you don't want to use Git:

1. **Create repository on GitHub**
2. **Use GitHub's web interface** to upload files:
   - Click "uploading an existing file"
   - Drag and drop your entire project folder
   - Commit the changes
3. **GitHub Actions will automatically start building**

## Build Status
The workflow will:
- ✅ Set up Android development environment
- ✅ Install dependencies
- ✅ Build debug APK
- ✅ Build release APK (unsigned)
- ✅ Make APKs available for download

## Free Tier Limits
- **2000 build minutes per month** for free
- **500MB artifact storage**
- More than enough for personal projects

## No Local Setup Required!
- No need to install Android Studio
- No need to install Android SDK
- No need to configure development environment
- Everything runs in the cloud
