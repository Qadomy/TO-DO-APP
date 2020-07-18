package com.qadomy.to_do_app.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
}