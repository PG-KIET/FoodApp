package com.example.adminecofood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminecofood.adapter.DeliveryAdapter
import com.example.adminecofood.databinding.ActivityOutForDeliveryBinding
import com.example.adminecofood.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private var listOfCompleteOrderList:ArrayList<OrderDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener{
            finish()
        }
        // retieve and display completed order
        retrieveCompletedOrderDetail()

    }

    private fun retrieveCompletedOrderDetail() {
        database = FirebaseDatabase.getInstance()
        val completedOrderReference = database.reference.child("CompletedOrder")
            .orderByChild("currentItem")
        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfCompleteOrderList.clear()
                for ( orderSnapShot in snapshot.children) {
                    val completeOrder = orderSnapShot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrderList.add(it)
                    }
                }
                listOfCompleteOrderList.reverse()
                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataIntoRecyclerView() {
        val customerName = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()

        for(order in listOfCompleteOrderList) {
            order.userName?.let {
                customerName.add(it)
            }
            moneyStatus.add(order.paymentReceived)
        }

        val adapter = DeliveryAdapter(customerName,moneyStatus)
        binding.MenuRecyclerDelivery.adapter = adapter
        binding.MenuRecyclerDelivery.layoutManager = LinearLayoutManager(this)
    }
}