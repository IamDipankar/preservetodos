package com.todoapp.preservetodos.repository

import androidx.lifecycle.LiveData
import com.todoapp.preservetodos.data.Task
import com.todoapp.preservetodos.data.TaskDao
import java.util.Date

class TaskRepository(private val taskDao: TaskDao) {
    
    fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()
    
    fun getActiveTasks(): LiveData<List<Task>> = taskDao.getActiveTasks()
    
    fun getCompletedTasks(): LiveData<List<Task>> = taskDao.getCompletedTasks()
    
    suspend fun getTaskById(id: Long): Task? = taskDao.getTaskById(id)
    
    suspend fun getTasksWithAlerts(): List<Task> = taskDao.getTasksWithAlerts()
    
    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)
    
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    
    suspend fun deleteTaskById(id: Long) = taskDao.deleteTaskById(id)
    
    suspend fun updateTaskCompletion(id: Long, isCompleted: Boolean) {
        val completedAt = if (isCompleted) Date() else null
        taskDao.updateTaskCompletion(id, isCompleted, completedAt)
    }
}
