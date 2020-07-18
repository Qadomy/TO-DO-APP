package com.qadomy.to_do_app.data.database

import androidx.room.TypeConverter
import com.qadomy.to_do_app.data.model.Priority

class ConverterPriority {

    /**
     * Because Room database just take an primitive types (string, int, ...),
     * we can't save enum class as entries to database
     *
     * This two classes converter from priority class to string to save it in database
     * and in opposite when read from database convert from string to class
     */


    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name // return the name of priority enum
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}