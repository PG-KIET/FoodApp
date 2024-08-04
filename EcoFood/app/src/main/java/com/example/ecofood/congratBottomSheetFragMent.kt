package com.example.ecofood

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecofood.databinding.FragmentCongratBottomSheetFragMentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class congratBottomSheetFragMent : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCongratBottomSheetFragMentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratBottomSheetFragMentBinding.inflate(inflater, container, false)

        binding.goBackButton.setOnClickListener {
            // Dismiss the bottom sheet
            dismiss()
            // Start MainActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        return binding.root
    }
}
