package com.awcindia.myapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.awcindia.myapp.adapters.MenuAdapters
import com.awcindia.myapp.databinding.FragmentSearchBinding
import com.awcindia.myapp.model.MenuItems
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
   private lateinit var adapter : MenuAdapters
   private lateinit var database: FirebaseDatabase

   private val originalMenuItems = mutableListOf<MenuItems>()

//    private val FilerMenuFoodName = mutableListOf<String>()
//    private val FilterorignalMenuFoodPrice = mutableListOf<String>()
//    private val FilterMenuFoodImage = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater , container , false)

        // Retrieve menu item from database
        retrieveMenuItem()
        // SetUp SearchView
        setUpsearchView()

        return binding.root
    }

    private fun retrieveMenuItem() {

        //get Database Ref
        database = FirebaseDatabase.getInstance()
        // Ref to the menu Node
        val foodReference = database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItems::class.java)
                    menuItem?.let {
                        originalMenuItems.add(it)
                    }
                }

                showAllMenus()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun showAllMenus() {
        val filteredMenuItem = ArrayList(originalMenuItems)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<MenuItems>) {
        adapter = MenuAdapters(filteredMenuItem , requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
    }


    private fun setUpsearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItem(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItem(newText)
                return true
            }

        })
    }
            private fun filterMenuItem(query: String) {
                val filteredMenuItem = originalMenuItems.filter {
                    it.foodName?.contains(query , ignoreCase = true) == true
                }
                setAdapter(filteredMenuItem)
                adapter.notifyDataSetChanged()
            }

    }






