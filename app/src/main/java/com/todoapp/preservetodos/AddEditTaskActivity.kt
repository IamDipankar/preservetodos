package com.todoapp.preservetodos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.todoapp.preservetodos.data.Task
import com.todoapp.preservetodos.data.TaskDatabase
import com.todoapp.preservetodos.databinding.ActivityAddEditTaskBinding
import com.todoapp.preservetodos.repository.TaskRepository
import com.todoapp.preservetodos.utils.NotificationHelper
import com.todoapp.preservetodos.viewmodel.TaskViewModel
import com.todoapp.preservetodos.viewmodel.TaskViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddEditTaskActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
    }

    private lateinit var binding: ActivityAddEditTaskBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepository(TaskDatabase.getDatabase(this).taskDao()))
    }

    private var selectedDateTime: Calendar? = null
    private var editingTask: Task? = null
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAlertSpinner()
        setupClickListeners()

        val taskId = intent.getLongExtra(EXTRA_TASK_ID, -1L)
        if (taskId != -1L) {
            loadTask(taskId)
        }
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = if (intent.hasExtra(EXTRA_TASK_ID)) {
            getString(R.string.edit_task_title)
        } else {
            getString(R.string.add_task)
        }
    }

    private fun setupAlertSpinner() {
        val alertOptions = resources.getStringArray(R.array.alert_times)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, alertOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAlertTime.adapter = adapter

        binding.switchAlert.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutAlertOptions.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }

    private fun setupClickListeners() {
        binding.buttonSelectDate.setOnClickListener { showDatePicker() }
        binding.buttonSelectTime.setOnClickListener { showTimePicker() }
        binding.buttonClearDeadline.setOnClickListener { clearDeadline() }
        binding.buttonSave.setOnClickListener { saveTask() }
        binding.buttonCancel.setOnClickListener { finish() }
    }

    private fun loadTask(taskId: Long) {
        lifecycleScope.launch {
            editingTask = taskViewModel.getTaskById(taskId)
            editingTask?.let { task ->
                binding.apply {
                    editTextTitle.setText(task.title)
                    editTextDescription.setText(task.description)
                    
                    task.deadline?.let { deadline ->
                        selectedDateTime = Calendar.getInstance().apply { time = deadline }
                        updateDateTimeDisplay()
                    }
                    
                    switchAlert.isChecked = task.hasAlert
                    
                    // Set alert time selection
                    val alertMinutes = resources.getIntArray(R.array.alert_minutes)
                    val index = alertMinutes.indexOf(task.alertMinutesBefore)
                    if (index != -1) {
                        spinnerAlertTime.setSelection(index)
                    }
                }
            }
        }
    }

    private fun showDatePicker() {
        val calendar = selectedDateTime ?: Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                if (selectedDateTime == null) {
                    selectedDateTime = Calendar.getInstance()
                }
                selectedDateTime?.set(year, month, dayOfMonth)
                updateDateTimeDisplay()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker() {
        if (selectedDateTime == null) {
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show()
            return
        }

        val calendar = selectedDateTime!!
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                updateDateTimeDisplay()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }

    private fun updateDateTimeDisplay() {
        selectedDateTime?.let { calendar ->
            binding.textSelectedDateTime.text = "Selected: ${dateFormat.format(calendar.time)}"
            binding.textSelectedDateTime.visibility = View.VISIBLE
            binding.buttonClearDeadline.visibility = View.VISIBLE
        }
    }

    private fun clearDeadline() {
        selectedDateTime = null
        binding.textSelectedDateTime.visibility = View.GONE
        binding.buttonClearDeadline.visibility = View.GONE
        binding.switchAlert.isChecked = false
    }

    private fun saveTask() {
        val title = binding.editTextTitle.text.toString().trim()
        if (title.isEmpty()) {
            binding.editTextTitle.error = getString(R.string.title_required)
            return
        }

        val description = binding.editTextDescription.text.toString().trim()
        val deadline = selectedDateTime?.time
        val hasAlert = binding.switchAlert.isChecked

        // Validate deadline is in the future
        if (deadline != null && deadline.before(Date())) {
            Toast.makeText(this, getString(R.string.invalid_deadline), Toast.LENGTH_SHORT).show()
            return
        }

        val alertMinutes = if (hasAlert && deadline != null) {
            val alertMinutesArray = resources.getIntArray(R.array.alert_minutes)
            alertMinutesArray[binding.spinnerAlertTime.selectedItemPosition]
        } else {
            30
        }

        val task = if (editingTask != null) {
            editingTask!!.copy(
                title = title,
                description = description,
                deadline = deadline,
                hasAlert = hasAlert && deadline != null,
                alertMinutesBefore = alertMinutes
            )
        } else {
            Task(
                title = title,
                description = description,
                deadline = deadline,
                hasAlert = hasAlert && deadline != null,
                alertMinutesBefore = alertMinutes
            )
        }

        lifecycleScope.launch {
            val taskId = if (editingTask != null) {
                taskViewModel.updateTask(task)
                task.id
            } else {
                taskViewModel.insertTask(task)
                // Note: In a real app, you'd want to get the actual inserted ID
                // For now, we'll schedule with a placeholder
                0L
            }

            // Schedule notification if alert is enabled
            if (task.hasAlert && task.deadline != null) {
                NotificationHelper.scheduleNotification(this@AddEditTaskActivity, task, taskId)
            }

            Toast.makeText(this@AddEditTaskActivity, getString(R.string.task_saved), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
