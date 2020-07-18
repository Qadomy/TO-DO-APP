package com.qadomy.to_do_app.screens.list_screen

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.qadomy.to_do_app.R
import com.qadomy.to_do_app.adapter.MyListAdapter
import com.qadomy.to_do_app.data.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.list_fragment.view.*

class ListFragment : Fragment() {

    private val mTodoViewModel: TodoViewModel by viewModels()
    private val adapter: MyListAdapter by lazy { MyListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        view.list_layout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }


        val recyclerView = view.recyclerView_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mTodoViewModel.getAllData.observe(viewLifecycleOwner, Observer { adapter.setData(it) })

        // for display menu
        setHasOptionsMenu(true)

        return view
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