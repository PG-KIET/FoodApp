package com.example.ecofood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecofood.DetailsActivity
import com.example.ecofood.databinding.PopulerItemBinding



class PopularAdapter(
    private val items: List<String>,
    private val prices: List<String>,
    private val images: List<Int>,
    private val requireContext :Context
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopulerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val price = prices[position]
        val image = images[position]
        holder.bind(item, price, image)
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName",item)
            intent.putExtra("MenuItemPrice",image)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder(private val binding: PopulerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imagesView = binding.ImagePopuler

        fun bind(item: String, price: String, image: Int) {
            binding.foodNamePopular.text = item
            binding.pricePopular.text = price
            imagesView.setImageResource(image)
        }
    }
}