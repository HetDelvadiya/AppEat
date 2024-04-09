package com.awcindia.myapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.awcindia.myapp.PayOutActivity
import com.awcindia.myapp.adapters.CartAdapters
import com.awcindia.myapp.databinding.FragmentCartBinding
import com.awcindia.myapp.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodName: MutableList<String>
    private lateinit var foodPrice : MutableList<String>
    private lateinit var foodDescription : MutableList<String>
        private lateinit var foodImage : MutableList<String>
        private lateinit var foodIngredient : MutableList<String>
        private lateinit var foodQuantity : MutableList<Int>
        private lateinit var cartAdapters: CartAdapters
        private lateinit var userId : String

    private lateinit var totalAmount: String
    private lateinit var foodItemName: MutableList<String>
    private lateinit var foodItemPrice : MutableList<String>
    private lateinit var foodItemDescription : MutableList<String>
    private lateinit var foodItemImage : MutableList<String>
    private lateinit var foodItemIngredient : MutableList<String>
    private lateinit var foodItemQuantities : MutableList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retrieveCartItems()


        binding.proceedButton.setOnClickListener{
            // get order Item and total price and Items
            getOderItemDetails()
        }





        return binding.root
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until foodItemPrice.size){
            val price = foodItemPrice[i]
            val lastchar = price.last()
            val priceIntvale = if (lastchar == '₹'){
                price.dropLast(1).toInt()
            }else{
                price.toInt()
            }
            val quality = foodItemQuantities[i]
            totalAmount += priceIntvale * quality
        }
        return totalAmount
    }

    private fun getOderItemDetails() {
        val ordeIdReference : DatabaseReference = database.reference.child("user").child(userId).child("CartItem")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()
        val foodQuantities = cartAdapters.getUpdatedItemsQuantities()

        ordeIdReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    // get the cartItems to Respective  List

                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    // add items details in to list

                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodIngredient?.let { foodIngredient.add(it) }
                }
                orderNow(foodName , foodPrice , foodDescription , foodImage , foodIngredient , foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext() , "Order making is Failed please Try Again" , Toast.LENGTH_SHORT ).show()
            }

        })

    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodQuantities: MutableList<Int>,
    ) {
        if (isAdded && context!= null){
            val intent = Intent(requireContext() , PayOutActivity::class.java)
            intent.putExtra("foodItemName" , foodName as ArrayList<String>)
            intent.putExtra("foodItemPrice" , foodPrice as ArrayList<String>)
            intent.putExtra("foodItemImage" , foodImage as ArrayList<String>)
            intent.putExtra("foodItemDescription" , foodDescription as ArrayList<String>)
            intent.putExtra("foodItemIngredient" , foodIngredient as ArrayList<String>)
            intent.putExtra("foodItemQuantities" , foodQuantities as ArrayList<Int>)

            startActivity(intent)
        }


    }

    private fun retrieveCartItems() {
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?:""
        val foodReference : DatabaseReference = database.reference.child("user").child(userId).child("CartItem")

        // List to Store cart Items

        foodName = mutableListOf()
        foodPrice = mutableListOf()
        foodDescription = mutableListOf()
        foodIngredient = mutableListOf()
        foodImage = mutableListOf()
        foodQuantity = mutableListOf()

        // fetch data  from the database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    // get the cartItem object from child node
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)
                    cartItems?.foodName?.let { foodName.add(it) }
                    cartItems?.foodPrice?.let { foodPrice.add(it) }
                    cartItems?.foodDescription?.let { foodDescription.add(it) }
                    cartItems?.foodImage?.let { foodImage.add(it) }
                    cartItems?.foodIngredient?.let { foodIngredient.add(it) }
                    cartItems?.foodQuantity?.let { foodQuantity.add(it) }

                }

                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data Not Fatch", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setAdapter() {
        cartAdapters = CartAdapters(requireContext() , foodName , foodPrice , foodImage , foodDescription , foodIngredient ,foodQuantity )
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
        binding.cartRecyclerView.adapter = cartAdapters

    }
}