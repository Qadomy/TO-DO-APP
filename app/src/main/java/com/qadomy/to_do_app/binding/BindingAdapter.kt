package com.qadomy.to_do_app.binding

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qadomy.to_do_app.R
import com.qadomy.to_do_app.data.model.Priority
import com.qadomy.to_do_app.data.model.ToDo
import com.qadomy.to_do_app.screens.list_screen.ListFragmentDirections

class BindingAdapter {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }


        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun navigateToAddFragment(view: ConstraintLayout, currentItem: ToDo) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }


        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    view.setSelection(0)
                }
                Priority.MEDIUM -> {
                    view.setSelection(1)
                }
                Priority.LOW -> {
                    view.setSelection(2)
                }
            }
        }


        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> cardView.setCardBackgroundColor(
                    ContextCompat.getColor(cardView.context, android.R.color.holo_red_dark)
                )

                Priority.MEDIUM -> cardView.setCardBackgroundColor(
                    ContextCompat.getColor(cardView.context, android.R.color.holo_purple)
                )
                Priority.LOW -> cardView.setCardBackgroundColor(
                    ContextCompat.getColor(cardView.context, android.R.color.holo_green_dark)
                )
            }
        }


    }
}