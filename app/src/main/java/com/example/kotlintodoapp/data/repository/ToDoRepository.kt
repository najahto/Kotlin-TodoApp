package com.example.kotlintodoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.kotlintodoapp.data.ToDoDAO
import com.example.kotlintodoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDAO) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData) {
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }
}