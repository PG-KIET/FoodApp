package com.example.ecofood.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.ecofood.databinding.CartItemBinding
import com.example.ecofood.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<String>,
    private val cartItemPrices: MutableList<String>,
    private var cartImages: MutableList<String>, // Sử dụng danh sách Int cho hình ảnh
    private var cartDescriptions: MutableList<String>,
    private val cartQuantity:MutableList<Int>,
    private var cartIngredients: MutableList<String>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private  val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid?:""
        val cartItemnumber = cartItems.size
        itemQuantities = IntArray(cartItemnumber){1}
        cartItemReference = database.reference.child("user").child("CartItems")
    }
    companion object {
        private var itemQuantities: IntArray = intArrayOf()
        private lateinit var cartItemReference: DatabaseReference
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size
    //get update quantity
    fun getUpdatedItemsQuantities(): MutableList<Int> {
        val itemQuantity = mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity
    }

    inner class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartFoodName.text = cartItems[position]
                cartItemPrice.text = cartItemPrices[position]
                //load image using Glide
                val uriString = cartImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri)
                    .into(cartImage)
                cartItemQuantity.text = quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                        if(itemPosition != RecyclerView.NO_POSITION) {
                            deleteItem(itemPosition)
                        }
                }
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                cartQuantity[position] = itemQuantities[position]
                notifyItemChanged(position)
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                cartQuantity[position] = itemQuantities[position]
                notifyItemChanged(position)
            }
        }

        private fun deleteItem(position: Int) {
          val positionRetriever= position
            getUniqueKetAtPosition(positionRetriever){uniqueKey ->
                if(uniqueKey!=null) {
                    removeItem(position,uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if(uniqueKey != null) {
                cartItemReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartImages.removeAt(position)
                    cartDescriptions.removeAt(position)
                    cartQuantity.removeAt(position)
                    cartItemPrices.removeAt(position)
                    cartIngredients.removeAt(position)
                    Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show()
                    // cập nhat itemQuantities
                    itemQuantities = itemQuantities.filterIndexed { index, i -> index != position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemChanged(position, cartItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getUniqueKetAtPosition(positonRetriever: Int,onComplete: (String?) -> Unit) {
             cartItemReference.addListenerForSingleValueEvent(object : ValueEventListener{
                 override fun onDataChange(snapshot: DataSnapshot) {
                     var  uniqueKey:String?=  null
                     snapshot.children.forEachIndexed { index,dataSnapshot ->
                         if (index == positonRetriever) {
                             uniqueKey = dataSnapshot.key
                             return@forEachIndexed
                         }

                     }
                     onComplete(uniqueKey)
                 }

                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }
             })
        }
    }
}
