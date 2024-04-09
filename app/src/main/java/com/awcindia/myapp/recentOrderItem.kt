package com.awcindia.myapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.awcindia.myapp.databinding.ActivityRecentOrderItemBinding

class recentOrderItem : AppCompatActivity() {

    private lateinit var binding : ActivityRecentOrderItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recent_order_item)

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}