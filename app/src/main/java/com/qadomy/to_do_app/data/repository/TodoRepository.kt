package com.qadomy.to_do_app.data.repository

import androidx.lifecycle.LiveData
import com.qadomy.to_do_app.data.database.TodoDao
import com.qadomy.to_do_app.data.model.ToDo

class TodoRepository(private val todoDao: TodoDao) {

    val getAllData: LiveData<List<ToDo>> = todoDao.getAllData()

    suspend fun insertData(toDo: ToDo) {
        todoDao.insertData(toDo)
    }
}