package com.example.ecofood

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecofood.databinding.ActivityPayOutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var name : String
    private lateinit var address: String
    private lateinit var phone : String
    private lateinit var totalAmount: String
    private lateinit var foodItemName : ArrayList<String>
    private lateinit var foodItemPrice : ArrayList<String>
    private lateinit var foodItemImage : ArrayList<String>
    private lateinit var foodItemDescription : ArrayList<String>
    private lateinit var foodItemIngredients : ArrayList<String>
    private lateinit var foodItemQuantities : ArrayList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()
        // set user data
        setUserData()

        // get user details from Firebase

        val intent = intent
        foodItemName = intent.getStringArrayListExtra("FoodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("FoodItemPrice") as ArrayList<String>
        foodItemImage= intent.getStringArrayListExtra("FoodItemImage") as ArrayList<String>
        foodItemDescription = intent.getStringArrayListExtra("FoodItemDescription") as ArrayList<String>
        foodItemIngredients = intent.getStringArrayListExtra("FoodItemIngredients") as ArrayList<String>
        foodItemQuantities = intent.getIntegerArrayListExtra("FoodItemQuantities") as ArrayList<Int>

        totalAmount = calculateTotalAmount().toString() + "$"
        binding.totalAmount.setText(totalAmount)


        binding.placeButtonOrder.setOnClickListener {
            val bottomSheetDialog = congratBottomSheetFragMent()
            bottomSheetDialog.show(supportFragmentManager, "CongratBottomSheet")
        }
        // Handle back button click
        binding.buttonBackPayOut.setOnClickListener {
            onBackPressed()  // This will navigate back to the previous fragment in the back stack
        }
    }
    private fun calculateTotalAmount(): String {
        var totalAmount = 0.0
        for (i in 0 until foodItemName.size) {
            val price = foodItemPrice[i]
            val lastChar = price.last()
            val priceDoubleValue = if (lastChar == '$') {
                price.dropLast(1).toDouble()
            } else {
                price.toDouble()
            }
            val quantity = foodItemQuantities[i]
            totalAmount += priceDoubleValue * quantity
        }
        // Định dạng tổng số tiền với 2 chữ số thập phân
        return String.format("%.2f", totalAmount)
    }





    private fun setUserData() {
        val user = auth.currentUser
        if (user != null){
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val names = snapshot.child("name").getValue(String::class.java)?:""
                        val addresses = snapshot.child("address").getValue(String::class.java)?:""
                        val phones = snapshot.child("phone").getValue(String::class.java)?:""
                        binding.apply {
                            name.setText(names)
                            address.setText(addresses)
                            phone.setText(phones)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}
