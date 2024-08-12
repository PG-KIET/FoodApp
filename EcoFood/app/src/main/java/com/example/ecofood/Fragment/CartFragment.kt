package com.example.ecofood.Fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecofood.PayOutActivity
import com.example.ecofood.adapter.CartAdapter
import com.example.ecofood.databinding.FragmentCartBinding
import com.example.ecofood.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""

        retrieveCartItems()

        binding.proceedButton.setOnClickListener {
            getOrderItemsDetail()
        }

        return binding.root
    }

    private fun getOrderItemsDetail() {
        val orderIdReference: DatabaseReference = database.reference.child("user").child(userId).child("CartItems")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()
        val foodQuantities = cartAdapter.getUpdatedItemsQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val orderItems = foodSnapshot.getValue(CartItem::class.java)
                    orderItems?.let {
                        foodName.add(it.foodName ?: "")
                        foodPrice.add(it.foodPrice ?: "")
                        foodDescription.add(it.foodDescription ?: "")
                        foodImage.add(it.foodImage ?: "")
                        foodIngredient.add(it.foodIngredint ?: "")
                    }
                }
                orderNow(foodName, foodPrice, foodDescription, foodImage, foodIngredient, foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Order making failed. Please try again", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("FoodItemName", ArrayList(foodName))
            intent.putExtra("FoodItemPrice", ArrayList(foodPrice))
            intent.putExtra("FoodItemImage", ArrayList(foodImage))
            intent.putExtra("FoodItemDescription", ArrayList(foodDescription))
            intent.putExtra("FoodItemIngredients", ArrayList(foodIngredient))
            intent.putExtra("FoodItemQuantities", ArrayList(foodQuantities))
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {
        val foodReference: DatabaseReference = database.reference.child("user").child(userId).child("CartItems")
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescriptions = mutableListOf()
        foodImageUri = mutableListOf()
        foodIngredients = mutableListOf()
        quantity = mutableListOf()

        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children) {
                    val cartItems = foodSnapShot.getValue(CartItem::class.java)
                    cartItems?.let {
                        foodNames.add(it.foodName ?: "")
                        foodPrices.add(it.foodPrice ?: "")
                        foodDescriptions.add(it.foodDescription ?: "")
                        foodImageUri.add(it.foodImage ?: "")
                        quantity.add(it.foodQuantity ?: 0)
                        foodIngredients.add(it.foodIngredint ?: "")
                    }
                }
                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter = CartAdapter(
                    requireContext(),
                    foodNames,
                    foodPrices,
                    foodImageUri,
                    foodDescriptions,
                    quantity,
                    foodIngredients
                )
                binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cartRecyclerView.adapter = cartAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not fetched", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
