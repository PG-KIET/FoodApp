package com.example.ecofood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecofood.adapter.NotificationAdapter
import com.example.ecofood.databinding.FragmentNotifactionBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotifactionBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotifactionBottomBinding.inflate(inflater, container, false)
        val notifications = listOf("Đơn hàng của bạn đã hủy thành công", "Đơn hàng của bạn đã được tài xế tiếp nhận", "Đơn hàng của bạn đã được đặt thành công")
        val notificationImage = listOf(R.drawable.sad, R.drawable.delivery, R.drawable.tick)
        val adapter = NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notificationImage)
        )
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter=adapter
        // Here you can set up your RecyclerView with the data
        return binding.root
    }
}
