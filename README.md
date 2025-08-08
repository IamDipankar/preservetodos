# PreserveToDos - Android To-Do List App

A feature-rich Android application for managing to-do lists with deadline tracking and notification alerts.

## Features

### Core Functionality
- **Add Tasks**: Create new tasks with titles and optional descriptions
- **Edit Tasks**: Modify existing tasks including all properties
- **Delete Tasks**: Remove tasks that are no longer needed
- **Mark as Done**: Toggle task completion status with visual feedback

### Deadline Management
- **Set Deadlines**: Assign specific dates and times to tasks
- **Visual Indicators**: Clear display of deadline information
- **Overdue Detection**: Visual highlighting of overdue tasks

### Alert System
- **Custom Alerts**: Set reminders before task deadlines
- **Flexible Timing**: Choose from 5 minutes to 1 day before deadline
- **Push Notifications**: Receive notifications even when app is closed
- **Boot Persistence**: Alerts are restored after device restart

### User Interface
- **Tabbed View**: Organized display of All, Active, and Completed tasks
- **Material Design**: Modern, intuitive user interface
- **Task Status**: Visual indicators for completion, alerts, and deadlines
- **Empty State**: Helpful message when no tasks are present

## Technical Architecture

### Database
- **Room Database**: Local SQLite database for data persistence
- **Entity Relationships**: Structured data model for tasks
- **Type Converters**: Support for Date objects in database

### MVVM Pattern
- **ViewModel**: Business logic and data management
- **LiveData**: Reactive UI updates
- **Repository**: Data access abstraction layer

### Notification System
- **AlarmManager**: Precise scheduling of notifications
- **Broadcast Receivers**: Handle notification delivery and boot events
- **Notification Channels**: Android O+ compatible notifications

## Project Structure

```
src/main/java/com/todoapp/preservetodos/
├── MainActivity.kt                 # Main screen with task list
├── AddEditTaskActivity.kt         # Add/edit task form
├── NotificationReceiver.kt        # Handles notification delivery
├── BootReceiver.kt               # Restores alerts after boot
├── adapter/
│   └── TaskAdapter.kt            # RecyclerView adapter for tasks
├── data/
│   ├── Task.kt                   # Task entity model
│   ├── TaskDao.kt               # Database access object
│   ├── TaskDatabase.kt          # Room database configuration
│   └── Converters.kt            # Type converters for Room
├── repository/
│   └── TaskRepository.kt        # Data repository layer
├── viewmodel/
│   ├── TaskViewModel.kt         # Main view model
│   └── TaskViewModelFactory.kt  # ViewModel factory
└── utils/
    └── NotificationHelper.kt     # Notification management utilities
```

## Permissions

The app requires the following permissions:
- `SCHEDULE_EXACT_ALARM`: For precise notification scheduling
- `USE_EXACT_ALARM`: Alternative for exact alarms on newer Android versions
- `POST_NOTIFICATIONS`: For displaying notifications (Android 13+)
- `WAKE_LOCK`: To ensure notifications work when device is sleeping
- `RECEIVE_BOOT_COMPLETED`: To restore alerts after device restart

## Build Requirements

- **Android Studio**: Arctic Fox or newer
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Kotlin**: 1.9.10
- **Gradle**: 8.4

## Dependencies

### Core Android Libraries
- AndroidX Core KTX
- AppCompat
- Material Design Components
- ConstraintLayout
- Activity KTX
- Lifecycle ViewModel & LiveData

### Database
- Room Runtime, KTX, and Compiler
- Kotlin Coroutines support

### UI Components
- RecyclerView
- Material Design Components

### Background Processing
- WorkManager Runtime KTX

## Installation & Setup

1. **Clone or Download**: Get the project files
2. **Open in Android Studio**: Import the project
3. **Sync Gradle**: Let Android Studio download dependencies
4. **Build Project**: Ensure all files compile successfully
5. **Run on Device/Emulator**: Deploy to Android device or emulator

## Usage

### Adding a Task
1. Tap the "+" floating action button
2. Enter task title (required) and description (optional)
3. Optionally set a deadline by selecting date and time
4. Enable alerts and choose timing if desired
5. Tap "Save" to create the task

### Managing Tasks
- **Complete**: Tap the checkbox to mark as done
- **Edit**: Tap the edit button or the task itself
- **Delete**: Tap the delete button to remove
- **Filter**: Use tabs to view All, Active, or Completed tasks

### Notifications
- Notifications appear at the scheduled time before deadlines
- Tapping a notification opens the app
- Alerts are automatically rescheduled after device restart

## Development Notes

### Database Schema
The Task entity includes:
- `id`: Unique identifier (auto-generated)
- `title`: Task title (required)
- `description`: Optional task description
- `deadline`: Optional deadline date/time
- `isCompleted`: Completion status
- `hasAlert`: Whether alert is enabled
- `alertMinutesBefore`: Minutes before deadline for alert
- `createdAt`: Task creation timestamp
- `completedAt`: Task completion timestamp

### Notification Handling
- Uses AlarmManager for precise scheduling
- Handles Android version differences for exact alarms
- Gracefully degrades when exact alarm permission is denied
- Supports notification permission handling for Android 13+

### Data Persistence
- All data stored locally using Room database
- No internet connection required
- Automatic backup support for Android's backup service
- Data retained across app updates

## Future Enhancements

Potential improvements for future versions:
- Task categories and labels
- Priority levels
- Recurring tasks
- Cloud synchronization
- Task sharing
- Advanced filtering and search
- Statistics and productivity insights
- Dark theme support
- Widget for home screen

## Troubleshooting

### Notifications Not Working
- Check notification permissions in system settings
- Verify exact alarm permission is granted
- Ensure app is not being optimized/killed by battery optimization

### Database Issues
- Clear app data if experiencing persistent issues
- Check available storage space
- Verify app has proper file system permissions

## License

This project is created for educational and personal use. Feel free to modify and distribute according to your needs.

## Support

For issues or questions about this application:
1. Check the troubleshooting section above
2. Review the code comments for implementation details
3. Refer to Android development documentation for platform-specific issues
