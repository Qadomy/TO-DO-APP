package com.qadomy.to_do_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qadomy.to_do_app.data.model.TABLE_NAME
import com.qadomy.to_do_app.data.model.ToDo


@Database(entities = [ToDo::class], version = 1, exportSchema = false)
@TypeConverters(ConverterPriority::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    TABLE_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}