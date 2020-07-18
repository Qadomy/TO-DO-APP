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

    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
    }


    fun insertData(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDo)
        }
    }
}