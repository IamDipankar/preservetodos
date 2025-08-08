package com.todoapp.preservetodos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.preservetodos.data.Task
import com.todoapp.preservetodos.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    
    val allTasks: LiveData<List<Task>> = repository.getAllTasks()
    val activeTasks: LiveData<List<Task>> = repository.getActiveTasks()
    val completedTasks: LiveData<List<Task>> = repository.getCompletedTasks()
    
    fun insertTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }
    
    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }
    
    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }
    
    fun updateTaskCompletion(taskId: Long, isCompleted: Boolean) = viewModelScope.launch {
        repository.updateTaskCompletion(taskId, isCompleted)
    }
    
    suspend fun getTaskById(id: Long): Task? {
        return repository.getTaskById(id)
    }
}
