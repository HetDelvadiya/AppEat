package com.awcindia.myapp.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.awcindia.myapp.adapters.MenuAdapters
import com.awcindia.myapp.databinding.FragmentMenuBottomsheetBinding
import com.awcindia.myapp.model.MenuItems
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MenuBottomsheetFragment  : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMenuBottomsheetBinding

    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems : List<MenuItems>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBottomsheetBinding.inflate(inflater,container,false)


        retrieveMenuItems()

        return binding.root


    }

    private fun retrieveMenuItems(){

        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItems::class.java)
                    menuItem?.let { (menuItems as MutableList<MenuItems>).add(it)  }
                }
                Log.d("Items" , "onDataChange : Data Received")
                // set data to adapter
                setAdapter()
            }

            private fun setAdapter() {
                if (menuItems.isNotEmpty()) {
                    val adapter = MenuAdapters(menuItems, requireContext())
                    binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.menuRecyclerView.adapter = adapter
                    Log.d("Items" , "SetAdapter : Data set")
                }
                else{
                    Log.d("Items" , "SetAdapter : Data Not Set")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}