package com.awcindia.myapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awcindia.myapp.databinding.NotificationItemBinding

class NotificationAdapters (private val notification: MutableList<String>, private val notificationImages: MutableList<Int>) : RecyclerView.Adapter<NotificationAdapters.NotiViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiViewHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotiViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = notification.size

    inner class NotiViewHolder(private val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                notificationTextView.text = notification[position]
                notificationImage.setImageResource(notificationImages[position])

            }
        }

    }
}