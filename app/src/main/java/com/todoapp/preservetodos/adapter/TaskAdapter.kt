package com.todoapp.preservetodos.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.preservetodos.R
import com.todoapp.preservetodos.data.Task
import com.todoapp.preservetodos.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onTaskComplete: (Task) -> Unit,
    private val onTaskDelete: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        
        private val dateFormat = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
        
        fun bind(task: Task) {
            binding.apply {
                textTitle.text = task.title
                
                // Description
                if (task.description.isNotEmpty()) {
                    textDescription.text = task.description
                    textDescription.visibility = View.VISIBLE
                } else {
                    textDescription.visibility = View.GONE
                }
                
                // Deadline
                if (task.deadline != null) {
                    textDeadline.text = "Due: ${dateFormat.format(task.deadline)}"
                    textDeadline.visibility = View.VISIBLE
                    
                    // Check if overdue
                    if (task.deadline.before(Date()) && !task.isCompleted) {
                        textDeadline.setTextColor(ContextCompat.getColor(itemView.context, R.color.overdue_color))
                    } else {
                        textDeadline.setTextColor(ContextCompat.getColor(itemView.context, R.color.deadline_color))
                    }
                } else {
                    textDeadline.visibility = View.GONE
                }
                
                // Alert icon
                iconAlert.visibility = if (task.hasAlert && !task.isCompleted) View.VISIBLE else View.GONE
                
                // Completion status
                checkBoxCompleted.isChecked = task.isCompleted
                
                // Strike through completed tasks
                if (task.isCompleted) {
                    textTitle.paintFlags = textTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textTitle.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.darker_gray))
                } else {
                    textTitle.paintFlags = textTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    textTitle.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.black))
                }
                
                // Click listeners
                checkBoxCompleted.setOnClickListener {
                    onTaskComplete(task)
                }
                
                buttonEdit.setOnClickListener {
                    onTaskClick(task)
                }
                
                buttonDelete.setOnClickListener {
                    onTaskDelete(task)
                }
                
                itemView.setOnClickListener {
                    onTaskClick(task)
                }
            }
        }
    }
    
    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
