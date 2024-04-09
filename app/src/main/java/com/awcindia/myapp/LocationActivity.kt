package com.awcindia.myapp

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.awcindia.myapp.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {
    val binding : ActivityLocationBinding by lazy {
        ActivityLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList = arrayOf("Rajkot" , "Ahmedabad" ,"Surat" ,"Vadodara" , "Junagadh")
        val adapter = ArrayAdapter(this , R.layout.simple_list_item_1 , locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.threshold = 0

    }

}