package com.awcindia.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.awcindia.myapp.adapters.RecentBuyAdapter
import com.awcindia.myapp.databinding.ActivityRecentBuyBinding
import com.awcindia.myapp.model.ConfirmOrder

class RecentBuyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecentBuyBinding

    private lateinit var allFoodName: ArrayList<String>
    private lateinit var allFoodPrice: ArrayList<String>
    private lateinit var allFoodImage: ArrayList<String>
    private lateinit var allFoodQuantity: ArrayList<Int>
    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val recentOrderItems =
            intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<ConfirmOrder>
        recentOrderItems.let { orderDetail ->
            if (orderDetail.isNotEmpty()) {
                val recentOrderItem = orderDetail[recentOrderItems.size -1]

                allFoodName = recentOrderItem.foodNames as ArrayList<String>
                allFoodPrice = recentOrderItem.foodPrices as ArrayList<String>
                allFoodImage = recentOrderItem.foodImages as ArrayList<String>
                allFoodQuantity = recentOrderItem.foodQuantities as ArrayList<Int>
            }
        }

        setAdapter()
    }

    private fun setAdapter() {
        val rv = binding.RecentRecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this , allFoodName , allFoodPrice , allFoodImage , allFoodQuantity)
        rv.adapter = adapter
    }
}