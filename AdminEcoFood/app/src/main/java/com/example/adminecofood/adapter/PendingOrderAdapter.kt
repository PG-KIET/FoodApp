package com.example.adminecofood.adapter

import android.content.Context
import android.net.Uri
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminecofood.databinding.ItemPendingBinding

class PendingOrderAdapter(
    private val context: Context,
    private val customerNames:MutableList<String>,
    private val quantity:MutableList<String>,
    private val foodImage:MutableList<String>,
    private val itemClicked: OnItemClicked
)
    : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {
    interface  OnItemClicked {
        fun onItemClickedListener(position: Int)
        fun onAcceptClickedListener(position: Int)
        fun onDispatchClickedListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
       val binding = ItemPendingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class PendingOrderViewHolder(private val binding: ItemPendingBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                Quantity.text = quantity[position]

                val uriString = foodImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(orderFoodImage)

                acceptButton.apply {
                    if (!isAccepted) {
                        text = "Chấp nhận"
                    }
                    else
                        text = "Giao hàng"
                    setOnClickListener{
                        if (!isAccepted) {
                            text = "Giao hàng"
                            isAccepted = true
                            showToast("Đơn hàng đã được chấp nhận")
                            itemClicked.onAcceptClickedListener(position)
                        }
                        else{
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Đơn hàng đang được gửi đi")
                            itemClicked.onDispatchClickedListener(position)
                        }

                    }
                }
                itemView.setOnClickListener {
                    itemClicked.onItemClickedListener(position)
                }
            }

        }
         private fun showToast(message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}