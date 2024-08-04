package com.example.ecofood.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecofood.PayOutActivity
import com.example.ecofood.R
import com.example.ecofood.adapter.CartAdapter
import com.example.ecofood.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cartFoodName = listOf("Burger", "Sandwich", "Momo", "Item", "Sandwich", "Momo")
        val cartItemPrice = listOf("5$", "10$", "15$", "20$", "25$", "30$")
        val cartImage = listOf(
            R.drawable.menu01,
            R.drawable.menu02,
            R.drawable.menu03,
            R.drawable.menu04,
            R.drawable.menu05,
            R.drawable.menu06
        )
        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartItemPrice), ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

        binding.proceedButton.setOnClickListener {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}
