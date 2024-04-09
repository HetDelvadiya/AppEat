package com.awcindia.myapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awcindia.myapp.DetailsActivity
import com.awcindia.myapp.databinding.MenuItemBinding
import com.awcindia.myapp.model.MenuItems
import com.bumptech.glide.Glide

class MenuAdapters(private val menuItemss: List<MenuItems>, private val requireContext: Context) : RecyclerView.Adapter<MenuAdapters.MenuViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItemss.size

    inner class MenuViewHolder(private  val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.menuFoodName.setOnClickListener{
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION){
                    openDetailsActivity(position)
                }


            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItemss[position]

            val intent = Intent(requireContext , DetailsActivity::class.java).apply {
                putExtra("MenuItemName" , menuItem.foodName)
                putExtra("MenuItemImage" , menuItem.foodImage)
                putExtra("MenuItemDescription" , menuItem.foodDescription)
                putExtra("MenuItemIngredients" , menuItem.foodIngredient)
                putExtra("MenuItemPrice" , menuItem.foodPrice) }
            requireContext.startActivity(intent)
        }


        // set data in to RecyclerView
        fun bind(position: Int) {
            val menuItem = menuItemss[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuItemPrice.text = menuItem.foodPrice

                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)

            }
        }
    }

}