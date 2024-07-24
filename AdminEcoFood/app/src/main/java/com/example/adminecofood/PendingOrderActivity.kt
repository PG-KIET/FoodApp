package com.example.adminecofood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminecofood.adapter.DeliveryAdapter
import com.example.adminecofood.adapter.PendingOrderAdapter
import com.example.adminecofood.databinding.ActivityPendingOrderBinding
import com.example.adminecofood.databinding.ActivitySignUpBinding

class PendingOrderActivity : AppCompatActivity() {
    private val binding: ActivityPendingOrderBinding by lazy{
        ActivityPendingOrderBinding.inflate(layoutInflater)
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
        val orderCustomerName = arrayListOf(
            "Pham Gia Kiet",
            "Ho Trung Long",
            "Tran Thi Ngoc Huyen"
        )
        val orderQuantity = arrayListOf(
            "8",
            "3",
            "6"
        )
        val foodImage = arrayListOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3
        )
        val adapter = PendingOrderAdapter(orderCustomerName,orderQuantity,foodImage, this)
        binding.pendingRecyclerView.adapter = adapter
        binding.pendingRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}