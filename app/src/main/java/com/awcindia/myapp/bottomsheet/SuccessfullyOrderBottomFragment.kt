package com.awcindia.myapp.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awcindia.myapp.MainActivity
import com.awcindia.myapp.databinding.FragmentSuccessfullOrderBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SuccessfullyOrderBottomFragment : BottomSheetDialogFragment(){

    private lateinit var binding : FragmentSuccessfullOrderBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessfullOrderBottomBinding.inflate(layoutInflater, container, false)
        binding.goHome.setOnClickListener{
            val intent = Intent(requireContext() , MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}