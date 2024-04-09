package com.awcindia.myapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.awcindia.myapp.R
import com.awcindia.myapp.adapters.MenuAdapters
import com.awcindia.myapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
  //  private lateinit var adapter : MenuAdapters

    private val FilerMenuFoodName = mutableListOf<String>()
    private val FilterorignalMenuFoodPrice = mutableListOf<String>()
    private val FilterMenuFoodImage = mutableListOf<Int>()


    val originalMenuFoodName = listOf("Bargur" , "sandwich" , "coca" , "delf" , "pizza" , "Bargur" , "sandwich" , "coca" , "delf" , "pizza")
    val originalMenuItemPrices = listOf("90" , "50" , "70" , "99" , "199" , "90" , "50" , "70" , "99" , "199")
    val originalMenuImage = listOf(R.drawable.bar , R.drawable.sandwich , R.drawable.coca , R.drawable.delf , R.drawable.pizza , R.drawable.bar , R.drawable.sandwich , R.drawable.coca , R.drawable.delf , R.drawable.pizza)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater , container , false)
     //    adapter = MenuAdapters(FilerMenuFoodName, FilterorignalMenuFoodPrice , FilterMenuFoodImage , requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//binding.menuRecyclerView.adapter = adapter



        setUpsearchView()
        showAllMenu()


        return binding.root
    }

    private fun showAllMenu() {
        FilerMenuFoodName.clear()
        FilterorignalMenuFoodPrice.clear()
        FilterMenuFoodImage.clear()

        FilerMenuFoodName.addAll(originalMenuFoodName)
        FilterorignalMenuFoodPrice.addAll(originalMenuItemPrices)
        FilterMenuFoodImage.addAll(originalMenuImage)

    //    adapter.notifyDataSetChanged()
    }


    private fun setUpsearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItem(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItem(newText)
                return true
            }



            private fun filterMenuItem(query: String) {
                FilerMenuFoodName.clear()
                FilterorignalMenuFoodPrice.clear()
                FilterMenuFoodImage.clear()

                originalMenuFoodName.forEachIndexed{ index, foodName -> if (foodName.contains(query.toString() , ignoreCase = true)){
                    FilerMenuFoodName.add(foodName)
                    FilterorignalMenuFoodPrice.add(originalMenuItemPrices[index])
                    FilterMenuFoodImage.add(originalMenuImage[index])
                }
                }
          //      adapter.notifyDataSetChanged()
            }
        })

    }
}





