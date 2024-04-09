package com.awcindia.myapp.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.awcindia.myapp.R
import com.awcindia.myapp.adapters.NotificationAdapters
import com.awcindia.myapp.databinding.FragmentNotificationBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class notificationBottomsheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotificationBottomsheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomsheetBinding.inflate(inflater , container , false)
        val notifications = listOf("your order has been Canceled successfully" , "order has been taken by the driver" , "congrasts your order placed")
        val notificationImages = listOf(R.drawable.cry, R.drawable.truck , R.drawable.correct)

        val adapter = NotificationAdapters(ArrayList(notifications) , ArrayList(notificationImages))
        binding.notificationRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerview.adapter = adapter
        return binding.root
    }
}