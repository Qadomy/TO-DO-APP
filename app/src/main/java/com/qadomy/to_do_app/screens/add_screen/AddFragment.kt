package com.qadomy.to_do_app.screens.add_screen

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.qadomy.to_do_app.R
import com.qadomy.to_do_app.data.model.ToDo
import com.qadomy.to_do_app.data.viewmodel.TodoViewModel
import com.qadomy.to_do_app.screens.SharedViewModel
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.android.synthetic.main.add_fragment.view.*

class AddFragment : Fragment() {

    private val todoViewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_fragment, container, false)

        setHasOptionsMenu(true)

        view.add_priorities_spinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add)
            insertDataToDatabase()
        return super.onOptionsItemSelected(item)
    }

    // function for insert data from UI to database
    private fun insertDataToDatabase() {
        val title = add_title_et.text.toString()
        val priority = add_priorities_spinner.selectedItem.toString()
        val description = add_descriptions_et.text.toString()

        // check validity
        val validation = mSharedViewModel.verifyDataFormatter(title, description)
        if (validation) {
            val newData = ToDo(0, title, mSharedViewModel.parsePriority(priority), description)
            todoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Add new data successfully!", Toast.LENGTH_SHORT)
                .show()

            // navigate back to list
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        } else
            Snackbar.make(requireView(), "Please Fill All Fields", Snackbar.LENGTH_LONG).show()
    }


}