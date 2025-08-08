package com.todoapp.preservetodos

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.todoapp.preservetodos.utils.NotificationHelper

class NotificationReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val taskTitle = intent.getStringExtra("task_title") ?: return
        val minutesBefore = intent.getIntExtra("minutes_before", 0)
        
        NotificationHelper.showNotification(context, taskTitle, minutesBefore)
    }
}
