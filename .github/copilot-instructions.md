<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Android To-Do List App Instructions

This is an Android application project for managing to-do lists with the following features:
- Adding, removing, and marking tasks as done
- Setting deadlines for tasks
- Configuring alerts before task deadlines
- Push notifications for alerts

## Architecture
- Uses Room database for local storage
- Implements MVVM architecture pattern
- Uses RecyclerView for displaying tasks
- Implements AlarmManager for scheduling notifications
- Uses WorkManager for background tasks

## Key Components
- MainActivity: Main screen displaying task list
- Task entity: Data model for tasks
- TaskDao: Database access object
- TaskRepository: Data repository layer
- TaskAdapter: RecyclerView adapter for task display
- NotificationService: Handles alert notifications

Please follow Android development best practices and use modern Android APIs.
