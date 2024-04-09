package com.awcindia.myapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awcindia.myapp.DetailsActivity
import com.awcindia.myapp.databinding.PopularItemBinding

class PopularAdapters(private val popularItemsNames : MutableList<String>, private val popularItemPrices : MutableList<String>, private val popularFoodImages : MutableList<Int>, private val requireContext : Context) : RecyclerView.Adapter<PopularAdapters.PopularViewHolder>() {

    private val itemClickListener : OnClickListener ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = popularFoodImages.size

    inner class PopularViewHolder(private  val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.foodname.setOnClickListener{
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION){
                    itemClickListener?.onItemClick(position)
                }

                val intent = Intent(requireContext , DetailsActivity::class.java)
                intent.putExtra("MenuItemName" , popularItemsNames[position])
                intent.putExtra("MenuItemImage" , popularFoodImages[position])
                requireContext.startActivity(intent)
            }
        }


        fun bind(position: Int) {
            binding.apply {
                foodname.text = popularItemsNames[position]
                price.text = popularItemPrices[position]
                Foodimage.setImageResource(popularFoodImages[position])

            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

}
