package com.chirathi.taskease.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tasks")
@Parcelize
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val taskName: String,
    val taskDes: String
   /* val taskPriority:String,
    val taskDeadline:String*/
):Parcelable
