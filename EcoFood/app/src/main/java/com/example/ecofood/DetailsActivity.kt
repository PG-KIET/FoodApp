package com.example.ecofood

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecofood.databinding.ActivityDetailsBinding
import com.example.ecofood.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
     private var foodName: String? = null
     private var foodPrice: String? = null
     private var foodDescription: String? = null
     private var foodImage: String? = null
     private var foodIngredient: String? = null
     private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        foodName = intent.getStringExtra("MenuItemName")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodImage = intent.getStringExtra("MenuItemImage")
        foodIngredient = intent.getStringExtra("MenuItemIngredient")

        with(binding){
            detailsFoodName.text = foodName
            description.text = foodDescription
            ingredient.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailsFoodImage)

        }

        binding.detailsFoodImage.setOnClickListener {
            finish()
        }
        binding.buttonBackDetails.setOnClickListener {
            onBackPressed()
        }
        binding.btnAddToCart.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""

        val carItem = CartItem(foodName.toString(),foodPrice.toString(),foodDescription.toString(),foodImage.toString(),1)

        database.child("user").child(userId).child("CartItems").push().setValue(carItem).addOnSuccessListener {
            Toast.makeText(this, "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener{
                Toast.makeText(this, "Thêm sản phẩm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show()
            }

    }
}
