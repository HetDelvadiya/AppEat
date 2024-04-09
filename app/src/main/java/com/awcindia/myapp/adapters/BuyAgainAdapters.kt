package com.awcindia.myapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awcindia.myapp.databinding.BuyAgainBinding
import com.bumptech.glide.Glide
import com.google.api.Context

class BuyAgainAdapters(private val buyAgainFoodName : MutableList<String> , private val buyAgainFoodPrices : MutableList<String> , private val buyAgainFoodImages : MutableList<String>, private var requireContext : android.content.Context): RecyclerView.Adapter<BuyAgainAdapters.BuyAgainView>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuyAgainAdapters.BuyAgainView {
        val binding = BuyAgainBinding.inflate(LayoutInflater.from(parent.context) ,parent , false)
        return BuyAgainView(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainAdapters.BuyAgainView, position: Int) {
        holder.bind(buyAgainFoodName[position] , buyAgainFoodPrices[position] , buyAgainFoodImages[position])
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    inner class BuyAgainView(private val binding : BuyAgainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(foodName: String, foodPrice: String, foodImage: String) {
            binding.buyAgainFoodName.text = foodName
            binding.buyAgainFoodPrice.text = foodPrice
           val uriString = foodImage
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyAgainFoodImage)
        }
    }
}