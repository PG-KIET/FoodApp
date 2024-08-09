package com.example.adminecofood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminecofood.databinding.ActivityAddItemBinding
import com.example.adminecofood.model.AllMenu
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {
    private  lateinit var foodName: String
    private  lateinit var foodPrice: String
    private  lateinit var foodDescription: String
    private  var foodImageUri: Uri? = null
    private  lateinit var foodIngredient: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding : ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        // khởi tạo Firebase database
        database = FirebaseDatabase.getInstance()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }
        binding.backButton.setOnClickListener{
           finish()
        }
        binding.AddItemButton.setOnClickListener{
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient = binding.ingredient.text.toString().trim()

            if (foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                uploadData()
                Toast.makeText(this, "thêm sản phẩm thành công", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun uploadData() {
        val menuRef = database.getReference("Menu")
        val newItemKey = menuRef.push().key

        if (foodImageUri != null){

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnCompleteListener{
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl ->
                        val newItem = AllMenu(
                            foodName = foodName,
                            foodPrice = foodPrice,
                            foodDescription = foodDescription,
                            foodIngredient = foodIngredient,
                            foodImage = downloadUrl.toString(),

                        )
                    newItemKey?.let {
                        key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "tải dữ liệu lên thành công", Toast.LENGTH_SHORT).show()

                        }
                            .addOnFailureListener{
                                Toast.makeText(this, "tải dữ liệu lên thất bại", Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }
                .addOnFailureListener{
                    Toast.makeText(this, "tải ảnh lên thất bại", Toast.LENGTH_SHORT).show()
                }

        }
        else{
            Toast.makeText(this, "vui lòng chọn ảnh", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri ->
        if (uri != null)
        {
            binding.selectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }
}