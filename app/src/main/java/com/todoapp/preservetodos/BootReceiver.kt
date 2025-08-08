package com.todoapp.preservetodos

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.todoapp.preservetodos.data.TaskDatabase
import com.todoapp.preservetodos.repository.TaskRepository
import com.todoapp.preservetodos.utils.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED ||
            intent.action == Intent.ACTION_PACKAGE_REPLACED) {
            
            // Reschedule all active task notifications after device restart
            rescheduleNotifications(context)
        }
    }
    
    private fun rescheduleNotifications(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = TaskDatabase.getDatabase(context)
            val repository = TaskRepository(database.taskDao())
            
            val tasksWithAlerts = repository.getTasksWithAlerts()
            
            tasksWithAlerts.forEach { task ->
                NotificationHelper.scheduleNotification(context, task, task.id)
            }
        }
    }
}
