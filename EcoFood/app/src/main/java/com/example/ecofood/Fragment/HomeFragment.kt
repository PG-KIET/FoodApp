package com.example.ecofood.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.ecofood.MenuBottomSheetFragment
import com.example.ecofood.R
import com.example.ecofood.adapter.MenuAdapter
import com.example.ecofood.adapter.PopularAdapter
import com.example.ecofood.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
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
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage ="Selected Image $position"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_SHORT).show()
            }
        })

        adapter = MenuAdapter(
            filteredMenuFoodName, filteredMenuItemPrice, filteredMenuImage,requireContext()
        )
        binding.popularRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter=adapter
        showAllMenu()
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
    companion object {

    }
}