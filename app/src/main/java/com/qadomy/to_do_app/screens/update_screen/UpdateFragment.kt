package com.qadomy.to_do_app.screens.update_screen

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.qadomy.to_do_app.R
import com.qadomy.to_do_app.data.model.ToDo
import com.qadomy.to_do_app.data.viewmodel.TodoViewModel
import com.qadomy.to_do_app.screens.SharedViewModel
import kotlinx.android.synthetic.main.update_fragment.*
import kotlinx.android.synthetic.main.update_fragment.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.update_fragment, container, false)

        // set menu
        setHasOptionsMenu(true)

        view.update_title_et.setText(args.currentItem.title)
        view.update_descriptions_et.setText(args.currentItem.description)
        view.update_priorities_spinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.update_priorities_spinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItemInDatabase()
            R.id.menu_delete -> deleteItemFromDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    // delete item from database
    private fun deleteItemFromDatabase() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES") { _, _ ->
            mTodoViewModel.deleteData(args.currentItem)
            Snackbar.make(requireView(), "Successfully Deleted", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("NO") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}' ?")
        builder.setMessage("Are you sure want to remove ${args.currentItem.title} ?")
        builder.create().show()
    }

    // update current item in database
    private fun updateItemInDatabase() {
        val title = update_title_et.text.toString()
        val description = update_descriptions_et.text.toString()
        val priority = update_priorities_spinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFormatter(title, description)
        if (validation) {
            // update current item
            val updateItem = ToDo(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )

            mTodoViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Successfully Updated!!", Toast.LENGTH_SHORT).show()

            // navigate back to list fragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        } else {
            Snackbar.make(requireView(), "Please Fill All Fields!!", Snackbar.LENGTH_LONG).show()
        }
    }

}