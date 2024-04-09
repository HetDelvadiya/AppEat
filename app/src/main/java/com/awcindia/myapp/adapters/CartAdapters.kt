package com.awcindia.myapp.adapters

import android.net.Uri
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.awcindia.myapp.databinding.CartItemBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Context

class CartAdapters(
    private val context : android.content.Context ,
    private val CartItems: MutableList<String>,
    private val CartItemPrice: MutableList<String>,
    private val CartItemImage: MutableList<String>,
    private var cartDescription : MutableList<String>,
    private var foodIngredient : MutableList<String>,
    private var cartQuantity : MutableList<Int>
) : RecyclerView.Adapter<CartAdapters.CartViewHolder>() {


    private val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid?:""
        var cartItemNuber = CartItems.size

        itemQuantities = IntArray(cartItemNuber){1}
        cartItemReference = database.reference.child("user").child(userId).child("CartItem")
    }
    companion object{
        private var itemQuantities : IntArray = intArrayOf()
        private lateinit var cartItemReference : DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CartItems.size

    // get Updated Quantity
    fun getUpdatedItemsQuantities(): MutableList<Int> {
        val itemQuantity = mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        init {
//            binding.foodname.setOnClickListener{
//                val position = adapterPosition
//
//                if (position != RecyclerView.NO_POSITION){
//                    itemClickListener?.onItemClick(position)
//                }
//
//                val intent = Intent(requireContext , DetailsActivity::class.java)
//                intent.putExtra("MenuItemName" , cartItems[position])
//                intent.putExtra("MenuItemImage" , cartItemImage[position])
//                requireContext.startActivity(intent)
//            }
//        }

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                foodname.text = CartItems[position]
                price.text = CartItemPrice[position]
                val uriString = CartItemImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(Foodimage)

                NubOfItem.text = quantity.toString()

                removeButton.setOnClickListener {
                    DecreaseQuantity(position)
                }

                AddButton.setOnClickListener {
                    IncreaseQuantity(position)
                }

                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        DeleteItem(itemPosition)
                    }
                }

            }

        }


        fun DecreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                cartQuantity[position] = itemQuantities[position]
                binding.NubOfItem.text = itemQuantities[position].toString()
            }
        }

        fun IncreaseQuantity(position: Int) {
            if (itemQuantities[position] < 20) {
                itemQuantities[position]++
                cartQuantity[position] = itemQuantities[position]
                binding.NubOfItem.text = itemQuantities[position].toString()
            }
        }

        fun DeleteItem(position: Int) {
            val positionRetrieve = position
            getUniqueKeyAtPosition(positionRetrieve){
                uniqueKey -> if (uniqueKey != null){
                    removeItem(position , uniqueKey)
            }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if (uniqueKey != null){
                cartItemReference.child(uniqueKey).removeValue().addOnCanceledListener {
                    CartItems.removeAt(position)
                    CartItemImage.removeAt(position)
                    cartDescription.removeAt(position)
                    CartItemPrice.removeAt(position)
                    cartQuantity.removeAt(position)
                    foodIngredient.removeAt(position)
                    Toast.makeText(context , "Item Deleted Successfully" , Toast.LENGTH_SHORT).show()

                    // update itemQuantities
                    itemQuantities =  itemQuantities.filterIndexed{ index, i -> index != position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position , CartItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed To Delete Item", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int , onComplete :(String?) -> Unit ){
            cartItemReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey:String? = null

                    //loop for snapshot children
                    snapshot.children.forEachIndexed{ index, dataSnapshot -> if (index ==  positionRetrieve){
                        uniqueKey = dataSnapshot.key
                        return@forEachIndexed
                    }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

//    interface OnClickListener {
//        fun onItemClick(position: Int)
//    }


}