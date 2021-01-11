package com.example.kotlintodoapp.fragments.list

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlintodoapp.R
import com.example.kotlintodoapp.data.viewmodel.ToDoViewModel
import com.example.kotlintodoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val mTodoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mTodoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //set menu
        setHasOptionsMenu(true)
        return view
    }

    private fun showEmptyDatabaseViews(emptyDB : Boolean) {
        if (emptyDB){
            view?.no_data_imageView?.visibility = View.VISIBLE
            view?.no_data_textView?.visibility = View.VISIBLE
        } else {
            view?.no_data_imageView?.visibility = View.INVISIBLE
            view?.no_data_textView?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    // Show Alert Dialog to confirm removal of all items from database Table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully Removed Everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Everything? ")
        builder.setMessage("Are you sure that you want to remove Everything?")
        builder.create().show()
    }
}