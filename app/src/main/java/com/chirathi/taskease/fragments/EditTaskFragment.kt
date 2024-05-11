package com.chirathi.taskease.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.chirathi.taskease.MainActivity
import com.chirathi.taskease.R
import com.chirathi.taskease.databinding.FragmentEditTaskBinding
import com.chirathi.taskease.viewmodel.TaskViewModel
import com.chirathi.taskease.model.Task

class EditTaskFragment : Fragment(R.layout.fragment_edit_task),MenuProvider {

    private var editTaskBinding: FragmentEditTaskBinding? = null
    private val binding get() = this.editTaskBinding!!

    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var currentTask: Task

    private val args: EditTaskFragmentArgs by this.navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.editTaskBinding= FragmentEditTaskBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost =this.requireActivity()
        menuHost.addMenuProvider(this, this.viewLifecycleOwner, Lifecycle.State.RESUMED)

        this.tasksViewModel= (this.activity as MainActivity).taskViewModel
        currentTask = args.task!!


        this.binding.editTaskTitle.setText(this.currentTask.taskName)
        this.binding.editTaskDesc.setText(this.currentTask.taskDes)

        this.binding.editTaskFab.setOnClickListener{
            val taskName = this.binding.editTaskTitle.text.toString().trim()
            val taskDesc = this.binding.editTaskDesc.text.toString().trim()

            if(taskName.isNotEmpty()){
                val task = Task(this.currentTask.id,taskName,taskDesc)
                this.tasksViewModel.updateTask(task)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                Toast.makeText(this.context,"Please enter name",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteTask(){

        AlertDialog.Builder(this.activity).apply{
            this.setTitle("Delete Task")
            this.setMessage("Do you want to delete this task?")
            this.setPositiveButton("Delete"){ _, _ ->
                this@EditTaskFragment.tasksViewModel.deleteTask(this@EditTaskFragment.currentTask)
                Toast.makeText(this.context,"Task Deleted",Toast.LENGTH_SHORT).show()
                this@EditTaskFragment.view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            this.setNegativeButton("Cancel",null)
        }.create().show()
    }



    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_task,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                this.deleteTask()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.editTaskBinding= null
    }
}