package com.todoapp.preservetodos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.todoapp.preservetodos.adapter.TaskAdapter
import com.todoapp.preservetodos.data.TaskDatabase
import com.todoapp.preservetodos.databinding.ActivityMainBinding
import com.todoapp.preservetodos.repository.TaskRepository
import com.todoapp.preservetodos.utils.NotificationHelper
import com.todoapp.preservetodos.viewmodel.TaskViewModel
import com.todoapp.preservetodos.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepository(TaskDatabase.getDatabase(this).taskDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupTabs()
        setupFab()
        observeTasks()

        // Initialize notification helper
        NotificationHelper.createNotificationChannel(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskClick = { task ->
                val intent = Intent(this, AddEditTaskActivity::class.java).apply {
                    putExtra(AddEditTaskActivity.EXTRA_TASK_ID, task.id)
                }
                startActivity(intent)
            },
            onTaskComplete = { task ->
                taskViewModel.updateTaskCompletion(task.id, !task.isCompleted)
            },
            onTaskDelete = { task ->
                taskViewModel.deleteTask(task)
            }
        )

        binding.recyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> observeAllTasks()
                    1 -> observeActiveTasks()
                    2 -> observeCompletedTasks()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupFab() {
        binding.fabAddTask.setOnClickListener {
            startActivity(Intent(this, AddEditTaskActivity::class.java))
        }
    }

    private fun observeTasks() {
        observeAllTasks() // Default to all tasks
    }

    private fun observeAllTasks() {
        taskViewModel.allTasks.observe(this) { tasks ->
            updateTaskList(tasks)
        }
    }

    private fun observeActiveTasks() {
        taskViewModel.activeTasks.observe(this) { tasks ->
            updateTaskList(tasks)
        }
    }

    private fun observeCompletedTasks() {
        taskViewModel.completedTasks.observe(this) { tasks ->
            updateTaskList(tasks)
        }
    }

    private fun updateTaskList(tasks: List<com.todoapp.preservetodos.data.Task>) {
        taskAdapter.submitList(tasks)
        
        if (tasks.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }
}
