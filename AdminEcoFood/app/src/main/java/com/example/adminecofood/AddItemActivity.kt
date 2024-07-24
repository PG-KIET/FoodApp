package com.example.adminecofood

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminecofood.databinding.ActivityAddItemBinding
import com.example.adminecofood.databinding.ActivityMainBinding

class AddItemActivity : AppCompatActivity() {
    private val binding : ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
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
        binding.selectImage.setOnClickListener {
            pickimage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.backButton.setOnClickListener{
           finish()
        }
    }
    val pickimage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            uri ->
        if (uri != null)
        {
            binding.selectedImage.setImageURI(uri)
        }
    }
}