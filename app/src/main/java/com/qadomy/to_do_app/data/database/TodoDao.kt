package com.qadomy.to_do_app.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.qadomy.to_do_app.data.model.ToDo

@Dao
interface TodoDao {

    @Query("SELECT * FROM todotable ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDo>>

    /**
     * OnConflictStrategy.IGNORE -> mean ignore any new item same items in database
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDo)


    @Update
    suspend fun updateData(toDoData: ToDo)


    @Delete
    suspend fun deleteData(toDoData: ToDo)

    @Query("DELETE FROM todotable")
    suspend fun deleteAll()


    /** function for search in database */
    @Query("SELECT * FROM TodoTable WHERE title OR description LIKE :searchQuery")
    fun searchDatabaseData(searchQuery: String): LiveData<List<ToDo>>
}