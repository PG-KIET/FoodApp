package com.example.ecofood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecofood.adapter.MenuAdapter
import com.example.ecofood.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

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

        // Prepare the menu data
        val menuFoodName = listOf("Burger", "Sandwich", "Momo", "Item", "Sandwich", "Momo")
        val menuItemPrice = listOf("5$", "10$", "15$", "20$", "25$", "30$")
        val menuImage = listOf(
            R.drawable.menu01,
            R.drawable.menu02,
            R.drawable.menu03,
            R.drawable.menu04,
            R.drawable.menu05,
            R.drawable.menu06
        )

        // Set up the RecyclerView with the adapter
        val adapter = MenuAdapter(
            ArrayList(menuFoodName),
            ArrayList(menuItemPrice),
            ArrayList(menuImage),requireContext()
        )
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(context) // Use context instead of requireContext()
        binding.menuRecyclerView.adapter = adapter

        return binding.root
    }
}
