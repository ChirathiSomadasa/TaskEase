package com.chirathi.taskease.fragments

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
import com.chirathi.taskease.MainActivity
import com.chirathi.taskease.R
import com.chirathi.taskease.databinding.FragmentAddTaskBinding
import com.chirathi.taskease.viewmodel.TaskViewModel
import com.chirathi.taskease.model.Task

class AddTaskFragment : Fragment(R.layout.fragment_add_task), MenuProvider {


    private var addTaskBinding: FragmentAddTaskBinding? = null
    private val binding get() = addTaskBinding!!

    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var addTaskView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel
        addTaskView = view
    }

    private fun saveTask(view: View){

        val taskName = binding.addTaskTitle.text.toString().trim()
        val taskDes= binding.addTaskDesc.text.toString().trim()
        /* val taskPriority = binding.addSpinnerPriority.text.toString().trim()
         val taskDeadline = binding.addcalendarView.text.toString().trim()*/

        if(taskName.isNotEmpty()){
            val task = Task(0,taskName,taskDes)
            tasksViewModel.addTask(task)

            Toast.makeText(addTaskView.context,"Not Saved",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)

        }else{
            Toast.makeText(addTaskView.context,"Please enter task name",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_task,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return when(menuItem.itemId){
           R.id.saveMenu -> {
               saveTask(addTaskView)
               true
           }
           else -> false
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        addTaskBinding = null
    }

}