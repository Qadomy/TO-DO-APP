package com.qadomy.to_do_app.data.repository

import androidx.lifecycle.LiveData
import com.qadomy.to_do_app.data.database.TodoDao
import com.qadomy.to_do_app.data.model.ToDo

class TodoRepository(private val todoDao: TodoDao) {

    val getAllData: LiveData<List<ToDo>> = todoDao.getAllData()
    val sortByLowPriority: LiveData<List<ToDo>> = todoDao.sortByLowPriority()
    val sortByHighPriority: LiveData<List<ToDo>> = todoDao.sortByHighPriority()


    suspend fun insertData(toDo: ToDo) {
        todoDao.insertData(toDo)
    }


    suspend fun updateData(toDo: ToDo) {
        todoDao.updateData(toDo)
    }

    suspend fun deleteData(toDo: ToDo) {
        todoDao.deleteData(toDo)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }


    /** function for search in database */
    fun searchDatabase(searchQuery: String): LiveData<List<ToDo>> {
        return todoDao.searchDatabaseData(searchQuery)
    }


}