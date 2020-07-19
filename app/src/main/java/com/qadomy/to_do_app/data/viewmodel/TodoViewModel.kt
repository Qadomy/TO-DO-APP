package com.qadomy.to_do_app.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.qadomy.to_do_app.data.database.TodoDatabase
import com.qadomy.to_do_app.data.model.ToDo
import com.qadomy.to_do_app.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val repository: TodoRepository

    val getAllData: LiveData<List<ToDo>>
    val sortByLowPriority: LiveData<List<ToDo>>
    val sortByHighPriority: LiveData<List<ToDo>>

    init {
        repository = TodoRepository(todoDao)

        getAllData = repository.getAllData
        sortByLowPriority = repository.sortByLowPriority
        sortByHighPriority = repository.sortByHighPriority
    }


    fun insertData(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDo)
        }
    }


    fun updateData(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDo)
        }
    }

    fun deleteData(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(toDo)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }


    /** function for search in database */
    fun searchDatabase(searchQuery: String): LiveData<List<ToDo>> {
        return repository.searchDatabase(searchQuery)
    }
}