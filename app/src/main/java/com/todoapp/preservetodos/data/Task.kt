package com.todoapp.preservetodos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val deadline: Date?,
    val isCompleted: Boolean = false,
    val hasAlert: Boolean = false,
    val alertMinutesBefore: Int = 30, // Default 30 minutes before deadline
    val createdAt: Date = Date(),
    val completedAt: Date? = null
)
