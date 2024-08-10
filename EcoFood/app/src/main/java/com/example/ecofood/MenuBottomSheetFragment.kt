package com.example.ecofood

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecofood.adapter.MenuAdapter
import com.example.ecofood.databinding.FragmentMenuBottomSheetBinding
import com.example.ecofood.model.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        // Set up the back button to dismiss the bottom sheet
        binding.buttonBack.setOnClickListener {
            dismiss()
        }
        retrieveMenuItem()

        // Set up the RecyclerView with the adapter

        return binding.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("Menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children){
                    val menuItem = foodSnapShot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                setAdapter()
            }



            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
    private fun setAdapter() {
     if (menuItems.isNotEmpty()){
         val adapter = MenuAdapter(menuItems, requireContext())
         binding.menuRecyclerView.layoutManager = LinearLayoutManager(context) // Use context instead of requireContext()
         binding.menuRecyclerView.adapter = adapter
         Log.d("Item", "setAdapter:Data set")
     }
        else{
         Log.d("Item", "setAdapter:Data not set")
     }
    }
}
