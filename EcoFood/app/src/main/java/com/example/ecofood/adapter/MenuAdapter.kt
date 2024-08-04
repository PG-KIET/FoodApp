package com.example.ecofood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecofood.DetailsActivity
import com.example.ecofood.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItemsName: MutableList<String>,
    private val menuItemPrice: MutableList<String>,
    private val MenuImage: MutableList<Int>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val itemClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItemsName.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position)
                }
                val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                    putExtra("MenuItemName", menuItemsName[position])
                    putExtra("MenuItemImage", MenuImage[position])  // Đảm bảo thêm hình ảnh vào đây
                }
                requireContext.startActivity(intent)
            }
        }

        fun bind(position: Int) {
            binding.apply {
                menuFoodName.text = menuItemsName[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(MenuImage[position])
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}
