package com.example.adminecofood.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminecofood.OrderDetailsActivity
import com.example.adminecofood.databinding.ActivityOrderDetailsBinding
import com.example.adminecofood.databinding.OrderdetailItemsBinding
import com.example.adminecofood.model.OrderDetails

class OrderDetailsAdapter(
    private var context: Context,
    private var foodNames: ArrayList<String>,
    private var foodImages: ArrayList<String>,
    private var foodQuantities :ArrayList<Int>,
    private var foodPrices: ArrayList<String>
):RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding = OrderdetailItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = foodNames.size
    inner class OrderDetailsViewHolder(private val binding: OrderdetailItemsBinding)
        :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
                binding.apply {
                    foodName.text = foodNames[position]

                    foodQuantity.text = foodQuantities[position].toString()
                    val uriString = foodImages[position]
                    val uri = Uri.parse(uriString)
                    Glide.with(context).load(uri).into(foodImage)
                    foodPrice.text = foodPrices[position]
                }
        }

    }


}