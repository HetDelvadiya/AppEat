package com.awcindia.myapp.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentBuyAdapter(private var context : Context ,
                       foodNameList : ArrayList<String>,
                       foodImageList : ArrayList<String>,
                       foodPriceList : ArrayList<String>,
                       foodQuantityList : ArrayList<String>) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>(){
   inner class RecentViewHolder {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding = 
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}