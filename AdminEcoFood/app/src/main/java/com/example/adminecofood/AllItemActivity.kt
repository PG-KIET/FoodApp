package com.example.adminecofood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminecofood.adapter.AddItemAdapter
import com.example.adminecofood.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
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
        val menuFoodName = listOf("Burger", "Sandwich", "Momo", "Item")
        val menuFoodPrice = listOf("5$", "6$", "8$", "10$")
        val menuImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4
        )
        val adapter = AddItemAdapter(ArrayList(menuFoodName),ArrayList(menuFoodPrice),ArrayList(menuImage))
        binding.MenuRecyclerView.layoutManager =  LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter
    }
}