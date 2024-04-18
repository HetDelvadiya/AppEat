package com.awcindia.myapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.awcindia.myapp.bottomsheet.SuccessfullyOrderBottomFragment
import com.awcindia.myapp.databinding.ActivityPayOutBinding
import com.awcindia.myapp.model.ConfirmOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var userId : String
    private lateinit var databaseReference : DatabaseReference

    private lateinit var name : String
    private lateinit var address : String
    private lateinit var phoneNumber: String
    private lateinit var totalAmount: String
    private lateinit var foodItemName: MutableList<String>
    private lateinit var foodItemPrice : MutableList<String>
    private lateinit var foodItemDescription : MutableList<String>
    private lateinit var foodItemImage : MutableList<String>
    private lateinit var foodItemIngredient : MutableList<String>
    private lateinit var foodItemQuantities : MutableList<Int>

    private lateinit var binding : ActivityPayOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        // set UserData

        setUserData()

        // get user Details From Firebase

        val intent = intent
        foodItemName = intent.getStringArrayListExtra("foodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("foodItemPrice") as ArrayList<String>
        foodItemImage = intent.getStringArrayListExtra("foodItemImage") as ArrayList<String>
        foodItemDescription = intent.getStringArrayListExtra("foodItemDescription") as ArrayList<String>
        foodItemIngredient = intent.getStringArrayListExtra("foodItemIngredient") as ArrayList<String>
        foodItemQuantities = intent.getIntegerArrayListExtra("foodItemQuantities") as ArrayList<Int>



        totalAmount = calculateTotalAmount().toString() + "₹"
        binding.totalAmount.isEnabled = false
        binding.totalAmount.text = totalAmount


        binding.placeOrder.setOnClickListener {

            name = binding.name.text.toString().trim()
            address = binding.address.text.toString().trim()
            phoneNumber = binding.ContactNumbers.text.toString().trim()

            if (name.isBlank() && address.isBlank() && phoneNumber.isBlank()) {
                Toast.makeText(this@PayOutActivity, "Please Fill All Details", Toast.LENGTH_SHORT)
                    .show()
            } else {
                placeOrder()
            }

        }
    }
    private fun placeOrder() {
        userId = auth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = ConfirmOrder(userId , name , foodItemName ,   foodItemPrice, foodItemImage  , foodItemQuantities , address, totalAmount , phoneNumber , time , itemPushKey , false ,false)
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {

            val bottomSheetDialog = SuccessfullyOrderBottomFragment()
            bottomSheetDialog.show(supportFragmentManager , "Test")

            removeItemFromCart()
           addOrderToHistory(orderDetails)

        }.addOnFailureListener {
            Toast.makeText(this@PayOutActivity , "Order Failed" , Toast.LENGTH_SHORT).show()
        }

    }
    private fun addOrderToHistory(orderDetails: ConfirmOrder){
        databaseReference.child("user").child(userId).child("BuyHistory").child(orderDetails.itemPushKey!!).setValue(orderDetails).addOnSuccessListener {

        }
    }
    private fun removeItemFromCart() {
        val cartItemsReference = databaseReference.child("user").child(userId).child("CartItem")
        cartItemsReference.removeValue()
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

    private fun setUserData() {
            val user = auth.currentUser
            if (user != null) {
                val userId = user.uid
                val userReference = databaseReference.child("user").child(userId)

                userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val names = snapshot.child("name").getValue(String::class.java) ?: ""
                            val addresses =
                                snapshot.child("address").getValue(String::class.java) ?: ""
                            val phoneNumbers =
                                snapshot.child("phone").getValue(String::class.java) ?: ""


                            binding.apply {
                                name.setText(names)
                                address.setText(addresses)
                                ContactNumbers.setText(phoneNumbers)


                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@PayOutActivity, "", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }
