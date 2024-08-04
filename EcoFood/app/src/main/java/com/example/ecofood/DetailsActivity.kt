package com.example.ecofood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecofood.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding :ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val foodName = intent.getStringExtra("MenuItemName")
        val foodImage = intent.getIntExtra("MenuItemImage",0)
        binding.detailsFoodName.text = foodName
        binding.detailsFoodImage.setImageResource(foodImage)
        binding.detailsFoodImage.setOnClickListener {
            finish()
        }
        binding.buttonBackDetails.setOnClickListener {
            onBackPressed()
        }

    }
}