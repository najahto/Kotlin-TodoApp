package com.example.kotlintodoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.kotlintodoapp.data.ToDoDAO
import com.example.kotlintodoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDAO) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

}