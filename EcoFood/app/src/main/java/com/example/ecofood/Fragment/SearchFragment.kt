package com.example.ecofood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecofood.R
import com.example.ecofood.adapter.MenuAdapter
import com.example.ecofood.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private val originalMenuFoodName = listOf(
        "Burger", "Sandwich", "Momo", "Item", "Sandwich", "Momo",
        "Pizza", "Hot Dog", "Pasta", "Salad", "Sushi", "Taco"
    )
    private val originalMenuItemPrice = listOf(
        "5$", "10$", "15$", "20$", "25$", "30$",
        "12$", "8$", "18$", "9$", "22$", "6$"
    )
    private val originalMenuImage = listOf(
        R.drawable.menu01,
        R.drawable.menu02,
        R.drawable.menu03,
        R.drawable.menu04,
        R.drawable.menu05,
        R.drawable.menu06,
        R.drawable.menu01,
        R.drawable.menu02,
        R.drawable.menu03,
        R.drawable.menu04,
        R.drawable.menu05,
        R.drawable.menu06
    )

    private val filteredMenuFoodName = mutableListOf<String>()
    private val filteredMenuItemPrice = mutableListOf<String>()
    private val filteredMenuImage = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = MenuAdapter(
            filteredMenuFoodName, filteredMenuItemPrice, filteredMenuImage,requireContext()
        )
        binding.menuSearchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuSearchRecyclerView.adapter = adapter
        setupSearchView()

        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        filteredMenuFoodName.addAll(originalMenuFoodName)
        filteredMenuItemPrice.addAll(originalMenuItemPrice)
        filteredMenuImage.addAll(originalMenuImage)
        adapter.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String?) {
        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        if (query.isNullOrEmpty()) {
            showAllMenu()
            return
        }

        originalMenuFoodName.forEachIndexed { index, foodName ->
            if (foodName.contains(query, ignoreCase = true)) {
                filteredMenuFoodName.add(foodName)
                filteredMenuItemPrice.add(originalMenuItemPrice[index])
                filteredMenuImage.add(originalMenuImage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}

