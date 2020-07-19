package com.qadomy.to_do_app.screens

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.qadomy.to_do_app.data.model.Priority
import com.qadomy.to_do_app.data.model.ToDo

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * we set the init value as false for emptyDatabase until the (no data image and text)
     * not showing when start the app
     * */
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    // function for check the emptyDatabase is empty or no and return true and false
    fun checkDatabaseEmpty(toDo: List<ToDo>) {
        emptyDatabase.value = toDo.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            android.R.color.holo_red_dark
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            android.R.color.holo_purple
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            android.R.color.holo_green_dark
                        )
                    )
                }
            }
        }
    }

    // function for convert selected spinner to string
    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> {
                Priority.HIGH
            }
            "Medium Priority" -> {
                Priority.MEDIUM
            }
            "Low Priority" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }

    // function for check verifications of formatter of text
    fun verifyDataFormatter(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) false
        else !(title.isEmpty() || description.isEmpty())
    }


}