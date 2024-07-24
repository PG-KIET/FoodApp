package com.example.adminecofood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminecofood.adapter.DeliveryAdapter
import com.example.adminecofood.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.backButton.setOnClickListener{
            finish()
        }
        val customerName = arrayListOf(
            "Pham Gia Kiet",
            "Ho Trung Long",
            "Tran Thi Ngoc Huyen"
        )
        val moneyStatus = arrayListOf(
            "received",
            "not Received",
            "Pending"
        )
        val adapter = DeliveryAdapter(customerName,moneyStatus)
        binding.MenuRecyclerDelivery.adapter = adapter
        binding.MenuRecyclerDelivery.layoutManager = LinearLayoutManager(this)
    }
}