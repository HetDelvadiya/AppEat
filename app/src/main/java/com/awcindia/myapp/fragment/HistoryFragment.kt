package com.awcindia.myapp.fragment


import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.awcindia.myapp.RecentBuyActivity
import com.awcindia.myapp.adapters.BuyAgainAdapters
import com.awcindia.myapp.databinding.FragmentHistoryBinding
import com.awcindia.myapp.model.ConfirmOrder
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId : String
    var listOfOrderItem : ArrayList<ConfirmOrder> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater , container , false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Retrieve and Display The user Order History
        retrieveBuyHistory()

        // Recent Buy Button Click
        binding.recentBuyItem.setOnClickListener{
            seeItemRecentBuy()
        }

        binding.orderRecieved.setOnClickListener {
            updateOrderStatus()
        }
        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPushKey = listOfOrderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
    }

    // For show a Recent Buy Items
    private fun seeItemRecentBuy() {
        listOfOrderItem.firstOrNull()?.let {
            recentBuy -> val i = Intent(requireContext() , RecentBuyActivity::class.java)
            i.putExtra("RecentBuyOrderItem" , listOfOrderItem)
            startActivity(i)
        }
    }

    // For Retrieve Items
    private fun retrieveBuyHistory() {
        binding.recentBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid?:""

        val buyItemReference : DatabaseReference = database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currentTime")

        shortingQuery.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapShot in snapshot.children){
                    val buyHistoryItem = buySnapShot.getValue(ConfirmOrder::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }

                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()){

                    // For Display Most Recent Buy Items
                    setDataInRecentBuy()
                    // For setUp to Recyclerview with previous Order Details
                    setPreviousBuyItemsRecyclerView()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Failed" , Toast.LENGTH_SHORT ).show()
            }

        })
    }


    // For Display Most Recent Buy Items
    private fun setDataInRecentBuy() {
        binding.recentBuyItem.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding){
                buyAgainFoodName.text = it.foodNames?.firstOrNull()?:""
                buyAgainFoodPrice.text = it.foodPrices?.firstOrNull()?:""
                val image = it.foodImages?.firstOrNull()?:""
                val uri = Uri.parse(image)
                context?.let { it1 -> Glide.with(it1).load(uri).into(buyAgainFoodImage) }

                val isOrderIsAccepted = listOfOrderItem[0].orderAccepted
                if (isOrderIsAccepted) {
                    orderStatus.background.setTint(Color.GREEN)
                    orderRecieved.visibility = View.VISIBLE
                }
                listOfOrderItem.reverse()
            }
        }
    }

    // For setUp to Recyclerview with previous Order Details
    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrices = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()
        for (i in 1 until listOfOrderItem.size){
            listOfOrderItem[i].foodNames?.firstOrNull()?.let { buyAgainFoodName.add(it) }
            listOfOrderItem[i].foodPrices?.firstOrNull()?.let { buyAgainFoodPrices.add(it) }
            listOfOrderItem[i].foodImages?.firstOrNull()?.let { buyAgainFoodImage.add(it) }

            val rv = binding.buyAgainRecyclerview
            rv.layoutManager = LinearLayoutManager(context)
            val buyAgainAdapter = context?.let {
                BuyAgainAdapters(buyAgainFoodName , buyAgainFoodPrices , buyAgainFoodImage ,
                    it
                )
            }
            rv.adapter = buyAgainAdapter
        }
    }
}