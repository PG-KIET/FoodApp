package com.example.ecofood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecofood.adapter.RecentBuyAdapter
import com.example.ecofood.databinding.ActivityRecentOrderItemBinding
import com.example.ecofood.model.OrderDetails


class recentOrderItem : AppCompatActivity() {
    private val binding: ActivityRecentOrderItemBinding by lazy {
        ActivityRecentOrderItemBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames: ArrayList<String>
    private lateinit var allFoodImages: ArrayList<String>
    private lateinit var allFoodPrices: ArrayList<String>
    private lateinit var allFoodQuantities: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize the lists
        allFoodNames = arrayListOf()
        allFoodImages = arrayListOf()
        allFoodPrices = arrayListOf()
        allFoodQuantities = arrayListOf()

        // Set up the back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Retrieve and set the data from the intent
        // Retrieve and set the data from the intent
        val recentOrderItems =
            intent.getSerializableExtra("RecentBuyOrderItem") as? ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetails ->
            if (orderDetails.isNotEmpty()) {
                val recentOrderItem = orderDetails[0]
                recentOrderItem.foodNames?.let { allFoodNames.addAll(it) }
                recentOrderItem.foodImages?.let { allFoodImages.addAll(it) }
                recentOrderItem.foodPrices?.let { allFoodPrices.addAll(it) }
                recentOrderItem.foodQuantities?.let { allFoodQuantities.addAll(it) }
            }
        }

        setAdapter()
    }

    private fun setAdapter() {
        val rv = binding.RecyclerViewRecentBuy
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(
            this,
            allFoodNames,
            allFoodImages,
            allFoodPrices,
            allFoodQuantities
        )
        rv.adapter = adapter
    }
}