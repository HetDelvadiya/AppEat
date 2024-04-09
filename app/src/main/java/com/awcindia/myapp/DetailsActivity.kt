package com.awcindia.myapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.awcindia.myapp.databinding.ActivityDetailsBinding
import com.awcindia.myapp.model.CartItems
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var auth: FirebaseAuth
    var foodName: String? = null
    var foodPrice: String? = null
    var foodDescription: String? = null
    var foodImage: String? = null
    var foodIngredient: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        foodName = intent.getStringExtra("MenuItemName")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredient = intent.getStringExtra("MenuItemIngredients")
        foodPrice = intent.getStringExtra("MenuItemPrice")

        foodImage = intent.getStringExtra("MenuItemImage")
        with(binding) {
            detailsFoodName.text = foodName
            description.text = foodDescription
            ingredients.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailsFoodImage)


        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.addToCart.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""
        val cartItem = CartItems(
            foodName.toString(),
            foodPrice.toString(),
            foodDescription.toString(),
            foodImage.toString(),
            foodIngredient.toString(),
            1
        )

        // save data to cartItem to firebase database

        database.child("user").child(userId).child("CartItem").push().setValue(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Items Added into Cart Successfully", Toast.LENGTH_SHORT)
                    .show()

            }.addOnFailureListener{
                Toast.makeText(this , "Item Not Added" , Toast.LENGTH_SHORT).show()
            }
    }
}