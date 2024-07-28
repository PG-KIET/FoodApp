package com.example.ecofood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecofood.R
import com.example.ecofood.adapter.BuyAgainAdapter
import com.example.ecofood.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupRecyclerView() // Gọi hàm setupRecyclerView ở đây
        return binding.root
    }

    private fun setupRecyclerView() {
        val buyAgainFoodName = listOf("Food 1", "Food 2", "Food 3")
        val buyAgainFoodPrice = listOf("5$", "10$", "15$")
        val buyAgainFoodImage = listOf(
            R.drawable.menu01,
            R.drawable.menu02,
            R.drawable.menu03
        )

        buyAgainAdapter = BuyAgainAdapter(
            ArrayList(buyAgainFoodName),
            ArrayList(buyAgainFoodPrice),
            ArrayList(buyAgainFoodImage)
        )
        binding.BuyAgainRecyclerView.adapter = buyAgainAdapter
        binding.BuyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}
