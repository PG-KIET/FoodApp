package com.example.ecofood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecofood.databinding.RecentBuyItemBinding

class RecentBuyAdapter(
    private var context: Context,
    foodNameList: ArrayList<String>,
    foodImageList: ArrayList<String>,
    foodPriceList: ArrayList<String>,
    foodQuantityList: ArrayList<String>,
) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): RecentViewHolder {
       val binding = RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentBuyAdapter.RecentViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    class RecentViewHolder(private val binding: RecentBuyItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

}