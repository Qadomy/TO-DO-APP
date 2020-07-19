package com.qadomy.to_do_app.screens.list_screen

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.qadomy.helper.SwipeToDelete
import com.qadomy.to_do_app.R
import com.qadomy.to_do_app.adapter.MyListAdapter
import com.qadomy.to_do_app.data.model.ToDo
import com.qadomy.to_do_app.data.viewmodel.TodoViewModel
import com.qadomy.to_do_app.databinding.ListFragmentBinding
import com.qadomy.to_do_app.screens.SharedViewModel

class ListFragment : Fragment() {

    private val mTodoViewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: MyListAdapter by lazy { MyListAdapter() }

    private var _binding: ListFragmentBinding? = null
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
        _binding = ListFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        // setup recycler view
        setupRecyclerView()

        mTodoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            mSharedViewModel.checkDatabaseEmpty(it)
            adapter.setData(it)
        })


        // for display menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerViewList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        // swipe to delete
        swipeToDelete(recyclerView)
    }


    // function for delete items from recycle view when swipe to left
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]

                // delete item
                mTodoViewModel.deleteData(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                // restore deleted item
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    // function for restore the data after deleted
    private fun restoreDeletedData(view: View, deletedItem: ToDo, adapterPosition: Int) {
        /**
         * if click undo button in snackBar we re-insert the data to database
         */
        val snackBar = Snackbar.make(
            view,
            "'${deletedItem.title}' Deleted Successfully",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mTodoViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(adapterPosition)
        }
        snackBar.show()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all)
            deleteAllDataFromDatabase()
        return super.onOptionsItemSelected(item)
    }


    // function for delete all data from database
    private fun deleteAllDataFromDatabase() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES") { _, _ ->
            mTodoViewModel.deleteAll()
            Snackbar.make(requireView(), "All Data Deleted Successfully", Snackbar.LENGTH_LONG)
                .show()
        }
        builder.setNegativeButton("NO") { _, _ -> }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure want to remove everything?")
        builder.create().show()
    }
}