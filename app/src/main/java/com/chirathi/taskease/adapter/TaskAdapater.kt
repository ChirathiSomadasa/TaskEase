package com.chirathi.taskease.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chirathi.taskease.databinding.TaskLayoutBinding
import com.chirathi.taskease.fragments.HomeFragmentDirections
import com.chirathi.taskease.model.Task

class TaskAdapater : RecyclerView.Adapter<TaskAdapater.TaskViewHolder>() {

    class TaskViewHolder(val itemBinding: TaskLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.taskDes == newItem.taskDes &&
                    oldItem.taskName == newItem.taskName &&
                    oldItem.taskPriority == newItem.taskPriority &&
                    oldItem.taskDeadline == newItem.taskDeadline
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
           return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = differ.currentList[position]

        holder.itemBinding.taskName.text = currentTask.taskName
        holder.itemBinding.taskDes.text = currentTask.taskDes
        holder.itemBinding.taskPriority.text = currentTask.taskPriority
        holder.itemBinding.taskDeadline.text = currentTask.taskDeadline

        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToEditTaskFragment()
            it.findNavController().navigate(direction)
        }

    }
}