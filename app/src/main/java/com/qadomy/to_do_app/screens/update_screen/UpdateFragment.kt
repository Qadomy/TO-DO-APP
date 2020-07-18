package com.qadomy.to_do_app.screens.update_screen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.qadomy.to_do_app.R
import com.qadomy.to_do_app.data.model.Priority
import com.qadomy.to_do_app.screens.SharedViewModel
import kotlinx.android.synthetic.main.update_fragment.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.update_fragment, container, false)

        // set menu
        setHasOptionsMenu(true)

        view.update_title_et.setText(args.currentItem.title)
        view.update_descriptions_et.setText(args.currentItem.description)
        view.update_priorities_spinner.setSelection(parsePriority(args.currentItem.priority))
        view.update_priorities_spinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }


    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}