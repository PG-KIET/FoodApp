package com.example.adminecofood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminecofood.adapter.PendingOrderAdapter
import com.example.adminecofood.databinding.ActivityPendingOrderBinding
import com.example.adminecofood.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(), PendingOrderAdapter.OnItemClicked {
    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFirstFood: MutableList<String> = mutableListOf()
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrdersDetails()

        binding.backButton.setOnClickListener {
            finish()
        }


    }

    private fun getOrdersDetails() {
        // retrieve order details from firebase database
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapShot in snapshot.children) {
                    val orderDetails = orderSnapShot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for (orderItem in listOfOrderItem) {
            //add data to respective list for popular the recycler
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.firstOrNull()?.let { firstImage ->
                listOfImageFirstFood.add(firstImage)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter =
            PendingOrderAdapter(this, listOfName, listOfTotalPrice, listOfImageFirstFood, this)
        binding.pendingRecyclerView.adapter = adapter
    }

    override fun onItemClickedListener(position: Int) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrderItem[position]
        intent.putExtra("userOrderDetails", userOrderDetails)
        startActivity(intent)
    }

    override fun onAcceptClickedListener(position: Int) {
        val childeItemPushKey = listOfOrderItem[position].itemPushKey
        val clickItemOrderReference = childeItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReference?.child("AcceptedOrder")?.setValue(true)
        updateOrderAcceptedStatus(position)
    }

    override fun onDispatchClickedListener(position: Int) {
        val dispatchItemPushKey = listOfOrderItem[position].itemPushKey
        val dispatchItemReference = database.reference.child("CompletedOrder")
            .child(dispatchItemPushKey!!)
        dispatchItemReference.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deleteThisItemFromOrderDetails(dispatchItemPushKey)
            }
    }

    private fun deleteThisItemFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemReference = database.reference.child("OrderDetails")
            .child(dispatchItemPushKey)
        orderDetailsItemReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "OrderIs is Dispatched", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "OrderIs is not Dispatched", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateOrderAcceptedStatus(position: Int) {
        val userIdOfClickedItem = listOfOrderItem[position].userUid
        val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
        val buyHistoryReference =
            database.reference.child("user").child(userIdOfClickedItem!!).child("BuyHistory")
                .child(pushKeyOfClickedItem!!)
        buyHistoryReference.child("AcceptedOrder").setValue(true)
        databaseOrderDetails.child(pushKeyOfClickedItem).child("AcceptedOrder").setValue(true)
    }

}