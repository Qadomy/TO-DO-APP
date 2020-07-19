package com.qadomy.to_do_app.screens.update_screen

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.qadomy.to_do_app.R
import com.qadomy.to_do_app.data.model.ToDo
import com.qadomy.to_do_app.data.viewmodel.TodoViewModel
import com.qadomy.to_do_app.databinding.UpdateFragmentBinding
import com.qadomy.to_do_app.screens.SharedViewModel
import kotlinx.android.synthetic.main.update_fragment.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: TodoViewModel by viewModels()

    private var _binding: UpdateFragmentBinding? = null
    private val binding get() = _binding!!

    // onDestroy, set _binding = null -> for avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // data binding
        _binding = UpdateFragmentBinding.inflate(inflater, container, false)

        binding.args = args
        // set menu
        setHasOptionsMenu(true)

        // set color fot spinner items
        binding.updatePrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
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
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure want to remove ${args.currentItem.title}..?")
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

            // navigate back to list fragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        } else {
            Snackbar.make(requireView(), "Please Fill All Fields!!", Snackbar.LENGTH_LONG).show()
        }
    }

}