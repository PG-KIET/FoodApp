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
    // TODO: Rename and change types of parameters
    private lateinit var  binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)
        binding.buttonBack.setOnClickListener {
            dismiss()
        }
        val menuFoodName = listOf("Burger","sanwwich","momo","item","sanwich","momo")
        val menuItemPrice = listOf("5$","10$","15$","20$","25$","30$")
        val menuImage = listOf(
            R.drawable.menu01,
            R.drawable.menu02,
            R.drawable.menu03,
            R.drawable.menu04,
            R.drawable.menu05,
            R.drawable.menu06
        )
        val adapter = MenuAdapter(
            ArrayList(menuFoodName),
            ArrayList(menuItemPrice),
            ArrayList(menuImage))
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {


    }
}